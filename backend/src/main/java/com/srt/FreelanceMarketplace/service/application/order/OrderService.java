package com.srt.FreelanceMarketplace.service.application.order;

import com.srt.FreelanceMarketplace.domain.dto.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.GetOrderDataResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderCustomerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderFreelancerResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.mapper.OrderMapper;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderDomainService domainService;
    private final OrderMapper mapper;

    private final ServiceDomainService serviceDomainService;
    private final AuthHelperService authHelperService;
    private final MessagingService messagingService;
    private final NotificationSenderService notificationSenderService;
    private final FreelancerDomainService freelancerDomainService;
    private final UserMapper userMapper;
    private final TransferDomainService transferDomainService;
    private final FreelanceMapper freelanceMapper;

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
        if (repository.existsByServiceAndCustomer(service, authHelperService.getUser())) {
            throw new GlobalBadRequestException("this order has already been placed");
        }

        OrderEntity order = OrderEntity.builder()
                .deadlineDate(request.getDeadlineDate())
                .service(service)
                .customer(authHelperService.getUser())
                .freelancer(service.getFreelancer())
                .build();
        repository.save(order);

        if (!messagingService.isConversationExists(
                service.getFreelancer(),
                authHelperService.getUser())
        ) {
            messagingService.createConversation(
                    service.getFreelancer(),
                    authHelperService.getUser(),
                    service,
                    order
            );
        } else {
            messagingService.changeConversationType(
                    service.getFreelancer(),
                    authHelperService.getUser(),
                    ConversationTypeEnum.ORDER,
                    order
            );
        }

        transferDomainService.generateHeldTransfer(order);

        notificationSenderService.sendNewOrder(
                order,
                service.getFreelancer().getUser(),
                authHelperService.getUser()
        );
    }

    public List<OrderCustomerResponse> getCustomerOrders() {
        return domainService.findAllByCustomer(authHelperService.getUser()).stream()
                .map(o -> new OrderCustomerResponse(
                        mapper.toResponse(o),
                        serviceDomainService.mapToServiceResponse(o.getService())
                ))
                .toList();
    }

    public List<OrderFreelancerResponse> getFreelancerOrders() {
        FreelancerEntity freelancer = freelancerDomainService.getByUser(authHelperService.getUser());
        return domainService.findAllByFreelancer(freelancer).stream()
                .map(o -> new OrderFreelancerResponse(
                        mapper.toResponse(o),
                        serviceDomainService.mapToServiceOrderInfoResponse(o.getService()),
                        userMapper.entityToUserNameResponse(o.getCustomer())
                ))
                .toList();
    }

    public GetOrderDataResponse getOrderById(UUID id) {
        OrderEntity order = repository.findWithServiceAndCustomerById(id)
                .orElseThrow(() -> new GlobalBadRequestException("order not found"));
        return new GetOrderDataResponse(
                mapper.toResponse(order),
                serviceDomainService.mapToServiceOrderInfoResponse(order.getService()),
                userMapper.entityToUserNameResponse(order.getCustomer()),
                freelanceMapper.freelancerEntityToResponse(order.getFreelancer())
        );
    }

    public void rejectOrder(UUID id) {
        OrderEntity order = domainService.getById(id);
        if (!order.getCustomer().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("only the customer can reject the order");
        }
        order.setStatus(OrderStatusEnum.REJECTED);

        transferDomainService.canselTransferByOrder(order);

        repository.save(order);
    }
}
