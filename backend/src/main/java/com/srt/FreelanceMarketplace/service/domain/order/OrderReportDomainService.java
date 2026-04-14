package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReportDomainService {
    private final OrderReportRepository repository;

    public OrderReportEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }

    public OrderReportEntity getByIdWithOrder(UUID id) {
        return repository.findByIdWithOrder(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }

    public OrderReportEntity getByIdWithOrderAndFreelancerAndService(UUID id) {
        return repository.findByIdWithOrderAndFreelancerAndService(id)
                .orElseThrow(() -> new GlobalBadRequestException("no such order report"));
    }
}
