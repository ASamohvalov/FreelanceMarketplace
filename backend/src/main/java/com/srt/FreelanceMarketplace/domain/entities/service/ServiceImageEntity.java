package com.srt.FreelanceMarketplace.domain.entities.service;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "service_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
}
