package com.goggle.voco.service;

import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;

public interface BookmarkService {
    BookmarkResponseDto bookmarkProject(Long projectId) throws Exception;
}