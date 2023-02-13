package com.goggle.voco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goggle.voco.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
}
