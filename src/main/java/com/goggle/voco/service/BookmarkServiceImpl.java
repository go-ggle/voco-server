package com.goggle.voco.service;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.User;
import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;
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
    public BookmarkResponseDto createBookmark(Long projectId, BookmarkRequestDto bookmarkRequestDto) throws Exception {
        Optional<Project> selectedProject = projectRepository.findById(projectId);
        Optional<User> selectedUser = userRepository.findById(bookmarkRequestDto.getUserId());

        Bookmark bookmark = new Bookmark();
        if(selectedProject.isPresent() && selectedUser.isPresent()){
            Project project = selectedProject.get();
            User user = selectedUser.get();

            bookmark.setProject(project);
            bookmark.setUser(user);

            bookmarkRepository.save(bookmark);
        }
        else{
            throw new Exception();
        }

        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();

        bookmarkResponseDto.setId(bookmark.getId());
        bookmarkResponseDto.setProjectId(projectId);

        return bookmarkResponseDto;
    }
}
