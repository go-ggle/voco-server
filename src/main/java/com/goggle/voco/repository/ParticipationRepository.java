package com.goggle.voco.repository;

import com.goggle.voco.domain.Participation;
import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("select t "
            + "from Participation p "
            + "join p.team t "
            + "where p.user.id = :userId "
            + "order by t.isPrivate desc")
    List<Team> findTeamsByUserId(Long userId);
    @Query("select u "
            + "from Participation p "
            + "join p.user u "
            + "where p.user.isRegistered = true")
    List<User> findRegisteredUsersByTeamId(Long teamId);
    Participation findByUserAndTeamId(User user, Long teamId);
}