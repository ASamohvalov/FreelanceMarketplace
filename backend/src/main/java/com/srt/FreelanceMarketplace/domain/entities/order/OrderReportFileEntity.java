package com.srt.FreelanceMarketplace.domain.entities.order;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "order_report_files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderReportFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String filePath;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_report_id")
    private OrderReportEntity orderReport;
}
