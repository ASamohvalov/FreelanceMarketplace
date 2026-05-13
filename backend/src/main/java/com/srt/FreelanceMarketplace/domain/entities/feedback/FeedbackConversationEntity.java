package com.srt.FreelanceMarketplace.domain.entities.feedback;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "feedback_conversations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FeedbackConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private ConversationEntity conversation;

    @ManyToOne(optional = false)
    private FeedbackEntity feedback;
}
