package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
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
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("""
            select count(o) > 0 from OrderEntity o
            where o.service = :service
            and o.customer = :customer
            and o.status = "IN_PROGRESS"
            """)
    boolean existsByServiceAndCustomer(ServiceEntity service, UserEntity customer);

    @EntityGraph(attributePaths = {"service", "customer", "service.titleImage"})
    @Query("select o from OrderEntity o where o.freelancer = :freelancer order by o.orderDate desc")
    List<OrderEntity> findAllByFreelancerWithServiceAndCustomer(FreelancerEntity freelancer);

    @EntityGraph(attributePaths = {"service", "service.titleImage", "freelancer.jobTitle", "freelancer.user"})
    @Query("select o from OrderEntity o where o.customer = :customer order by o.orderDate desc")
    List<OrderEntity> findAllByCustomerWithServiceAndFreelancerAndJobTitle(UserEntity customer);

    @EntityGraph(attributePaths = {"freelancer"})
    @Query("select o from OrderEntity o where o.id = :id")
    Optional<OrderEntity> findByIdWithFreelancer(UUID id);

    @EntityGraph(attributePaths = {"freelancer", "service"})
    @Query("select o from OrderEntity o where o.id = :id")
    Optional<OrderEntity> findByIdWithFreelancerAndService(UUID id);

    Optional<OrderEntity> findFirstByServiceAndCustomerOrderByCompletionDateDesc(
            ServiceEntity service, UserEntity customer);

    @EntityGraph(attributePaths = {"customer", "service.titleImage", "service.freelancer.user", "service.freelancer.jobTitle"})
    Optional<OrderEntity> findWithServiceAndCustomerById(UUID id);

    @EntityGraph(attributePaths = {"orderRequirement", "customer"})
    Optional<OrderEntity> findWithOrderRequirementAndCustomerById(UUID id);

    @EntityGraph(attributePaths = {"orderRequirement.files"})
    Optional<OrderEntity> findWithOrderRequirementAndFilesById(UUID id);

    @EntityGraph(attributePaths = {"customer"})
    Optional<OrderEntity> findWithCustomerById(UUID id);

    int countByFreelancer(FreelancerEntity freelancer);
}
