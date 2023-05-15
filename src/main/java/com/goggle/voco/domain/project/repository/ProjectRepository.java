package com.goggle.voco.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goggle.voco.domain.project.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    Optional<Project> findById(Long projectId);
    List<Project> findByTeamIdOrderByUpdatedAtDesc(Long teamId);
}

