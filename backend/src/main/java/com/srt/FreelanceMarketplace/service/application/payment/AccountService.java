package com.srt.FreelanceMarketplace.service.application.payment;

import com.srt.FreelanceMarketplace.domain.dto.request.finance.ConvertPointsRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.BalanceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.TransferResponse;
import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.TransferMapper;
import com.srt.FreelanceMarketplace.repository.payment.AccountRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.AccountDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.SystemFinanceDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountDomainService domainService;

    private final AuthHelperService authHelperService;
    private final TransferDomainService transferDomainService;
    private final TransferMapper transferMapper;
    private final SystemFinanceDomainService systemFinanceDomainService;

    public BalanceResponse getBalance() {
        AccountEntity account = domainService.getByUser(authHelperService.getUser());
        return new BalanceResponse(account.getBalance(), account.getNumberOfPoints());
    }

    public List<TransferResponse> getIncomingTransfers() {
        AccountEntity account = domainService.getByUser(authHelperService.getUser());
        return transferDomainService.getWithServiceAndCustomerAllByAccount(account).stream()
                .map(transferMapper::toResponseWithoutCommission)
                .toList();
    }

    public List<TransferResponse> getExpenseTransfers() {
        return transferDomainService.getWithServiceAndCustomerAllBySender(authHelperService.getUser()).stream()
                .map(transferMapper::toResponse)
                .toList();
    }

    public Map<String, Long> getCurrentPointRate() {
        return Map.of("rate", systemFinanceDomainService.getPointRate());
    }

    @Transactional
    public void convertPoints(ConvertPointsRequest request) {
        AccountEntity account = domainService.getByUser(authHelperService.getUser());
        if (account.getNumberOfPoints() < request.getNumberOfPoints())  {
            throw new GlobalBadRequestException("you can't convert more points than you have");
        }

        long currentRate = systemFinanceDomainService.getPointRate();
        long moneyAdded = request.getNumberOfPoints() * currentRate;

        account.setNumberOfPoints(account.getNumberOfPoints() - request.getNumberOfPoints());
        account.setBalance(account.getBalance() + moneyAdded);
        repository.save(account);
    }
}
