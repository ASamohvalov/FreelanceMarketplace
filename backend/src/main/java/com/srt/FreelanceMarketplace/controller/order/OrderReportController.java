package com.srt.FreelanceMarketplace.controller.order;

import com.srt.FreelanceMarketplace.domain.dto.request.order.SendOrderReportRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderReportResponse;
import com.srt.FreelanceMarketplace.service.application.order.OrderReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order/report")
@RequiredArgsConstructor
public class OrderReportController {
    private final OrderReportService orderReportService;

    @PostMapping("/send")
    public void sendReport(@RequestBody @Valid SendOrderReportRequest request) {
        orderReportService.sendReport(request);
    }

    @GetMapping("/get/{orderId}")
    public List<OrderReportResponse> getReports(@PathVariable UUID orderId) {
        return orderReportService.getReports(orderId);
    }
}
