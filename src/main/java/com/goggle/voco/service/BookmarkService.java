package com.goggle.voco.service;

import com.goggle.voco.dto.BookmarkResponseDto;

public interface BookmarkService {
    BookmarkResponseDto createBookmark(Long userId, Long projectId) throws Exception;
    void deleteBookmark(Long userId, Long projectId) throws Exception;
}
