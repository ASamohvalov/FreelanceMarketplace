package com.srt.FreelanceMarketplace.service.domain.review;

import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewDomainService {
    private final ReviewRepository repository;

    public boolean existsByOrder(OrderEntity order) {
        return repository.existsByOrder(order);
    }

    public boolean existsByServiceAndCustomer(ServiceEntity service, UserEntity customer) {
        return repository.existsByOrder_serviceAndOrder_customer(service, customer);
    }

    public ReviewEntity getByIdWithOrderAndCustomerById(UUID id) {
        return repository.findWithOrderAndCustomerById(id)
                .orElseThrow(() -> new GlobalBadRequestException("the review not found"));
    }

    public ReviewEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("the review not found"));
    }
}
