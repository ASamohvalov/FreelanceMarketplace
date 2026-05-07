package com.srt.FreelanceMarketplace.domain.entities.order;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_requirements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequirementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    @Column(nullable = false)
    private int deadlineDays;

    @OneToMany(mappedBy = "orderRequirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderRequirementFileEntity> files;
}
