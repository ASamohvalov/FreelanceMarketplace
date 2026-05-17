package com.srt.FreelanceMarketplace.repository.payment;

import com.srt.FreelanceMarketplace.domain.entities.payment.SystemFinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemFinanceRepository extends JpaRepository<SystemFinanceEntity, Long> {
    @Modifying
    @Query("""
        update SystemFinanceEntity sf
        set sf.totalServiceEarnings = sf.totalServiceEarnings + :amount
        where sf.id = 1
    """)
    void incrementEarnings(long amount);

    @Modifying
    @Query("""
        update SystemFinanceEntity sf
        set sf.totalServiceEarnings = sf.totalServiceEarnings - :amount
        where sf.id = 1
    """)
    void decrementEarnings(long amount);

    @Modifying
    @Query("""
        update SystemFinanceEntity sf
        set sf.currencyRate = :pointRate
        where sf.id = 1
    """)
    void setPointRate(long pointRate);
}
