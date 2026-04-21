package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReportEntity, UUID> {
    @EntityGraph(attributePaths = {"customer"})
    List<OrderReportEntity> findAllWithCustomerByFreelancerOrderByCreatedAtDesc(FreelancerEntity freelancer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle"})
    List<OrderReportEntity> findAllWithFreelancerByCustomerOrderByCreatedAtDesc(UserEntity customer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle"})
    List<OrderReportEntity> findAllWithFreelancerByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndService(UUID id);

    @EntityGraph(attributePaths = {"order", "freelancer", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndFreelancerAndService(UUID id);
}
