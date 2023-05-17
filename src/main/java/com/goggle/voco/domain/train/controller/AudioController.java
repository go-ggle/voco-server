package com.goggle.voco.domain.train.controller;

import com.goggle.voco.domain.train.service.AudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
@RequestMapping("/inputs")
@Log4j2
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    @PostMapping("/{textId}")
    public ResponseEntity<String> audioInput(
            @AuthenticationPrincipal Long userId,
            @PathVariable("textId") Long textId,
            @RequestPart MultipartFile audio
    ) throws IOException {
        HttpStatus status = audioService.audioInput(userId, textId, audio);
        return new ResponseEntity<>(status);
    }

    @GetMapping("/{textId}")
    public Mono<ResponseEntity<ByteArrayResource>> getAudio(
            @AuthenticationPrincipal Long userId,
            @PathVariable("textId") Long textId
    ) throws IOException {
        return audioService.getAudio(userId, textId);
    }
}
