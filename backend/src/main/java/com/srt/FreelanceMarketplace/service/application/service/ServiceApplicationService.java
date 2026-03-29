package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.PaymentInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.service.ServiceRepository;
import com.srt.FreelanceMarketplace.service.domain.messaging.ProposalDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.SubcategoryDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceApplicationService {
    private final ServiceDomainService domainService;
    private final ServiceRepository repository;
    private final FreelanceMapper mapper;

    private final FileStorageUtil fileStorageUtil;
    private final FreelancerDomainService freelancerService;
    private final AuthHelperService authHelperService;
    private final SubcategoryDomainService subcategoryService;
    private final ProposalDomainService proposalService;

    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(domainService::mapToServiceResponse)
                .toList();
    }

    public Optional<File> getImage(UUID serviceId) {
        ServiceEntity serviceEntity = domainService.getById(serviceId);
        ServiceImageEntity image = serviceEntity.getTitleImage();
        if (image == null) {
            return Optional.empty();
        }
        return Optional.of(fileStorageUtil.downloadFile(image.getImagePath()));
    }

    public List<ServiceResponse> getAllByFreelancerId(UUID freelancerId) {
        FreelancerEntity freelancer = freelancerService.getById(freelancerId);
        return repository.findAllByFreelancer(freelancer).stream()
                .map(mapper::serviceEntityToResponse)
                .toList();
    }

    public IdentifierDto create(ServiceRequest request) {
        validateFiles(request);

        FreelancerEntity freelancer = freelancerService.getByUserOrElseThrow(authHelperService.getUser());
        ServiceSubcategoryEntity subcategory = subcategoryService.getById(request.getSubcategoryId());

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .freelancer(freelancer)
                .title(request.getTitle())
                .description(request.getDescription())
                .subcategory(subcategory)
                .deadlineDays(request.getDeadlineDays())
                .revisionsCount(request.getRevisionsCount())
                .price(request.getPrice())
                .build();

        processFiles(request, serviceEntity);
        return new IdentifierDto(repository.save(serviceEntity).getId());
    }

    public PaymentInfoResponse getPaymentInfo(UUID serviceId) {
        ServiceEntity service = domainService.getByIdWithAuthor(serviceId);
        return PaymentInfoResponse.builder()
                .serviceId(service.getId())
                .price(service.getPrice())
                .serviceName(service.getTitle())
                .commission(getOrderCommission(service.getPrice()))
                .freelancerFirstName(service.getFreelancer().getUser().getFirstName())
                .freelancerLastName(service.getFreelancer().getUser().getLastName())
                .build();
    }

    public ServiceInfoResponse getResponseById(UUID id) {
        ServiceEntity service = domainService.getById(id);
        ServiceInfoResponse response = mapper.serviceEntityToInfoResponse(service);
        if (authHelperService.isAuthenticated()) {
            response.setProposalBeenSent(proposalService.existsByServiceAndAuthor(service, authHelperService.getUser()));
        }
        return response;
    }

    // for create service

    private void validateFiles(ServiceRequest request) {
        // todo file format
        if (!fileStorageUtil.isValidFile(request.getTitleImage())) {
            throw new GlobalBadRequestException("invalid file format");
        }
        request.getImages().forEach((file) -> {
            if (!fileStorageUtil.isValidFile(file)) {
                throw new GlobalBadRequestException("invalid file format");
            }
        });
    }

    private void processFiles(ServiceRequest request, ServiceEntity service) {
        // image saving
        ServiceImageEntity titleImageEntity = uploadFile(request.getTitleImage(), service);
        service.setTitleImage(titleImageEntity);
        request.getImages().forEach((file) -> {
            service.getImages().add(uploadFile(file, service));
        });
    }

    private ServiceImageEntity uploadFile(MultipartFile file, ServiceEntity service) {
        String imageName = fileStorageUtil.getRandomName(file);
        try {
            fileStorageUtil.uploadFile(file, imageName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return ServiceImageEntity.builder()
                .imagePath(imageName)
                .service(service)
                .build();
    }

    private int getOrderCommission(int servicePrice) {
        return servicePrice / 10;
    }
}
