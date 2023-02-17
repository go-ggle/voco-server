package com.goggle.voco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goggle.voco.domain.Project;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    Optional<Project> findById(Long projectId);
}

