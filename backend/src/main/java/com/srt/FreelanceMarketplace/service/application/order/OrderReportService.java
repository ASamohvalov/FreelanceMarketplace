package com.srt.FreelanceMarketplace.service.application.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.order.SendOrderReportRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.SendRejectOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.OrderReportMapper;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderReportDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReportService {
    private final OrderReportDomainService domainService;
    private final OrderReportRepository repository;
    private final OrderReportMapper mapper;

    private final FreelancerDomainService freelancerDomainService;
    private final AuthHelperService authHelperService;
    private final OrderDomainService orderDomainService;
    private final NotificationSenderService notificationSenderService;

    public void sendReport(SendOrderReportRequest request) {
        FreelancerEntity freelancer = freelancerDomainService.getByUser(authHelperService.getUser());
        OrderEntity order = orderDomainService.getByIdWithFreelancerAndService(request.getOrderId());

        if (order.getStatus() == OrderStatusEnum.SUBMITTED ||
            order.getStatus() == OrderStatusEnum.COMPLETED) {
            throw new GlobalBadRequestException("the report has already been sent");
        }

        if (!freelancer.getId()
                .equals(order.getFreelancer().getId())) {
            throw new GlobalBadRequestException("the user does not own this service");
        }

        OrderReportEntity report = OrderReportEntity.builder()
                .report(request.getReport())
                .freelancer(freelancer)
                .order(order)
                .build();

        order.setStatus(OrderStatusEnum.SUBMITTED);

        repository.save(report);
        orderDomainService.save(order);

        notificationSenderService.sendNewOrderReport(report, order.getCustomer(), authHelperService.getUser());
    }

    public List<OrderReportResponse> getReports(UUID orderId) {
        OrderEntity order = orderDomainService.getReferenceIfExistsById(orderId);
        return repository.findAllByOrder(order).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public void acceptReport(UUID reportId) {
        OrderReportEntity report = domainService.getByIdWithOrderAndFreelancerAndService(reportId);

        validateResponseOnOrderRequest(report);

        report.setStatus(OrderReportStatusEnum.ACCEPTED);
        report.getOrder().setStatus(OrderStatusEnum.COMPLETED);
        repository.save(report);

        notificationSenderService.sendOrderCompleted(
                report.getOrder(),
                report.getFreelancer().getUser(),
                authHelperService.getUser()
        );
    }

    public void rejectReport(UUID reportId, SendRejectOrderReportResponse response) {
        OrderReportEntity report = domainService.getByIdWithOrder(reportId);

        validateResponseOnOrderRequest(report);

        report.setStatus(OrderReportStatusEnum.REJECTED);
        report.setCustomerComment(response.getComment());

        repository.save(report);
    }

    private void validateResponseOnOrderRequest(OrderReportEntity report) {
        if (!authHelperService.getUser().getId()
                .equals(report.getOrder().getCustomer().getId())) {
            throw new GlobalBadRequestException("this user is not the consumer of this order");
        }

        if (!report.getStatus().equals(OrderReportStatusEnum.PENDING)) {
            throw new GlobalBadRequestException("a response to this report has already been sent");
        }
    }
}
