package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.dto.BlocksResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams/{teamId}/projects/{projectId}/blocks")
@Log4j2
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping("")
    public ResponseEntity<BlockResponseDto> createBlock(@RequestBody AudioRequestDto audioRequestDto, @PathVariable("projectId") Long projectId) {
        BlockResponseDto blockResponseDto = blockService.createBlock(audioRequestDto, projectId);

        return new ResponseEntity<>(blockResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<BlocksResponseDto> findBlocks(
            @AuthenticationPrincipal User user,
            @PathVariable("projectId") Long projectId) {
        BlocksResponseDto blocksResponseDto = blockService.findBlocks(projectId);

        return new ResponseEntity<>(blocksResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{blockId}")
    public ResponseEntity<String> deleteBlock(@PathVariable("blockId") Long blockId) throws Exception {
        blockService.deleteBlock(blockId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
