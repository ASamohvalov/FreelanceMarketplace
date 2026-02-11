package com.srt.FreelanceMarketplace.domain.entities.service;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private FreelancerEntity freelancer;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private ServiceSubcategoryEntity subcategory;

    @Size(max = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Size(min = 100)
    private String description;

    private int price;
    private int deadlineDays;
    private int revisionsCount;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceImageEntity> images;

    // this is crutch
    // null ptr ex - service.getImages().add(some);
    public List<ServiceImageEntity> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }
}
