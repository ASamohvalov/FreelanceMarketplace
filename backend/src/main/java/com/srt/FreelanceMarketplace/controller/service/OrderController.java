package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.service.entity.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/service/{serviceId}")
    public void order(@PathVariable UUID serviceId) {
        orderService.order(serviceId);
    }
}
