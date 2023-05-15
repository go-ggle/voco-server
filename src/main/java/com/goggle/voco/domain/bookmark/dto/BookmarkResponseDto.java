package com.goggle.voco.domain.bookmark.dto;

import com.goggle.voco.domain.bookmark.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponseDto {
    private Long id;
    private Long project;
    private Long user;

    public static BookmarkResponseDto from(Bookmark bookmark) {
        return new BookmarkResponseDto(
            bookmark.getId(),
            bookmark.getProject().getId(),
            bookmark.getUser().getId()
        );
    }
}
