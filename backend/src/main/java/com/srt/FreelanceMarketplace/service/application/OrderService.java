package com.srt.FreelanceMarketplace.service.application;

import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    private final ServiceDomainService serviceDomainService;
    private final AuthHelperService authHelperService;
    private final MessagingService messagingService;

    public void order(MakeOrderRequest request) {
        ServiceEntity service = serviceDomainService.getByIdWithAuthor(request.getServiceId());
        long daysBetween = ChronoUnit.DAYS.between(Instant.now(), request.getDeadlineDate());
        if (daysBetween < 0) {
            throw new GlobalBadRequestException("the deadline should be at least tomorrow");
        }
        if (service.getFreelancer().getUser().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("the user cannot order his own service");
        }
        if (repository.existsByServiceAndUser(service, authHelperService.getUser())) {
            throw new GlobalBadRequestException("this order has already been placed");
        }

        OrderEntity order = OrderEntity.builder()
                .deadlineDate(request.getDeadlineDate())
                .service(service)
                .user(authHelperService.getUser())
                .status(OrderStatusEnum.IN_PROGRESS)
                .orderDate(Instant.now())
                .build();
        repository.save(order);

        messagingService.createConversation(
                service.getFreelancer(),
                authHelperService.getUser(),
                service,
                order
        );
    }
}
