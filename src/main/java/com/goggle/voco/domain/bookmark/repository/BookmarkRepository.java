package com.goggle.voco.domain.bookmark.repository;

import com.goggle.voco.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUser_IdAndProject_Id(Long userId, Long projectId);
    @Query("select b "
            + "from Bookmark b "
            + "join Project p "
            + "on p.team.id=:teamId "
            + "where b.user.id=:userId")
    List<Bookmark> findByUserIdAndTeamId(Long userId, Long teamId);
    boolean existsByUser_IdAndProject_Id(Long userId, Long projectId);
}
