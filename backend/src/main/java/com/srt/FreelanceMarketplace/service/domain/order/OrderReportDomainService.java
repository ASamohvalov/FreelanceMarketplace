package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReportDomainService {
    private final OrderReportRepository repository;

    private final OrderDomainService orderDomainService;

    /**
     * @param report required with order, freelancer and customer
     */
    @Transactional
    public void acceptOrderReport(OrderReportEntity report, String customerComment) {
        report.setStatus(OrderReportStatusEnum.ACCEPTED);
        report.setCustomerComment(customerComment);

        orderDomainService.completeOrder(report.getOrder());
    }

    public OrderReportEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }

    public OrderReportEntity getByIdWithOrderAndService(UUID id) {
        return repository.findByIdWithOrderAndService(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }

    public OrderReportEntity getByIdWithOrderAndFreelancerAndService(UUID id) {
        return repository.findByIdWithOrderAndFreelancerAndService(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }
}
