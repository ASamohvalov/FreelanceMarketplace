package com.srt.FreelanceMarketplace.service.application.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.order.SendOrderReportRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.report.ReceivedOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.report.SentOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.SendRejectOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.OrderReportMapper;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.service.OrderReportRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderReportDomainService;
import com.srt.FreelanceMarketplace.service.domain.payment.TransferDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.UserDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    private final UserDomainService userDomainService;
    private final TransferDomainService transferDomainService;

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

        UserEntity customer = userDomainService.getReferenceById(order.getCustomer().getId());

        OrderReportEntity report = OrderReportEntity.builder()
                .title(request.getTitle())
                .report(request.getReport())
                .freelancer(freelancer)
                .order(order)
                .customer(customer)
                .build();

        order.setStatus(OrderStatusEnum.SUBMITTED);

        repository.save(report);
        orderDomainService.save(order);

        notificationSenderService.sendNewOrderReport(report, order.getCustomer(), authHelperService.getUser());
    }

    public List<ReceivedOrderReportResponse> getReceivedReportsByOrder(UUID orderId) {
        OrderEntity order = orderDomainService.getReferenceIfExistsById(orderId);
        return repository.findAllWithFreelancerByOrder(order).stream()
                .map(mapper::toReceivedResponse)
                .toList();
    }

    public List<ReceivedOrderReportResponse> getReceivedReports() {
        return repository.findAllWithFreelancerByCustomerOrderByCreatedAtDesc(authHelperService.getUser()).stream()
                .map(mapper::toReceivedResponse)
                .toList();
    }

    public List<SentOrderReportResponse> getSentReports() {
        return repository.findAllWithCustomerByFreelancerOrderByCreatedAtDesc(
                        freelancerDomainService.getByUser(authHelperService.getUser())).stream()
                .map(mapper::toSentResponse)
                .toList();
    }

    @Transactional
    public void acceptReport(UUID reportId, SendRejectOrderReportResponse response) {
        OrderReportEntity report = domainService.getByIdWithOrderAndFreelancerAndService(reportId);

        validateResponseOnOrderRequest(report);

        report.setStatus(OrderReportStatusEnum.ACCEPTED);
        report.getOrder().setStatus(OrderStatusEnum.COMPLETED);
        report.getOrder().setCompletionDate(Instant.now());
        report.setCustomerComment(response.getComment());
        repository.save(report);

        TransferEntity transfer = transferDomainService.getTransferByOrder(report.getOrder());
        transferDomainService.completeTransfer(transfer);

        notificationSenderService.sendOrderCompleted(
                report.getOrder(),
                report.getFreelancer().getUser(),
                authHelperService.getUser()
        );

        notificationSenderService.sendMoneyTransferred(
                transfer,
                report.getFreelancer().getUser(),
                authHelperService.getUser()
        );
    }

    @Transactional
    public void rejectReport(UUID reportId, SendRejectOrderReportResponse response) {
        OrderReportEntity report = domainService.getByIdWithOrderAndFreelancerAndService(reportId);

        validateResponseOnOrderRequest(report);

        report.setStatus(OrderReportStatusEnum.REJECTED);
        report.getOrder().setStatus(OrderStatusEnum.IN_PROGRESS);
        report.setCustomerComment(response.getComment());

        repository.save(report);

        notificationSenderService.sendReportRejected(
                report,
                report.getFreelancer().getUser(),
                authHelperService.getUser()
        );
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
