package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReportEntity, UUID> {
    @EntityGraph(attributePaths = {"customer", "files"})
    List<OrderReportEntity> findAllWithCustomerAndFilesByFreelancerOrderByCreatedAtDesc(FreelancerEntity freelancer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle", "files"})
    List<OrderReportEntity> findAllWithFreelancerAndFilesByCustomerOrderByCreatedAtDesc(UserEntity customer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle"})
    List<OrderReportEntity> findAllWithFreelancerByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndService(UUID id);

    @EntityGraph(attributePaths = {"order", "freelancer", "order.service", "customer"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndFreelancerAndService(UUID id);

    boolean existsByOrderAndStatus(OrderEntity order, OrderReportStatusEnum status);

    @EntityGraph(attributePaths = {"freelancer", "order.service", "customer"})
    List<OrderReportEntity> findWithOrderAndServiceAndCustomerAllByCreatedAtLessThanAndStatus(
            Instant createdAt, OrderReportStatusEnum status);
}
