package com.srt.FreelanceMarketplace.service.infrastructure;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderReportDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderScheduleService {
    private final OrderReportRepository orderReportRepository;
    private final OrderReportDomainService orderReportDomainService;

    @Scheduled(fixedRate = 60_000)
    public void acceptExpiredReports() {
        Instant minDate = Instant.now().minus(3, ChronoUnit.DAYS);
        var reportList = orderReportRepository
                .findWithOrderAndServiceAndCustomerAllByCreatedAtLessThanAndStatus(
                        minDate, OrderReportStatusEnum.PENDING
                );

        if (!reportList.isEmpty()) {
            log.info("[schedule] accept report call");
        }

        reportList.forEach(report ->
                orderReportDomainService.acceptOrderReport(report, ""));
    }
}
