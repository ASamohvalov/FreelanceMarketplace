package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ServiceTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.AccountDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDomainService {
    private final OrderRepository repository;

    private final TransferDomainService transferDomainService;
    private final NotificationSenderService notificationSenderService;
    private final AccountDomainService accountDomainService;

    public void completeOrder(OrderEntity order) {
        if (endStatus(order.getStatus())) {
            throw new GlobalBadRequestException("this order already completed");
        }

        order.setStatus(OrderStatusEnum.COMPLETED);
        order.setCompletionDate(Instant.now());
        repository.save(order);

        notificationSenderService.sendOrderCompleted(
                order,
                order.getFreelancer().getUser(),
                order.getCustomer()
        );

        if (order.getService().getType() == ServiceTypeEnum.USUAL) {
            TransferEntity transfer = transferDomainService.getTransferByOrder(order);
            transferDomainService.completeTransfer(transfer);

            notificationSenderService.sendMoneyTransferred(
                    transfer,
                    order.getFreelancer().getUser(),
                    order.getCustomer()
            );
        } else {
            accountDomainService.incrementPointsCount(
                    accountDomainService.getByUser(order.getFreelancer().getUser())
            );
        }
    }

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

    public OrderEntity getByIdWithFreelancerAndCustomerAndService(UUID id) {
        return repository.findWithFreelancerAndCustomerById(id)
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

    public boolean endStatus(OrderStatusEnum status) {
        return status == OrderStatusEnum.CANCELLED ||
                status == OrderStatusEnum.COMPLETED ||
                status == OrderStatusEnum.REJECTED;
    }
}
