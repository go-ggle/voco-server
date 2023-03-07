package com.goggle.voco.service;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.User;
import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.repository.BookmarkRepository;
import com.goggle.voco.repository.ProjectRepository;
import com.goggle.voco.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public BookmarkResponseDto createBookmark(User user, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException("존재하지 않는 프로젝트입니다."));

        Bookmark bookmark = new Bookmark(user, project);
        bookmarkRepository.save(bookmark);

        return BookmarkResponseDto.from(bookmark);
    }

    @Override
    public void deleteBookmark(User user, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException("존재하지 않는 프로젝트입니다."));
        Bookmark bookmark = bookmarkRepository.findByUserAndProject(user, project).orElseThrow();

        bookmarkRepository.delete(bookmark);
    }
}
