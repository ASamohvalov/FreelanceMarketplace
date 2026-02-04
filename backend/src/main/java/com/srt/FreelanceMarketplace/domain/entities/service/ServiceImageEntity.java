package com.srt.FreelanceMarketplace.domain.entities.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "service_images")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    private boolean isTitleImage;
}
