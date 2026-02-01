package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceSubcategoryRepository extends JpaRepository<ServiceSubcategoryEntity, UUID> {
    boolean existsByName(String name);
}
