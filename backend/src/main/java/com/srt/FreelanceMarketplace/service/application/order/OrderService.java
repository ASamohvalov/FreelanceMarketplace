package com.srt.FreelanceMarketplace.service.application.order;

import com.srt.FreelanceMarketplace.domain.dto.request.order.ExtendDeadlineRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.*;
import com.srt.FreelanceMarketplace.domain.dto.response.order.requirement.OrderRequirementResponse;
import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderExtensionStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ServiceTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderExtensionEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderRequirementEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.mapper.OrderMapper;
import com.srt.FreelanceMarketplace.mapper.OrderRequirementMapper;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import com.srt.FreelanceMarketplace.repository.service.OrderRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderExtensionDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderRequirementDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderRequirementFileDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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
    private final OrderRequirementDomainService orderRequirementDomainService;
    private final OrderRequirementFileDomainService orderRequirementFileDomainService;
    private final OrderRequirementMapper orderRequirementMapper;
    private final OrderExtensionDomainService orderExtensionDomainService;
    private final OrderReportRepository orderReportRepository;

    @Transactional
    public void order(MakeOrderRequest request) {
        ServiceEntity service = serviceDomainService.getByIdWithAuthor(request.getServiceId());

        if (service.getFreelancer().getUser().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("the user cannot order his own service");
        }
        if (repository.existsByServiceAndCustomer(service, authHelperService.getUser())) {
            throw new GlobalBadRequestException("this order has already been placed");
        }

        OrderEntity order = OrderEntity.builder()
                .service(service)
                .customer(authHelperService.getUser())
                .freelancer(service.getFreelancer())
                .build();
        repository.save(order);

        OrderRequirementEntity orderRequirement = OrderRequirementEntity.builder()
                .order(order)
                .description(request.getDescription())
                .comment(request.getComment())
                .deadlineDays(request.getDeadlineDays())
                .build();
        orderRequirementDomainService.save(orderRequirement);

        if (request.getFiles() != null) {
            var res = orderRequirementFileDomainService.uploadFiles(orderRequirement, request.getFiles());
            orderRequirementFileDomainService.saveAll(res);
        }

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

        if (service.getType() == ServiceTypeEnum.USUAL) {
            transferDomainService.generateHeldTransfer(order);
        }

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

    @Transactional
    public OrderRejectResponse rejectOrder(UUID id) {
        OrderEntity order = domainService.getById(id);

        UserEntity user = authHelperService.getUser();

        if (order.getStatus() == OrderStatusEnum.PENDING) {
            if (!order.getCustomer().getId().equals(user.getId())) {
                throw new GlobalBadRequestException("only the customer can reject the pending order");
            }

            reject(order);
            return new OrderRejectResponse(true);
        } else if (!order.getDeadlineDate().isAfter(Instant.now()) &&
                !orderReportRepository.existsByOrder(order) &&
                order.getCustomer().getId().equals(user.getId())) {
            // if deadline ended & no order report & you are customer
            reject(order);
            return new OrderRejectResponse(true);
        } else {
            Optional<FreelancerEntity> freelancer = freelancerDomainService.findByUser(user);

            if (order.getStatus() == OrderStatusEnum.CANCELLED ||
                    order.getStatus() == OrderStatusEnum.REJECTED ||
                    order.getStatus() == OrderStatusEnum.COMPLETED) {
                throw new GlobalBadRequestException("the order already rejected");
            }

            if (order.getCustomer().getId().equals(user.getId())) {
                order.setRejectByCustomer(true);
                if (order.isRejectByFreelancer()) {
                    reject(order);
                    return new OrderRejectResponse(true);
                }
            } else if (freelancer.isPresent() &&
                    order.getFreelancer().getId().equals(freelancer.get().getId())) {
                order.setRejectByFreelancer(true);
                if (order.isRejectByCustomer()) {
                    reject(order);
                    return new OrderRejectResponse(true);
                }
            } else {
                throw new GlobalBadRequestException("you cannot reject this order");
            }

            order.setStatus(OrderStatusEnum.WAITING_FOR_REJECT);

            repository.save(order);
        }

        return new OrderRejectResponse(false);
    }

    public void cancelOrder(UUID id) {
        OrderEntity order = domainService.getByIdWithCustomer(id);

        if (order.getStatus() != OrderStatusEnum.PENDING) {
            throw new GlobalBadRequestException("the status already changed");
        }

        FreelancerEntity freelancer = freelancerDomainService.getByUser(authHelperService.getUser());
        if (!order.getFreelancer().getId()
                .equals(freelancer.getId())) {
            throw new GlobalBadRequestException("only the freelancer can cancel the order");
        }

        order.setStatus(OrderStatusEnum.CANCELLED);
        repository.save(order);

        notificationSenderService.sendOrderCancelled(
                order, order.getCustomer(), authHelperService.getUser());
    }

    public void acceptOrder(UUID id) {
        OrderEntity order = domainService.getByIdWithRequirementAndCustomer(id);

        if (order.getStatus() != OrderStatusEnum.PENDING) {
            throw new GlobalBadRequestException("the status already changed");
        }

        FreelancerEntity freelancer = freelancerDomainService.getByUser(authHelperService.getUser());
        if (!order.getFreelancer().getId()
                .equals(freelancer.getId())) {
            throw new GlobalBadRequestException("only the freelancer can accept the order");
        }

        Instant deadline = Instant.now().plus(
                order.getOrderRequirement().getDeadlineDays(),
                ChronoUnit.DAYS);
        order.setDeadlineDate(deadline);

        order.setStatus(OrderStatusEnum.IN_PROGRESS);
        repository.save(order);

        notificationSenderService.sendOrderAccepted(
                order, order.getCustomer(), authHelperService.getUser());
    }

    public OrderRequirementResponse getOrderRequirementByOrderId(UUID id) {
        OrderEntity order = domainService.getByIdWithRequirementAndFiles(id);
        UserEntity user = authHelperService.getUser();
        if (!order.getCustomer().getId().equals(user.getId())) {
            if (!order.getFreelancer().getId().equals(freelancerDomainService.getReferenceByUser(user).getId())) {
                throw new GlobalBadRequestException("you don't have rights to view the requirements");
            }
        }
        return orderRequirementMapper.toResponse(order.getOrderRequirement());
    }

    public void extendDeadline(ExtendDeadlineRequest request) {
        OrderEntity order = domainService.getByIdWithFreelancerAndCustomerAndService(request.getOrderId());
        UserEntity user = authHelperService.getUser();

        // if not freelancer
        if (!order.getFreelancer().getUser().getId().equals(user.getId())) {
            throw new GlobalBadRequestException("you don't have rights to extend the deadline");
        }

        if (domainService.endStatus(order.getStatus())) {
            throw new GlobalBadRequestException("order closed");
        }

        OrderExtensionEntity orderExtension = OrderExtensionEntity.builder()
                .daysAdded(request.getDaysAdded())
                .order(order)
                .build();

        orderExtensionDomainService.save(orderExtension);

        notificationSenderService.sendOrderExtendDeadlineRequest(
                orderExtension,
                order.getCustomer(),
                user
        );
    }

    public void acceptExtendDeadline(UUID orderExtensionId) {
        changeOrderExtensionStatus(orderExtensionId, OrderExtensionStatusEnum.ACCEPTED);
    }

    public void rejectExtendDeadline(UUID orderExtensionId) {
        changeOrderExtensionStatus(orderExtensionId, OrderExtensionStatusEnum.REJECTED);
    }

    public Page<GetOrderResponse> getOrders(Pageable pageable) {
        return repository.findAllByOrderByOrderDateDesc(pageable)
                .map(mapper::toGetResponse);
    }

    @Transactional
    public void completeOrder(UUID id) {
        OrderEntity order = domainService.getByIdWithFreelancerAndCustomerAndService(id);
        domainService.completeOrder(order);
    }

    public void rejectOrderByModerator(UUID id) {
        OrderEntity order = domainService.getById(id);
        if (domainService.endStatus(order.getStatus())) {
            throw new GlobalBadRequestException("this order already completed");
        }
        reject(order);
    }

    private void changeOrderExtensionStatus(UUID orderExtensionId, OrderExtensionStatusEnum status) {
        OrderExtensionEntity orderExtension = orderExtensionDomainService
                .getByIdWithOrderAndFreelancer(orderExtensionId);
        UserEntity user = authHelperService.getUser();
        OrderEntity order = orderExtension.getOrder();

        if (!order.getCustomer().getId().equals(user.getId())) {
            throw new GlobalBadRequestException("you don't have rights to extend the deadline");
        }

        orderExtension.setStatus(status);

        if (status == OrderExtensionStatusEnum.ACCEPTED) {
            Instant newDeadline = order.getDeadlineDate().plus(orderExtension.getDaysAdded(), ChronoUnit.DAYS);
            order.setDeadlineDate(newDeadline);
            repository.save(order);

            notificationSenderService.sendOrderExtensionAccepted(
                    orderExtension,
                    order.getFreelancer().getUser(),
                    user
            );
        } else if (status == OrderExtensionStatusEnum.REJECTED) {
            notificationSenderService.sendOrderExtensionRejected(
                    orderExtension,
                    order.getFreelancer().getUser(),
                    user
            );
        }
    }

    private void reject(OrderEntity order) {
        order.setStatus(OrderStatusEnum.REJECTED);
        order.setCompletionDate(Instant.now());

        transferDomainService.canselTransferByOrder(order);

        repository.save(order);
    }
}
