package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, UUID> {
    boolean existsByName(String name);
}
