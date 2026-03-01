package com.srt.FreelanceMarketplace.service.entity.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
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
import com.srt.FreelanceMarketplace.service.entity.user.FreelancerService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceEntityService {
    private final ServiceRepository repository;

    private final FreelanceMapper freelanceMapper;
    private final FreelancerService freelancerService;
    private final AuthHelperService authHelperService;
    private final FileStorageUtil fileStorageUtil;
    private final SubcategoryService subcategoryService;
    private final ProposalRepository proposalRepository; // crutch

    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(freelanceMapper::serviceEntityToResponse)
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
        ServiceInfoResponse response = freelanceMapper.serviceEntityToInfoResponse(service);
        if (authHelperService.isAuthenticated()) {
            response.setProposalBeenSent(proposalRepository.existsByServiceAndAuthor(service, authHelperService.getUser()));
        }
        return response;
    }

    public void create(ServiceRequest request) {
        validateFiles(request);

        FreelancerEntity freelancer = freelancerService.findByUser(authHelperService.getUser())
                .orElseThrow(() -> new IllegalStateException("user has FREELANCER_ROLE but hasn't freelancer entity"));
        ServiceSubcategoryEntity subcategory = subcategoryService.getById(request.getSubcategoryId());

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .freelancer(freelancer)
                .title(request.getTitle())
                .description(request.getDescription())
                .subcategory(subcategory)
                .deadlineDays(request.getDeadlineDays())
                .revisionsCount(request.getRevisionsCount())
                .build();

        processFiles(request, serviceEntity);
        repository.save(serviceEntity);
    }

    public List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity) {
        return repository.getAllByFreelancer(entity).stream()
                .map(freelanceMapper::entityToUserServiceResponse)
                .toList();
    }

    @Transactional
    public byte[] getImage(UUID serviceId) {
        ServiceEntity serviceEntity = repository.findById(serviceId)
                .orElseThrow(() -> new GlobalBadRequestException("such id not found"));
        try {
            ServiceImageEntity image = serviceEntity.getImages().stream()
                    .filter(ServiceImageEntity::isTitleImage)
                    .findAny().orElseThrow(() -> new IllegalStateException("not found title image in service"));
            return fileStorageUtil.downloadFile(image.getImagePath());
        } catch (IOException e) {
            throw new IllegalStateException("some error with downloading file from disk - " + e);
        }
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
        service.getImages().add(
                uploadFile(request.getTitleImage(), service, true)
        );
        request.getImages().forEach((file) -> {
            service.getImages().add(
                    uploadFile(file, service, false)
            );
        });
    }

    private ServiceImageEntity uploadFile(MultipartFile file, ServiceEntity service, boolean isTitle) {
        String imageName = fileStorageUtil.getRandomName(file);
        try {
            fileStorageUtil.uploadFile(file, imageName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return ServiceImageEntity.builder()
                .imagePath(imageName)
                .service(service)
                .isTitleImage(isTitle)
                .build();
    }
}
