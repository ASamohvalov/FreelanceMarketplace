package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.PaymentInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.messaging.ProposalRepository;
import com.srt.FreelanceMarketplace.repository.service.ServiceRepository;
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
public class ServiceDomainService {
    private final ServiceRepository repository;
    private final FreelanceMapper mapper;

    private final FreelancerDomainService freelancerService;
    private final AuthHelperService authHelperService;
    private final FileStorageUtil fileStorageUtil;
    private final SubcategoryDomainService subcategoryDomainService;
    private final ProposalRepository proposalRepository; // crutch

    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(s -> {
                    ServiceResponse res = mapper.serviceEntityToResponse(s);
                    if (s.getTitleImage() != null) {
                        res.setImageURL(getImageURL(s));
                    }
                    return res;
                })
                .toList();
    }

    public Optional<File> getImage(UUID serviceId) {
        ServiceEntity serviceEntity = getById(serviceId);
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

    public ServiceEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such service id not found"));
    }

    public ServiceEntity getByIdWithAuthor(UUID id) {
        return repository.findByIdWithFreelancer(id)
                .orElseThrow(() -> new GlobalBadRequestException("such service id not found"));
    }

    public ServiceEntity getReferenceIfExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("such service id not found");
        }
        return repository.getReferenceById(id);
    }

    public ServiceInfoResponse getResponseById(UUID id) {
        ServiceEntity service = getById(id);
        ServiceInfoResponse response = mapper.serviceEntityToInfoResponse(service);
        if (authHelperService.isAuthenticated()) {
            response.setProposalBeenSent(proposalRepository.existsByServiceAndAuthor(service, authHelperService.getUser()));
        }
        return response;
    }

    public void create(ServiceRequest request) {
        validateFiles(request);

        FreelancerEntity freelancer = freelancerService.getByUserOrElseThrow(authHelperService.getUser());
        ServiceSubcategoryEntity subcategory = subcategoryDomainService.getById(request.getSubcategoryId());

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
        repository.save(serviceEntity);
    }

    public List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity) {
        return repository.findAllByFreelancer(entity).stream()
                .map(mapper::entityToUserServiceResponse)
                .toList();
    }

    public PaymentInfoResponse getPaymentInfo(UUID serviceId) {
        ServiceEntity service = getByIdWithAuthor(serviceId);
        return PaymentInfoResponse.builder()
                .serviceId(service.getId())
                .price(service.getPrice())
                .serviceName(service.getTitle())
                .commission(getOrderCommission(service.getPrice()))
                .freelancerFirstName(service.getFreelancer().getUser().getFirstName())
                .freelancerLastName(service.getFreelancer().getUser().getLastName())
                .build();
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

    // TODO!!!!
    private String getImageURL(ServiceEntity service) {
        return "localhost:8080/api/service/image/title/" + service.getId();
    }
}
