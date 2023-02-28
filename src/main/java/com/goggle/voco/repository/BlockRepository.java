package com.goggle.voco.repository;

import com.goggle.voco.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findByProjectId(Long projectId);
}
