package com.srt.FreelanceMarketplace.domain.entities.payment;


import com.srt.FreelanceMarketplace.domain.dto.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_account_id")
    private AccountEntity recipientAccount;

    @Column(nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    @Builder.Default
    private TransferStatusEnum status = TransferStatusEnum.HELD;

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
