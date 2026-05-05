package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

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

    @EntityGraph(attributePaths = {"roles"})
    @Query("select u from UserEntity u order by u.createdAt asc")
    Page<UserEntity> findAllWithRolesOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByEmail(String email);
}
