package com.srt.FreelanceMarketplace.service.application.review;

import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.review.EditReviewRequest;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ReviewDomainService domainService;
    private final ReviewMapper mapper;

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

        if (order.getStatus() != OrderStatusEnum.COMPLETED) {
            throw new GlobalBadRequestException("you can't leave a review on an unfinished order");
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

    public void editReview(EditReviewRequest request) {
        ReviewEntity review = domainService.getByIdWithOrderAndCustomerById(request.getReviewId());

        if (!review.getOrder().getCustomer().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("this user don't customer of this order");
        }

        review.setReview(request.getReview());
        review.setRating(request.getRating());
        review.setUpdatedAt(Instant.now());

        repository.save(review);
    }

    public ReviewResponse getReviewByOrder(UUID orderId) {
        OrderEntity order = orderDomainService.getReferenceIfExistsById(orderId);
        Optional<ReviewEntity> review = repository.findFirstWithAuthorByOrder(order);
        return review.isPresent()
                ? mapper.toResponse(review.get())
                : new ReviewResponse();
    }

    public ReviewResponse getPersonalReviewByService(UUID serviceId) {
        ServiceEntity service = serviceDomainService.getReferenceIfExistsById(serviceId);
        var res = repository.findFirstByOrder_serviceAndOrder_customer(service, authHelperService.getUser());
        return res.map(this::mapToResponse).orElseGet(ReviewResponse::new);
    }

    public List<ReviewResponse> getReviewsByService(UUID serviceId) {
        ServiceEntity service = serviceDomainService.getReferenceIfExistsById(serviceId);
        return repository.findAllByServiceWithAuthor(service).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(ReviewEntity review) {
        ReviewResponse response = mapper.toResponse(review);
        response.setAuthor(userMapper.entityToUserNameResponse(review.getOrder().getCustomer()));
        response.setExists(true);
        return response;
    }
}
