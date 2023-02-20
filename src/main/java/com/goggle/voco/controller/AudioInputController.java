package com.goggle.voco.controller;

import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import com.goggle.voco.service.AudioInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/input")
@Log4j2
@RequiredArgsConstructor
public class AudioInputController {

    private final AudioInputService audioInputService;

    @PostMapping("")
    public ResponseEntity<AudioInputResponseDto> audioInput(
            @RequestPart MultipartFile audio,
            @RequestPart AudioInputRequestDto audioInputRequestDto
    ) throws IOException {

        AudioInputResponseDto audioInputResponseDto = audioInputService.audioInput(audio, audioInputRequestDto);

        return new ResponseEntity<>(audioInputResponseDto, HttpStatus.CREATED);
    }

}
