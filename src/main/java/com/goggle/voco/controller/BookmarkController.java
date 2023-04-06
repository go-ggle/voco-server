package com.goggle.voco.controller;

import com.goggle.voco.dto.BookmarkResponseDto;
import com.goggle.voco.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@Controller
@RequestMapping("/bookmarks")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Bookmark", description = "북마크 API")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{projectId}")
    @Operation(summary = "북마크 생성", description = "특정 프로젝트를 북마크한다.")
    public ResponseEntity<BookmarkResponseDto> bookmarkProject(
            @AuthenticationPrincipal Long userId,
            @PathVariable("projectId") Long projectId) throws Exception {
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.createBookmark(userId, projectId);

        return ResponseEntity.status(HttpStatus.OK).body(bookmarkResponseDto);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "북마크 해제", description = "특정 프로젝트를 북마크 해제한다.")
    public ResponseEntity<String> deleteProject(
            @AuthenticationPrincipal Long userId,
            @PathVariable("projectId") Long projectId) throws Exception {
        bookmarkService.deleteBookmark(userId, projectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
