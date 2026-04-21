package com.srt.FreelanceMarketplace.domain.entities.message;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String review;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
