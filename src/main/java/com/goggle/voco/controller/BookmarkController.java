package com.goggle.voco.controller;

import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;
import com.goggle.voco.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bookmarks")
@Log4j2
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{projectId}")
    public ResponseEntity<BookmarkResponseDto> bookmarkProject(@PathVariable("projectId") Long projectId, BookmarkRequestDto bookmarkRequestDto) throws Exception {
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.createBookmark(projectId, bookmarkRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(bookmarkResponseDto);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId, BookmarkRequestDto bookmarkRequestDto) throws Exception {
        bookmarkService.deleteBookmark(projectId, bookmarkRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 북마크가 해제되었습니다.");
    }

}
