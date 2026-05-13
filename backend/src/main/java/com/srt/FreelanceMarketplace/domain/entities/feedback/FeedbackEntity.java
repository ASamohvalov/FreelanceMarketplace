package com.srt.FreelanceMarketplace.domain.entities.feedback;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.FeedbackTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private FeedbackTypeEnum type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne(optional = false)
    private UserEntity sender;

    @Column(nullable = false)
    @Builder.Default
    private boolean accepted = false;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
