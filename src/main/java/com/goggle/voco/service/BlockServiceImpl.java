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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

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
    public String createAudio(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .path("/tts")
                .queryParam("teamId", teamId)
                .queryParam("projectId", projectId)
                .queryParam("blockId", blockId)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<byte[]> audioEntity = restTemplate.postForEntity(uri, audioRequestDto, byte[].class);
        byte[] body = audioEntity.getBody();

        String audioPath = "https://" + AUDIO_BUCKET_NAME + ".s3." + AWS_REGION + ".amazonaws.com/" + teamId + "/" + projectId + "/" + blockId + ".wav";

        return audioPath;
    }

    @Override
    public void mergeBlocks(Long teamId, Long projectId){
        try {
            List<Block> blocks = blockRepository.findByProjectId(projectId);
            long length = 0;
            AudioInputStream clip = null;
            List<AudioInputStream> list = new ArrayList<AudioInputStream>();
            new File(String.valueOf(projectId)).mkdirs();
            for (Block b: blocks) {
                FileOutputStream fos = new FileOutputStream(new File(projectId + "/temp" + b.getId() + ".wav"));
                S3Object o = amazonS3Client.getObject(AUDIO_BUCKET_NAME, teamId + "/" + projectId + "/" + b.getId() + ".wav");
                S3ObjectInputStream s3is = o.getObjectContent();
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                fos.close();
                String[] cmd = {"sox", projectId + "\\temp" + b.getId() + ".wav", projectId + "\\interval" + b.getId() + ".wav", "pad", "0", String.valueOf(b.getInterval())};
                try {
                    Runtime rt = Runtime.getRuntime();
                    Process pr = rt.exec(cmd);
                    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                    String line = null;

                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }

                    int exitVal = pr.waitFor();
                    System.out.println("Exited with error code " + exitVal);
                } catch(Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }

                clip = AudioSystem.getAudioInputStream(new File(projectId + "/interval" + b.getId() + ".wav"));
                list.add(clip);
                length += clip.getFrameLength();
            }
            if(length>0 && list.size()>0 && clip!=null) {
                AudioInputStream appendedFiles =
                        new AudioInputStream(
                                new SequenceInputStream(Collections.enumeration(list)),
                                clip.getFormat(),
                                length);

                AudioSystem.write(appendedFiles,
                        AudioFileFormat.Type.WAVE,
                        new File(projectId + "/appended.wav"));
            }
            clip.close();
            amazonS3Client.putObject(AUDIO_BUCKET_NAME, teamId + "/" + projectId + "/0.wav", new File(projectId + "/appended.wav"));
            FileUtils.deleteDirectory(new File(String.valueOf(projectId)));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId) {
        String text = audioRequestDto.getText();
        Long voiceId = audioRequestDto.getVoiceId();
        Long interval = audioRequestDto.getInterval();

        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        Block block = new Block(project, text, "", voiceId, interval);
        blockRepository.save(block);

        String audioPath = createAudio(audioRequestDto, teamId, projectId, block.getId());
        block.setAudioPath(audioPath);
        //mergeBlocks(teamId, projectId);

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
    public void deleteBlock(Long teamId, Long projectId, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));
        blockRepository.delete(block);
        //mergeBlocks(teamId, projectId);
    }

    @Override
    public BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));

        block.setText(audioRequestDto.getText());
        block.setAudioPath(createAudio(audioRequestDto, teamId, projectId, blockId));
        block.setUpdatedAt(LocalDateTime.now());
        //mergeBlocks(teamId, projectId);

        blockRepository.save(block);

        return BlockResponseDto.from(block);
    }
}
