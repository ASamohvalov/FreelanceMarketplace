package com.srt.FreelanceMarketplace.domain.entities.order;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "order_requirement_files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequirementFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String filePath;

    @ManyToOne(optional = false)
    private OrderRequirementEntity orderRequirement;
}
