package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProposalRepository extends JpaRepository<ProposalEntity, UUID> {
    boolean existsByServiceAndAuthor(ServiceEntity service, UserEntity author);

    @Query("""
            select p from ProposalEntity p
            join fetch p.author
            join fetch p.service s
            join s.freelancer f
            where f = :freelancer
            and p.isAccepted = false
            """)
    List<ProposalEntity> findAllNotAcceptedByFreelancerWithAuthor(FreelancerEntity freelancer);
}
