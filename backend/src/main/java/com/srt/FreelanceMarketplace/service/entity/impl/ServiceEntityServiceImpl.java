package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.ServiceRepository;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import com.srt.FreelanceMarketplace.service.entity.FreelancerService;
import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository repository;
    private final FreelanceMapper freelanceMapper;
    private final FreelancerService freelancerService;
    private final AuthHelperService authHelperService;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(freelanceMapper::serviceEntityToResponse)
                .toList();
    }

    @Override
    public void create(ServiceRequest request) {
        ServiceEntity serviceEntity = freelanceMapper.serviceRequestToEntity(request);
        FreelancerEntity freelancer = freelancerService.findByUser(authHelperService.getUser())
                .orElseThrow(() -> new IllegalStateException("user has FREELANCER_ROLE but hasn't freelancer entity"));
        serviceEntity.setFreelancer(freelancer);

        // image saving
        if (!fileStorageUtil.isValidFile(request.getImage())) {
            throw new GlobalBadRequestException("invalid file format");
        }

        String imageName = fileStorageUtil.getRandomName(request.getImage());
        try {
            fileStorageUtil.uploadFile(request.getImage(), imageName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        serviceEntity.setImagePath(imageName);
        repository.save(serviceEntity);
    }

    @Override
    public List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity) {
        return repository.getAllByFreelancer(entity).stream()
                .map(freelanceMapper::entityToUserServiceResponse)
                .toList();
    }

    @Override
    public byte[] getImage(UUID serviceId) {
        ServiceEntity serviceEntity = repository.findById(serviceId)
                .orElseThrow(() -> new GlobalBadRequestException("such id not found"));
        try {
            return fileStorageUtil.downloadFile(serviceEntity.getImagePath());
        } catch (IOException e) {
            throw new IllegalStateException("some error with downloading file from disk - " + e);
        }
    }
}
