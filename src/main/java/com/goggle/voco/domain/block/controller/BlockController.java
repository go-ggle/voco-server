package com.goggle.voco.domain.block.controller;

import com.goggle.voco.domain.block.dto.AudioRequestDto;
import com.goggle.voco.domain.block.dto.BlockCreateRequestDto;
import com.goggle.voco.domain.block.dto.BlockResponseDto;
import com.goggle.voco.domain.block.dto.BlocksResponseDto;
import com.goggle.voco.domain.block.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Block", description = "블럭 음성 API")
public class BlockController {

    private final BlockService blockService;

    @PostMapping("")
    @Operation(summary = "블럭 음성 생성", description = "블럭 음성을 생성한다.")
    public ResponseEntity<BlockResponseDto> createBlock(
            @RequestBody BlockCreateRequestDto blockCreateRequestDto,
            @PathVariable("teamId") Long teamId,
            @PathVariable("projectId") Long projectId) {
        BlockResponseDto blockResponseDto = blockService.createBlock(blockCreateRequestDto, teamId, projectId);

        return new ResponseEntity<>(blockResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "블럭 음성 목록 조회", description = "특정 프로젝트에 속한 블럭 음성 목록을 조회한다.")
    public ResponseEntity<BlocksResponseDto> findBlocks(
            @AuthenticationPrincipal Long userId,
            @PathVariable("projectId") Long projectId) {
        BlocksResponseDto blocksResponseDto = blockService.findBlocks(projectId);

        return new ResponseEntity<>(blocksResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{blockId}")
    @Operation(summary = "블럭 음성 삭제", description = "특정 블럭 음성을 삭제한다.")
    public ResponseEntity<String> deleteBlock(
            @PathVariable("teamId") Long teamId,
            @PathVariable("projectId") Long projectId,
            @PathVariable("blockId") Long blockId) throws Exception {
        blockService.deleteBlock(teamId, projectId, blockId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{blockId}")
    @Operation(summary = "블럭 음성 수정", description = "특정 블럭 음성을 수정한다.")
    public ResponseEntity<BlockResponseDto> updateBlock(
            @RequestBody AudioRequestDto audioRequestDto,
            @PathVariable("teamId") Long teamId,
            @PathVariable("projectId") Long projectId,
            @PathVariable("blockId") Long blockId) throws Exception{
        BlockResponseDto blockResponseDto = blockService.updateBlock(audioRequestDto, teamId, projectId, blockId);
        return new ResponseEntity<>(blockResponseDto, HttpStatus.OK);
    }
}
