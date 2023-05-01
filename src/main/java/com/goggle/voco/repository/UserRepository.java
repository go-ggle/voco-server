package com.goggle.voco.repository;

import com.goggle.voco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndSocialId(String socialType, Long socialId);
}
