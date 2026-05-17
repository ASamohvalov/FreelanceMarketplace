package com.srt.FreelanceMarketplace.domain.entities.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "system_finance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SystemFinanceEntity {
    @Id
    private Long id = 1L;

    // in kopeck
    @Column(nullable = false)
    private Long currencyRate = 0L;

    // in kopeck
    @Column(nullable = false)
    private Long totalServiceEarnings = 0L;

    @Version
    private Long version;
}
