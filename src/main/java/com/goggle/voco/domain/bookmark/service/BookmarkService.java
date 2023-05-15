package com.goggle.voco.domain.bookmark.service;

import com.goggle.voco.domain.bookmark.dto.BookmarkResponseDto;

public interface BookmarkService {
    BookmarkResponseDto createBookmark(Long userId, Long projectId) throws Exception;
    void deleteBookmark(Long userId, Long projectId) throws Exception;
}
