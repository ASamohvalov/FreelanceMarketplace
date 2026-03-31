package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.order.SendOrderReportRequest;
import com.srt.FreelanceMarketplace.service.application.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/make")
    public void order(@RequestBody @Valid MakeOrderRequest request) {
        orderService.order(request);
    }

    @PostMapping("/report/send")
    public void sendReport(@RequestBody @Valid SendOrderReportRequest request) {
        orderService.sendReport(request);
    }
}
