package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

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
        audioRequestDto.setUser(1);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> audioPath = restTemplate.postForEntity(uri, audioRequestDto, String.class);

        log.info(audioPath);

        return null;
    }

    @Override
    public BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId) {

        String text = audioRequestDto.getText();
        createAudio(text);

        return null;
    }
}
