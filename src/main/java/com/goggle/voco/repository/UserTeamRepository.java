package com.goggle.voco.repository;

import com.goggle.voco.domain.User_Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<User_Team, Long> {
}