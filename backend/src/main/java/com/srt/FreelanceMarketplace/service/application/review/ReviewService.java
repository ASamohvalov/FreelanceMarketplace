package com.srt.FreelanceMarketplace.service.application.review;

import com.srt.FreelanceMarketplace.domain.dto.request.review.SendReviewRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.ReviewMapper;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.ReviewRepository;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.review.ReviewDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ReviewDomainService domainService;
    private final ReviewMapper reviewMapper;

    private final OrderDomainService orderDomainService;
    private final AuthHelperService authHelperService;
    private final ServiceDomainService serviceDomainService;
    private final UserMapper userMapper;

    public void sendReview(SendReviewRequest request) {
        OrderEntity order = orderDomainService.getById(request.getOrderId());

        if (!order.getCustomer().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("this user don't customer of this order");
        }

        if (repository.existsByOrder(order)) {
            throw new GlobalBadRequestException("the review already sent");
        }

        ReviewEntity review = ReviewEntity.builder()
                .rating(request.getRating())
                .review(request.getReview())
                .order(order)
                .build();
        repository.save(review);
    }

    public void editReview(SendReviewRequest request) {
        OrderEntity order = orderDomainService.getById(request.getOrderId());

        if (!order.getCustomer().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("this user don't customer of this order");
        }

        if (repository.existsByOrder(order)) {
            throw new GlobalBadRequestException("the review already sent");
        }

        ReviewEntity review = ReviewEntity.builder()
                .rating(request.getRating())
                .review(request.getReview())
                .order(order)
                .build();
        repository.save(review);
    }

    public List<ReviewResponse> getReviewByService(UUID serviceId) {
        ServiceEntity service = serviceDomainService.getReferenceIfExistsById(serviceId);
        return repository.findAllByServiceWithAuthor(service).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(ReviewEntity review) {
        ReviewResponse response = reviewMapper.toResponse(review);
        response.setAuthor(userMapper.entityToUserNameResponse(review.getOrder().getCustomer()));
        return response;
    }
}
