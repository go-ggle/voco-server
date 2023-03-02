package com.goggle.voco.repository;

import com.goggle.voco.domain.Participation;
import com.goggle.voco.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("select t "
            + "from Participation p "
            + "join p.team t "
            + "where p.user.id = :userId "
            + "order by t.isPrivate desc")
    List<Team> findTeamsByUserId(Long userId);
}