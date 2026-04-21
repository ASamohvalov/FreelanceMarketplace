package com.srt.FreelanceMarketplace.service.domain.review;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewDomainService {
    private final ReviewRepository repository;

    public boolean existsByOrder(OrderEntity order) {
        return repository.existsByOrder(order);
    }
}
