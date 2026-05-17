package com.srt.FreelanceMarketplace.service.domain.payment;

import com.srt.FreelanceMarketplace.domain.entities.payment.SystemFinanceEntity;
import com.srt.FreelanceMarketplace.repository.payment.SystemFinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemFinanceDomainService {
    private final SystemFinanceRepository systemFinanceRepository;

    public SystemFinanceEntity get() {
        return systemFinanceRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("system finance entity not found"));
    }

    public long getPointRate() {
        return systemFinanceRepository.findById(1L)
                .map(SystemFinanceEntity::getCurrencyRate)
                .orElseThrow(() -> new IllegalStateException("system finance entity not found"));
    }

    public long getTotalEarnings() {
        return systemFinanceRepository.findById(1L)
                .map(SystemFinanceEntity::getTotalServiceEarnings)
                .orElseThrow(() -> new IllegalStateException("system finance entity not found"));
    }

    @Transactional
    public void setPointRate(long pointRate) {
        systemFinanceRepository.setPointRate(pointRate);
    }

    public void incrementEarnings(long amount) {
        systemFinanceRepository.incrementEarnings(amount);
    }

    public void decrementEarnings(long amount) {
        systemFinanceRepository.decrementEarnings(amount);
    }
}
