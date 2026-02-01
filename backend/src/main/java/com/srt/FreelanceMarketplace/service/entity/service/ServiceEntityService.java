package com.srt.FreelanceMarketplace.service.entity.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.service.ServiceRepository;
import com.srt.FreelanceMarketplace.service.entity.FreelancerService;
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

    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(freelanceMapper::serviceEntityToResponse)
                .toList();
    }

    @Transactional
    public void create(ServiceRequest request) {
        ServiceEntity serviceEntity = freelanceMapper.serviceRequestToEntity(request);
        FreelancerEntity freelancer = freelancerService.findByUser(authHelperService.getUser())
                .orElseThrow(() -> new IllegalStateException("user has FREELANCER_ROLE but hasn't freelancer entity"));
        serviceEntity.setFreelancer(freelancer);

        if (!fileStorageUtil.isValidFile(request.getTitleImage())) {
            throw new GlobalBadRequestException("invalid file format");
        }
        request.getImages().forEach((file) -> {
            if (!fileStorageUtil.isValidFile(file)) {
                throw new GlobalBadRequestException("invalid file format");
            }
        });

        // image saving
        serviceEntity.getImages().add(
                uploadFile(request.getTitleImage(), serviceEntity, true)
        );
        request.getImages().forEach((file) -> {
            serviceEntity.getImages().add(
                    uploadFile(file, serviceEntity, false)
            );
        });
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
