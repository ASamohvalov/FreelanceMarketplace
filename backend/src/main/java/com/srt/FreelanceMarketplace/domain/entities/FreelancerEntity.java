package com.srt.FreelanceMarketplace.domain.entities;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "freelancers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreelancerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    // can be null!!!
    private JobTitleEntity jobTitle;

    @Column(columnDefinition = "TEXT")
    private String aboutYourself;
}
