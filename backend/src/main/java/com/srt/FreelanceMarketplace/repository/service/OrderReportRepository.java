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
    @Query("select r from OrderReportEntity r where r.freelancer = :freelancer")
    List<OrderReportEntity> findAllByFreelancerWithCustomer(FreelancerEntity freelancer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle"})
    @Query("select r from OrderReportEntity r where r.customer = :customer")
    List<OrderReportEntity> findAllByCustomerWithFreelancer(UserEntity customer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user", "freelancer.jobTitle"})
    @Query("select r from OrderReportEntity r where r.order = :order")
    List<OrderReportEntity> findAllByOrderWithFreelancer(OrderEntity order);

    @EntityGraph(attributePaths = {"order", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndService(UUID id);

    @EntityGraph(attributePaths = {"order", "freelancer", "order.service"})
    @Query("select r from OrderReportEntity r where r.id = :id")
    Optional<OrderReportEntity> findByIdWithOrderAndFreelancerAndService(UUID id);
}
