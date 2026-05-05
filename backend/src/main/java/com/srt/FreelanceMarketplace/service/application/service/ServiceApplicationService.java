package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewCheckActionEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewCheckResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.GetOwnServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.PaymentInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.service.ServiceRepository;
import com.srt.FreelanceMarketplace.service.domain.messaging.ProposalDomainService;
import com.srt.FreelanceMarketplace.service.domain.order.OrderDomainService;
import com.srt.FreelanceMarketplace.service.domain.review.ReviewDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceImageDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.SubcategoryDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.CommissionService;
import com.srt.FreelanceMarketplace.util.FileStorageStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceApplicationService {
    private final ServiceDomainService domainService;
    private final ServiceRepository repository;
    private final FreelanceMapper mapper;

    private final FileStorageStrategy imageStorageStrategy;
    private final FreelancerDomainService freelancerService;
    private final AuthHelperService authHelperService;
    private final SubcategoryDomainService subcategoryService;
    private final ProposalDomainService proposalService;
    private final OrderDomainService orderDomainService;
    private final ReviewDomainService reviewDomainService;
    private final CommissionService commissionService;
    private final ServiceImageDomainService serviceImageDomainService;

    public List<ServiceResponse> getAll() {
        return repository.findAllNotHideWithFreelancer().stream()
                .map(domainService::mapToServiceResponse)
                .toList();
    }

    public Optional<Path> getImage(UUID serviceId) {
        ServiceEntity serviceEntity = domainService.getById(serviceId);
        ServiceImageEntity image = serviceEntity.getTitleImage();
        if (image == null) {
            return Optional.empty();
        }
        return Optional.of(imageStorageStrategy.get(image.getImagePath()));
    }

    public List<ServiceResponse> getAllByFreelancerId(UUID freelancerId) {
        FreelancerEntity freelancer = freelancerService.getByIdWithJobTitle(freelancerId);
        return repository.findAllByFreelancer(freelancer).stream()
                .map(domainService::mapToServiceResponse)
                .toList();
    }

    public IdentifierDto create(ServiceRequest request) {
        FreelancerEntity freelancer = freelancerService.getByUserOrElseThrow(authHelperService.getUser());
        ServiceSubcategoryEntity subcategory = subcategoryService.getById(request.getSubcategoryId());

        ServiceEntity service = ServiceEntity.builder()
                .freelancer(freelancer)
                .title(request.getTitle())
                .description(request.getDescription())
                .subcategory(subcategory)
                .deadlineDays(request.getDeadlineDays())
                .revisionsCount(request.getRevisionsCount())
                .price(request.getPrice())
                .build();

        var entityList = serviceImageDomainService
                .uploadImages(service, request.getTitleImage(), request.getImages());

        repository.save(service);
        serviceImageDomainService.saveAll(entityList);

        return new IdentifierDto(service.getId());
    }

    @Transactional
    public IdentifierDto update(ServiceRequest request, UUID serviceId) {
        ServiceEntity service = domainService.getById(serviceId);
        FreelancerEntity freelancer = freelancerService.getByUserOrElseThrow(authHelperService.getUser());

        if (!service.getFreelancer().getId()
                .equals(freelancer.getId())) {
            throw new GlobalBadRequestException("the user does not own this service");
        }

        ServiceSubcategoryEntity subcategory = subcategoryService.getById(request.getSubcategoryId());

        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setSubcategory(subcategory);
        service.setDeadlineDays(request.getDeadlineDays());
        service.setRevisionsCount(request.getRevisionsCount());
        service.setPrice(request.getPrice());

        serviceImageDomainService.deleteImages(service);

        var entityList = serviceImageDomainService
                .uploadImages(service, request.getTitleImage(), request.getImages());

        serviceImageDomainService.saveAll(entityList);
        repository.save(service);

        return new IdentifierDto(service.getId());
    }

    public PaymentInfoResponse getPaymentInfo(UUID serviceId) {
        ServiceEntity service = domainService.getByIdWithAuthor(serviceId);
        return PaymentInfoResponse.builder()
                .serviceId(service.getId())
                .price(service.getPrice())
                .serviceName(service.getTitle())
                .commission(commissionService.getCommission(service.getPrice()))
                .freelancerFirstName(service.getFreelancer().getUser().getFirstName())
                .freelancerLastName(service.getFreelancer().getUser().getLastName())
                .build();
    }

    public ServiceInfoResponse getResponseById(UUID id) {
        ServiceEntity service = domainService.getByIdWithImagesAndSubcategory(id);
        ServiceInfoResponse response = mapper.serviceEntityToInfoResponse(service);

        if (authHelperService.isAuthenticated()) {
            response.setProposalBeenSent(proposalService.existsByServiceAndAuthor(service, authHelperService.getUser()));
        }

        List<UUID> imageIds = service.getImages().stream()
                .map(ServiceImageEntity::getId)
                .toList();

        response.setImageIds(imageIds);
        return response;
    }

    public void hideService(UUID serviceId, boolean hide) {
        ServiceEntity service = domainService.getByIdWithAuthor(serviceId);
        if (!authHelperService.getUser().getId()
                .equals(service.getFreelancer().getUser().getId())) {
            throw new GlobalBadRequestException("the user does not own this service");
        }
        service.setHidden(hide);
        repository.save(service);
    }

    public List<GetOwnServiceResponse> getAllOwnServices() {
        return repository.findAllByFreelancer(freelancerService.getByUser(authHelperService.getUser())).stream()
                .map(domainService::mapToGetOwnService)
                .toList();
    }

    public ReviewCheckResponse reviewCheck(UUID serviceId) {
        ServiceEntity service = domainService.getReferenceIfExistsById(serviceId);
        Optional<OrderEntity> order = orderDomainService.findLastOrderByCustomer(
                service, authHelperService.getUser());

        if (order.isEmpty() || order.get().getStatus() != OrderStatusEnum.COMPLETED) {
            return new ReviewCheckResponse(false, ReviewCheckActionEnum.NONE, null);
        }
        ReviewCheckActionEnum action = reviewDomainService.existsByServiceAndCustomer(service, authHelperService.getUser())
                ? ReviewCheckActionEnum.EDIT
                : ReviewCheckActionEnum.CREATE;
        return new ReviewCheckResponse(true, action, order.get().getId());
    }

    public Page<ServiceResponse> getMostPopularServices(Pageable pageable) {
        return repository.findAllMostPopular(pageable)
                .map(domainService::mapToServiceResponse);
    }
}
