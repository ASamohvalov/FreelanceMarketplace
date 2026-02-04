package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceImageRepository extends JpaRepository<ServiceImageEntity, UUID> {
}
