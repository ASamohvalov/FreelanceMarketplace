package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderRequirementEntity;
import com.srt.FreelanceMarketplace.repository.service.OrderRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRequirementDomainService {
    private final OrderRequirementRepository repository;

    public void save(OrderRequirementEntity entity) {
        repository.save(entity);
    }
}
