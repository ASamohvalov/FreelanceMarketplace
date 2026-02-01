package com.srt.FreelanceMarketplace.domain.entities.messages;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "proposals")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private FreelancerEntity freelancer;

    private String description;

    private boolean isAccepted;
}
