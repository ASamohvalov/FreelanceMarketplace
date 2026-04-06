package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDomainService {
    private final OrderRepository repository;

    public OrderEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public List<OrderEntity> findAllByFreelancer(FreelancerEntity freelancer) {
        return repository.findAllByFreelancerWithServiceAndCustomer(freelancer);
    }

    public List<OrderEntity> findAllByCustomer(UserEntity user) {
        return repository.findAllByCustomerWithServiceAndFreelancerAndJobTitle(user);
    }
}
