package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FreelancerRepository extends JpaRepository<FreelancerEntity, UUID> {
    Optional<FreelancerEntity> findByUser(UserEntity user);

    FreelancerEntity getReferenceByUser(UserEntity user);

    @EntityGraph(attributePaths = {"jobTitle", "user"})
    Optional<FreelancerEntity> findWithJobTitleByUser(UserEntity user);

    boolean existsByUser(UserEntity user);

    @EntityGraph(attributePaths = {"jobTitle"})
    @Query("select f from FreelancerEntity f where f.id = :id")
    Optional<FreelancerEntity> findByIdWithJobTitle(UUID id);
}
