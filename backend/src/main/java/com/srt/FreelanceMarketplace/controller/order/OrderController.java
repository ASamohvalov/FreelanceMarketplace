package com.srt.FreelanceMarketplace.controller.order;

import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderCustomerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderFreelancerResponse;
import com.srt.FreelanceMarketplace.service.application.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/make")
    public void order(@RequestBody @Valid MakeOrderRequest request) {
        orderService.order(request);
    }

    @GetMapping("/customer/get")
    public List<OrderCustomerResponse> getCustomerOrders() {
        return orderService.getCustomerOrders();
    }

    @GetMapping("/freelancer/get")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public List<OrderFreelancerResponse> getFreelancerOrders() {
        return orderService.getFreelancerOrders();
    }
}
