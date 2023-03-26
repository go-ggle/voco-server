package com.goggle.voco.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.goggle.voco.domain.Block;
import com.goggle.voco.domain.Project;
import com.goggle.voco.dto.*;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.repository.BlockRepository;
import com.goggle.voco.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final ProjectRepository projectRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${AUDIO_BUCKET_NAME}")
    private String AUDIO_BUCKET_NAME;
    @Value("${AWS_REGION}")
    private String AWS_REGION;
    @Value("${AI_ADDRESS}")
    private String AI_ADDRESS;
    @Value("${FLASK_PORT}")
    private String FLASK_PORT;

    @Override
    public String createAudio(AudioRequestDto audioRequestDto, Long teamId) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .path("/tts")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<byte[]> audioEntity = restTemplate.postForEntity(uri, audioRequestDto, byte[].class);
        byte[] body = audioEntity.getBody();

        Long projectId = audioRequestDto.getProjectId();
        Long blockId = audioRequestDto.getBlockId();
        String audioPath = "https://" + AUDIO_BUCKET_NAME + ".s3." + AWS_REGION + ".amazonaws.com/" + teamId + "/" + projectId + "/" + blockId + ".wav";

        return audioPath;
    }

    @Override
    public void mergeBlocks(Long projectId){
        List<Block> blocks = blockRepository.findByProjectId(projectId);
        try {
            FileOutputStream fos = new FileOutputStream(new File("temp.wav"));
            for(Block b: blocks){
                S3Object o = amazonS3Client.getObject(AUDIO_BUCKET_NAME, projectId + "/" + b.getId());
                S3ObjectInputStream s3is = o.getObjectContent();
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                s3is.close();
            }
            fos.close();
            amazonS3Client.putObject(AUDIO_BUCKET_NAME, projectId + "/0", new File("temp.wav"));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId) {
        String text = audioRequestDto.getText();
        Long userId = audioRequestDto.getUserId();
        audioRequestDto.setProjectId(projectId);

        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        Block block = new Block(project, text, "", userId);
        blockRepository.save(block);

        audioRequestDto.setBlockId(block.getId());
        String audioPath = createAudio(audioRequestDto, teamId);
        block.setAudioPath(audioPath);

        return BlockResponseDto.from(block);
    }

    @Override
    public BlocksResponseDto findBlocks(Long projectId) {
        List<Block> blocks = blockRepository.findByProjectId(projectId);
        List<BlockResponseDto> blocksResponseDtos = blocks.stream()
                .map(block -> BlockResponseDto.from(block))
                .collect(Collectors.toList());

        return new BlocksResponseDto(blocksResponseDtos);
    }

    //TODO: 버켓에서 음성 삭제
    @Override
    public void deleteBlock(Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));

        blockRepository.delete(block);
    }

    @Override
    public BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));

        audioRequestDto.setProjectId(block.getProject().getId());
        audioRequestDto.setBlockId(block.getId());

        block.setText(audioRequestDto.getText());
        block.setAudioPath(createAudio(audioRequestDto, teamId));
        block.setUpdatedAt(LocalDateTime.now());
        mergeBlocks(audioRequestDto.getProjectId());

        blockRepository.save(block);

        return BlockResponseDto.from(block);
    }
}
