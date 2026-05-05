package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    boolean existsByOrder(OrderEntity order);

    boolean existsByOrder_serviceAndOrder_customer(ServiceEntity service, UserEntity customer);

    @EntityGraph(attributePaths = {"order", "order.service", "order.customer"})
    @Query("select r from ReviewEntity r where r.order.service = :service")
    List<ReviewEntity> findAllByServiceWithAuthor(ServiceEntity service);

    @EntityGraph(attributePaths = {"order", "order.service", "order.customer"})
    Optional<ReviewEntity> findFirstByOrder_serviceAndOrder_customer(ServiceEntity service, UserEntity customer);

    @EntityGraph(attributePaths = {"order", "order.customer"})
    Optional<ReviewEntity> findFirstWithAuthorByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order", "order.customer"})
    Optional<ReviewEntity> findWithOrderAndCustomerById(UUID id);
}
