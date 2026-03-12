package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.service.entity.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
