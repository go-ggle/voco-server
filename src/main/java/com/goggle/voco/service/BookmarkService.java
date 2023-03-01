package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;

public interface BookmarkService {
    BookmarkResponseDto createBookmark(User user, Long projectId) throws Exception;
    void deleteBookmark(User user, Long projectId) throws Exception;
}
