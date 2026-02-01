package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    @Query("""
            select s from ServiceEntity s
            join fetch s.freelancer
            """)
    List<ServiceEntity> findAllWithFreelancer();

    List<ServiceEntity> getAllByFreelancer(FreelancerEntity freelancer);
}
