package com.goggle.voco.service;

import com.goggle.voco.domain.Project;
import com.goggle.voco.dto.BookmarkRequestDto;
import com.goggle.voco.dto.BookmarkResponseDto;
import com.goggle.voco.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class BookmarkServiceImpl implements BookmarkService{
    private final ProjectRepository projectRepository;

    @Autowired
    public BookmarkServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public BookmarkResponseDto bookmarkProject(Long projectId) throws Exception {
        Optional<Project> selectedProject = projectRepository.findById(projectId);

        Project updatedProject;
        if(selectedProject.isPresent()){
            Project project = selectedProject.get();
            project.setIsBookmarked(!project.getIsBookmarked());
            updatedProject = projectRepository.save(project);
        }
        else{
            throw new Exception();
        }

        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();

        bookmarkResponseDto.setId(projectId);
        bookmarkResponseDto.setIsBookmarked(updatedProject.getIsBookmarked());

        return bookmarkResponseDto;
    }
}
