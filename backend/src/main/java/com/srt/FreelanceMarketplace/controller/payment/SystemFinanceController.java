package com.srt.FreelanceMarketplace.controller.payment;

import com.srt.FreelanceMarketplace.domain.dto.request.finance.SetPointRateRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.payment.SystemFinanceStatisticResponse;
import com.srt.FreelanceMarketplace.service.application.payment.SystemFinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system_finance")
@RequiredArgsConstructor
// only admins
public class SystemFinanceController {
    private final SystemFinanceService systemFinanceService;

    @GetMapping("/get/statistic")
    public SystemFinanceStatisticResponse getStatistic() {
        return systemFinanceService.getStatistic();
    }

    @PatchMapping("/set/point_rate")
    public void setPointRate(@RequestBody @Valid SetPointRateRequest request) {
        systemFinanceService.setPointRate(request);
    }
}
