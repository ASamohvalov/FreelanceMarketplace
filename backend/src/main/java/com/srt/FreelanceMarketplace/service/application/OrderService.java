package com.srt.FreelanceMarketplace.service.application;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    private final ServiceDomainService serviceDomainService;
    private final AuthHelperService authHelperService;

    public void order(UUID serviceId) {
        ServiceEntity service = serviceDomainService.getReferenceIfExistsById(serviceId);
        OrderEntity order = new OrderEntity(service, authHelperService.getUser());
        repository.save(order);
    }
}
