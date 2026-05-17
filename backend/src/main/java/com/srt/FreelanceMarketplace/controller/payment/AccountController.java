package com.srt.FreelanceMarketplace.controller.payment;

import com.srt.FreelanceMarketplace.domain.dto.request.finance.ConvertPointsRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.BalanceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.TransferResponse;
import com.srt.FreelanceMarketplace.service.application.payment.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
// authorized users
public class AccountController {
    private final AccountService accountService;

    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/get/balance")
    public BalanceResponse getBalance() {
        return accountService.getBalance();
    }

    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/get/transfer/income")
    public List<TransferResponse> getIncomingTransfers() {
        return accountService.getIncomingTransfers();
    }

    @GetMapping("/get/transfer/expense")
    public List<TransferResponse> getExpenseTransfers() {
        return accountService.getExpenseTransfers();
    }

    @GetMapping("/get/current_point_rate")
    public Map<String, Long> getCurrentPointRate() {
        return accountService.getCurrentPointRate();
    }

    @PatchMapping("/convert/points")
    public void convertPoints(@RequestBody @Valid ConvertPointsRequest request) {
        accountService.convertPoints(request);
    }
}
