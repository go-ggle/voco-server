package com.goggle.voco.controller;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects/{projectId}")
@Log4j2
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<BlockResponseDto> createBlock(@RequestBody AudioRequestDto audioRequestDto, @PathVariable Long projectId) {
        BlockResponseDto blockResponseDto = blockService.createBlock(audioRequestDto, projectId);

        return new ResponseEntity<>(blockResponseDto, HttpStatus.CREATED);

    }
}
