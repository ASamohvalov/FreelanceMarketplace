package com.srt.FreelanceMarketplace.service.application.payment;

import com.srt.FreelanceMarketplace.domain.dto.request.finance.SetPointRateRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.SystemFinanceStatisticResponse;
import com.srt.FreelanceMarketplace.domain.entities.payment.SystemFinanceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.payment.AccountRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.SystemFinanceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemFinanceService {

    private final SystemFinanceDomainService systemFinanceDomainService;
    private final AccountRepository accountRepository;

    public SystemFinanceStatisticResponse getStatistic() {
        SystemFinanceEntity systemFinance = systemFinanceDomainService.get();
        Long sumOfAllPoints = accountRepository.findSumPoints();

        return new SystemFinanceStatisticResponse(
                systemFinance.getCurrencyRate(),
                systemFinance.getTotalServiceEarnings(),
                sumOfAllPoints
        );
    }

    public void setPointRate(SetPointRateRequest request) {
        if (request.getPointRate() < 0) {
            throw new GlobalBadRequestException("point rate cannot be null");
        }
        if (maxPossibleRate() < request.getPointRate()) {
            throw new GlobalBadRequestException("max possible rate exceeded");
        }
        systemFinanceDomainService.setPointRate(request.getPointRate());
    }

    public long maxPossibleRate() {
        SystemFinanceStatisticResponse response = getStatistic();
        return response.getTotalPoints() == 0L
                ? 0L : (response.getTotalServiceEarnings() / response.getTotalPoints());
    }
}
