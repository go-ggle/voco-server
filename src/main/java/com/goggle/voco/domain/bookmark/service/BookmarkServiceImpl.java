package com.goggle.voco.domain.bookmark.service;

import com.goggle.voco.domain.bookmark.domain.Bookmark;
import com.goggle.voco.domain.project.domain.Project;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.domain.bookmark.dto.BookmarkResponseDto;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.domain.bookmark.repository.BookmarkRepository;
import com.goggle.voco.domain.project.repository.ProjectRepository;
import com.goggle.voco.domain.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkRepository bookmarkRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookmarkResponseDto createBookmark(Long userId, Long projectId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        Bookmark bookmark = new Bookmark(user, project);
        bookmarkRepository.save(bookmark);

        return BookmarkResponseDto.from(bookmark);
    }

    @Override
    public void deleteBookmark(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        Bookmark bookmark = bookmarkRepository.findByUser_IdAndProject_Id(userId, projectId).orElseThrow(()-> new NotFoundException(ErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }
}
