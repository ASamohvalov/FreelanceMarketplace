package com.srt.FreelanceMarketplace.domain.entities.messages;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "conversation_members")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConversationMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private UserEntity member;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    public ConversationMemberEntity(UserEntity member, ConversationEntity conversation) {
        this.member = member;
        this.conversation = conversation;
    }
}
