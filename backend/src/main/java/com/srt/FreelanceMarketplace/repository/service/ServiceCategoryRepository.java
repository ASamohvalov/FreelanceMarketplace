package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, UUID> {
    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"subcategory"})
    @Query("select c from ServiceCategoryEntity c")
    List<ServiceCategoryEntity> findAllWithSubcategory();
}
