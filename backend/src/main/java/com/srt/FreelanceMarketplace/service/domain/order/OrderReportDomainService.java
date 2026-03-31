package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderReportDomainService {
    private final OrderReportRepository repository;

    public void save(OrderReportEntity entity) {
        repository.save(entity);
    }
}
