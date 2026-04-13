package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReportEntity, UUID> {
    List<OrderReportEntity> findAllByOrder(OrderEntity order);

    boolean existsByOrderAndAccepted(OrderEntity order, boolean accepted);
}
