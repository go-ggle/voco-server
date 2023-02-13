package com.goggle.voco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goggle.voco.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
}
