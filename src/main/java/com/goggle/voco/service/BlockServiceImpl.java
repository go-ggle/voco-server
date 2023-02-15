package com.goggle.voco.service;

import com.goggle.voco.domain.Block;
import com.goggle.voco.domain.Project;
import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.repository.BlockRepository;
import com.goggle.voco.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final ProjectRepository projectRepository;

    @Override
    public String createAudio(String text) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://58.142.29.186:52424")
                .path("/tts")
                .encode()
                .build()
                .toUri();

        AudioRequestDto audioRequestDto = new AudioRequestDto();
        audioRequestDto.setLanguage(0);
        audioRequestDto.setText(text);
        audioRequestDto.setUser(400);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<byte[]> audioEntity = restTemplate.postForEntity(uri, audioRequestDto, byte[].class);
        byte[] body = audioEntity.getBody();

        String audioPath = "sample.wav";

        return audioPath;
    }

    @Override
    public BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId) {

        String text = audioRequestDto.getText();
        String audioPath = createAudio(text);

        Project project = projectRepository.findById(projectId).orElseThrow();
        Block block = new Block(text, audioPath, project);
        blockRepository.save(block);

        return BlockResponseDto.from(block);
    }
}
