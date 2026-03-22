package com.srt.FreelanceMarketplace.domain.entities.message;

import com.srt.FreelanceMarketplace.domain.dto.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

@Entity
@Table(name = "conversations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // nullable
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private ConversationTypeEnum type;
}
