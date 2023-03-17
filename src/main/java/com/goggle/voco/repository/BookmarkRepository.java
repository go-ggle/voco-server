package com.goggle.voco.repository;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndProject(User user, Project project);
}
