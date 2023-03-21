package com.goggle.voco.service;

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

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final ProjectRepository projectRepository;

    @Value("${AUDIO_BUCKET_NAME}")
    private String AUDIO_BUCKET_NAME;
    @Value("${AWS_REGION}")
    private String AWS_REGION;
    @Value("${AI_ADDRESS}")
    private String AI_ADDRESS;
    @Value("${FLASK_PORT}")
    private String FLASK_PORT;

    @Override
    public String createAudio(AudioRequestDto audioRequestDto) {

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
        String audioPath = "https://" + AUDIO_BUCKET_NAME + ".s3." + AWS_REGION + ".amazonaws.com/"+ projectId + "/" + blockId + ".wav";

        return audioPath;
    }

    @Override
    public BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId) {
        String text = audioRequestDto.getText();
        Long voiceId = audioRequestDto.getVoiceId();
        audioRequestDto.setProjectId(projectId);

        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        Block block = new Block(project, text, "", voiceId);
        blockRepository.save(block);

        audioRequestDto.setBlockId(block.getId());
        String audioPath = createAudio(audioRequestDto);
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
    public BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));

        audioRequestDto.setProjectId(block.getProject().getId());
        audioRequestDto.setBlockId(block.getId());

        block.setText(audioRequestDto.getText());
        block.setAudioPath(createAudio(audioRequestDto));
        block.setUpdatedAt(LocalDateTime.now());

        blockRepository.save(block);

        return BlockResponseDto.from(block);
    }
}
