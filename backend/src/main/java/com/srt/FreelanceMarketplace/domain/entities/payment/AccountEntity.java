package com.srt.FreelanceMarketplace.domain.entities.payment;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // in kopeck (rubles * 100)
    @Column(nullable = false)
    private long balance = 0;

    public AccountEntity(UserEntity user) {
        this.user = user;
    }
}
