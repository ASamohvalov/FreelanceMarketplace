package com.srt.FreelanceMarketplace.controller.order;

import com.srt.FreelanceMarketplace.domain.dto.request.order.SendOrderReportRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.report.ReceivedOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.report.SentOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.SendRejectOrderReportResponse;
import com.srt.FreelanceMarketplace.service.application.order.OrderReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order/report")
@RequiredArgsConstructor
public class OrderReportController {
    private final OrderReportService orderReportService;

    @PostMapping("/send")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void sendReport(@RequestBody @Valid SendOrderReportRequest request) {
        orderReportService.sendReport(request);
    }

    @GetMapping("/received/get/{orderId}")
    public List<ReceivedOrderReportResponse> getReceivedReportsByOrder(@PathVariable UUID orderId) {
        return orderReportService.getReceivedReportsByOrder(orderId);
    }

    @GetMapping("/received/get")
    public List<ReceivedOrderReportResponse> getReceivedReports() {
        return orderReportService.getReceivedReports();
    }

    @GetMapping("/sent/get")
    public List<SentOrderReportResponse> getSentReports() {
        return orderReportService.getSentReports();
    }

    @PatchMapping("/accept/{reportId}")
    public void acceptReport(
            @PathVariable UUID reportId,
            @RequestBody SendRejectOrderReportResponse response) {
        orderReportService.acceptReport(reportId, response);
    }

    @PatchMapping("/reject/{reportId}")
    public void rejectReport(
            @PathVariable UUID reportId,
            @RequestBody SendRejectOrderReportResponse response) {
        orderReportService.rejectReport(reportId, response);
    }
}
