package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    boolean existsByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order", "order.service", "order.customer"})
    @Query("select r from ReviewEntity r where r.order.service = :service")
    List<ReviewEntity> findAllByServiceWithAuthor(ServiceEntity service);
}
