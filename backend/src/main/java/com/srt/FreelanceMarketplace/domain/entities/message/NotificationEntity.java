package com.srt.FreelanceMarketplace.domain.entities.message;

import com.srt.FreelanceMarketplace.domain.dto.NotificationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;

    @Size(max = 55)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private NotificationTypeEnum type;

    @Size(max = 55)
    private String entityType;

    private UUID entityId;

    // default is NOW()
    private Instant sendAt;

    private Instant readAt;
}
