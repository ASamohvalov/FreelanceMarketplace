package com.srt.FreelanceMarketplace.domain.entities.order;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "freelancer_id")
    private FreelancerEntity freelancer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String report;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    private boolean accepted;
}
