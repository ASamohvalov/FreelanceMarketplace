package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    // todo change this to EntityGraph?
    @Query("""
            select u from UserEntity u
            join fetch u.roles
            where u.email = :email
            """)
    Optional<UserEntity> findByEmailWithRoles(String email);

    @Query("""
            select u from UserEntity u
            join fetch u.roles
            where u.id = :id
            """)
    Optional<UserEntity> findByIdWithRoles(UUID id);

    boolean existsByEmail(String email);
}
