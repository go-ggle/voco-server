package com.goggle.voco.controller;

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
    public ResponseEntity<BookmarkResponseDto> bookmarkProject(@PathVariable("projectId") Long projectId) throws Exception {
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.bookmarkProject(projectId);

        return ResponseEntity.status(HttpStatus.OK).body(bookmarkResponseDto);
    }

}
