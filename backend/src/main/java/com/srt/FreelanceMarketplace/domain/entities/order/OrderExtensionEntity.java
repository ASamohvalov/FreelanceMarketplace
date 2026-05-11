package com.srt.FreelanceMarketplace.domain.entities.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderExtensionStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.UUID;

// запос на продление дедлайна заказа
@Entity
@Table(name = "order_extensions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private OrderEntity order;

    @Column(nullable = false)
    private int daysAdded;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    @Builder.Default
    private OrderExtensionStatusEnum status = OrderExtensionStatusEnum.PENDING;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
