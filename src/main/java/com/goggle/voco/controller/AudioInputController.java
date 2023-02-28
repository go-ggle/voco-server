package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import com.goggle.voco.service.AudioInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/inputs")
@Log4j2
@RequiredArgsConstructor
public class AudioInputController {

    private final AudioInputService audioInputService;

    @PostMapping("/{textId}")
    public ResponseEntity<String> audioInput(
            @AuthenticationPrincipal User user,
            @PathVariable("textId") Long textId,
            @RequestPart MultipartFile audio
    ) throws IOException {

        audioInputService.audioInput(user, textId, audio);
        return ResponseEntity.status(HttpStatus.OK).body(textId + "번 음성이 입력되었습니다.");
    }

}
