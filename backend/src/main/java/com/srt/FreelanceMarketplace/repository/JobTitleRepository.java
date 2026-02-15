package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitleEntity, UUID> {
    Optional<JobTitleEntity> findByName(String name);
    boolean existsByName(String name);
}
