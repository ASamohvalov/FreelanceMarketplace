package com.srt.FreelanceMarketplace.controller.order;

import com.srt.FreelanceMarketplace.domain.dto.request.order.ExtendDeadlineRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.order.MakeOrderRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.GetOrderDataResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderCustomerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderFreelancerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.requirement.OrderRequirementResponse;
import com.srt.FreelanceMarketplace.service.application.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/make", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void order(@ModelAttribute @Valid MakeOrderRequest request) {
        orderService.order(request);
    }

    @GetMapping("/get/{id}")
    public GetOrderDataResponse getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
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

    @PatchMapping("/reject/{id}")
    public void rejectOrder(@PathVariable UUID id) {
        orderService.rejectOrder(id);
    }

    @PatchMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void cancelOrder(@PathVariable UUID id) {
        orderService.cancelOrder(id);
    }

    @PatchMapping("/accept/{id}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void acceptOrder(@PathVariable UUID id) {
        orderService.acceptOrder(id);
    }

    @GetMapping("/requirement/get/{orderId}")
    public OrderRequirementResponse getOrderRequirementByOrderId(@PathVariable UUID orderId) {
        return orderService.getOrderRequirementByOrderId(orderId);
    }

    // продлить может только
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PostMapping("/extend/deadline")
    public void extendDeadline(@RequestBody @Valid ExtendDeadlineRequest request) {
        orderService.extendDeadline(request);
    }

    @PatchMapping("/extend/deadline/{orderExtendId}/accept")
    public void acceptExtendDeadline(@PathVariable UUID orderExtendId) {
        orderService.acceptExtendDeadline(orderExtendId);
    }

    @PatchMapping("/extend/deadline/{orderExtendId}/reject")
    public void rejectExtendDeadline(@PathVariable UUID orderExtendId) {
        orderService.rejectExtendDeadline(orderExtendId);
    }
}
