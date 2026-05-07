package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDomainService {
    private final OrderRepository repository;

    public OrderEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public OrderEntity getByIdWithCustomer(UUID id) {
        return repository.findWithCustomerById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public OrderEntity getByIdWithRequirementAndCustomer(UUID id) {
        return repository.findWithOrderRequirementAndCustomerById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public OrderEntity getByIdWithFreelancer(UUID id) {
        return repository.findByIdWithFreelancer(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public OrderEntity getByIdWithFreelancerAndService(UUID id) {
        return repository.findByIdWithFreelancerAndService(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }

    public OrderEntity getReferenceIfExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("such order not found");
        }
        return repository.getReferenceById(id);
    }

    public List<OrderEntity> findAllByFreelancer(FreelancerEntity freelancer) {
        return repository.findAllByFreelancerWithServiceAndCustomer(freelancer);
    }

    public List<OrderEntity> findAllByCustomer(UserEntity user) {
        return repository.findAllByCustomerWithServiceAndFreelancerAndJobTitle(user);
    }

    public Optional<OrderEntity> findLastOrderByCustomer(ServiceEntity service, UserEntity customer) {
        return repository.findFirstByServiceAndCustomerOrderByCompletionDateDesc(service, customer);
    }

    public void save(OrderEntity order) {
        repository.save(order);
    }

    public OrderEntity getByIdWithRequirementAndFiles(UUID id) {
        return repository.findWithOrderRequirementAndFilesById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such order not found"));
    }
}
