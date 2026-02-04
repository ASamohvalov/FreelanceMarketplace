package com.srt.FreelanceMarketplace.domain.entities.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "service_subcategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubcategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @Size(max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategoryEntity category;
}
