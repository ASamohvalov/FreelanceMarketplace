package com.srt.FreelanceMarketplace.repository.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    @Query("""
            select s from ServiceEntity s
            join fetch s.freelancer
            where s.hidden = false
            and s.deleted = false
            order by s.updatedAt asc
            """)
    List<ServiceEntity> findAllNotHideWithFreelancer();

    @EntityGraph(attributePaths = {"freelancer.user", "freelancer.jobTitle", "titleImage"})
    @Query("""
            select s from ServiceEntity s
            where s.hidden = false
            and s.deleted = false
            order by (select count(o) from OrderEntity o where o.service = s) desc
            """)
    Page<ServiceEntity> findAllMostPopular(Pageable pageable);

    @EntityGraph(attributePaths = {"freelancer.user", "freelancer.jobTitle", "titleImage"})
    List<ServiceEntity> findAllByFreelancerAndHiddenFalse(FreelancerEntity freelancer);

    @EntityGraph(attributePaths = {"freelancer", "freelancer.user"})
    Optional<ServiceEntity> findWithFreelancerByIdAndHiddenFalseAndDeletedFalse(UUID id);

    @EntityGraph(attributePaths = {"images", "subcategory.category", "freelancer.user", "freelancer.jobTitle"})
    Optional<ServiceEntity> findWithImagesAndSubcategoryAndFreelancerById(UUID id);

    boolean existsBySubcategory(ServiceSubcategoryEntity subcategory);

    boolean existsBySubcategory_category(ServiceCategoryEntity category);
}
