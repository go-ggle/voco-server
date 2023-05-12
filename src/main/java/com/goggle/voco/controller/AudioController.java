package com.goggle.voco.controller;

import com.goggle.voco.service.AudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
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
        audioService.audioInput(userId, textId, audio);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{textId}")
    public Mono<ByteArrayResource> getAudio(
            @AuthenticationPrincipal Long userId,
            @PathVariable("textId") Long textId
    ) throws IOException {
        return audioService.getAudio(userId, textId);
    }
}
