package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;
import com.goggle.voco.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/bookmarks")
@Log4j2
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{projectId}")
    public ResponseEntity<BookmarkResponseDto> bookmarkProject(
            @AuthenticationPrincipal User user,
            @PathVariable("projectId") Long projectId) throws Exception {
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.createBookmark(user, projectId);

        return ResponseEntity.status(HttpStatus.OK).body(bookmarkResponseDto);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(
            @AuthenticationPrincipal User user,
            @PathVariable("projectId") Long projectId) throws Exception {
        bookmarkService.deleteBookmark(user, projectId);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 북마크가 해제되었습니다.");
    }

}
