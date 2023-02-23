package com.goggle.voco.repository;

import com.goggle.voco.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndProject(Long userId, Long projectId);
}
