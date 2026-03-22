package com.srt.FreelanceMarketplace.domain.entities.message;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false)
    private Instant sendAt;

    @Column(nullable = false)
    private boolean isRead;
}
