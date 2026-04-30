package com.srt.FreelanceMarketplace.service.application.payment;

import com.srt.FreelanceMarketplace.domain.dto.response.payment.BalanceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.TransferResponse;
import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.mapper.TransferMapper;
import com.srt.FreelanceMarketplace.repository.payment.AccountRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.AccountDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountDomainService domainService;

    private final AuthHelperService authHelperService;
    private final TransferDomainService transferDomainService;
    private final TransferMapper transferMapper;

    public BalanceRequest getBalance() {
        AccountEntity account = domainService.getByUser(authHelperService.getUser());
        return new BalanceRequest(account.getBalance());
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
}
