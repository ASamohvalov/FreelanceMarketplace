package com.srt.FreelanceMarketplace.domain.entities.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "freelancer_id")
    private FreelancerEntity freelancer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subcategory_id")
    private ServiceSubcategoryEntity subcategory;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Size(min = 100)
    private String description;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int deadlineDays;

    @Column(nullable = false)
    private int revisionsCount;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceImageEntity> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_image_id")
    private ServiceImageEntity titleImage;

    @Column(nullable = false)
    @Builder.Default
    private boolean hidden = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    // this is crutch
    // null ptr ex - service.getImages().add(some);
    public List<ServiceImageEntity> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }
}
