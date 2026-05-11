package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderExtensionEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderExtensionDomainService {
    private final OrderExtensionRepository repository;

    public OrderExtensionEntity getByIdWithOrderAndFreelancer(UUID id) {
        return repository.findWithOrderAndFreelancerById(id)
                .orElseThrow(() -> new GlobalBadRequestException("order extension not found"));
    }

    public void save(OrderExtensionEntity entity) {
        repository.save(entity);
    }
}
