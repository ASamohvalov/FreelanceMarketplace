package com.srt.FreelanceMarketplace.domain.entities.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String report;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    @Builder.Default
    private OrderReportStatusEnum status = OrderReportStatusEnum.PENDING;

    @Column(columnDefinition = "TEXT")
    private String customerComment;
}
