package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReportEntity, UUID> {
    List<OrderReportEntity> findAllByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrder(UUID id);

    @EntityGraph(attributePaths = {"order", "freelancer", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndFreelancerAndService(UUID id);
}
