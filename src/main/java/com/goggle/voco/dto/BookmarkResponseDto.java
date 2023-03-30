package com.goggle.voco.dto;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
