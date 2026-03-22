package com.srt.FreelanceMarketplace.domain.entities.message;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isAccepted = false;
}
