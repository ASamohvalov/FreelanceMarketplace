package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderExtensionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderExtensionRepository extends JpaRepository<OrderExtensionEntity, UUID> {

    @EntityGraph(attributePaths = {"order.service", "order.freelancer"})
    Optional<OrderExtensionEntity> findWithOrderAndFreelancerById(UUID id);
}
