package com.goggle.voco.repository;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndProject(User user, Project project);
    @Query("select b "
            + "from Bookmark b "
            + "left outer join Project p "
            + "on p.team.id=:teamId "
            + "where b.user.id=:userId")
    List<Bookmark> findByUserIdAndTeamId(Long userId, Long teamId);
    boolean existsByUserAndProject(User user, Project project);
}
