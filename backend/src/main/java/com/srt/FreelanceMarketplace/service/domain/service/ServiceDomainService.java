package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.domain.dto.response.service.GetOwnServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceOrderInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceDomainService {
    private final ServiceRepository repository;
    private final FreelanceMapper mapper;

    private final ServiceImageDomainService serviceImageService;

    public ServiceEntity getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    public boolean existsByCategory(ServiceCategoryEntity category) {
        return repository.existsBySubcategory_category(category);
    }

    public boolean existsBySubcategory(ServiceSubcategoryEntity subcategory) {
        return repository.existsBySubcategory(subcategory);
    }

    public ServiceEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such service id not found"));
    }

    public ServiceEntity getByIdWithImagesAndSubcategory(UUID id) {
        return repository.findWithImagesAndSubcategoryAndFreelancerById(id)
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

    public List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity) {
        return repository.findAllByFreelancerAndHiddenFalse(entity).stream()
                .map(mapper::toUserServiceResponse)
                .toList();
    }

    public ServiceResponse mapToServiceResponse(ServiceEntity entity) {
        ServiceResponse serviceResponse = mapper.serviceEntityToResponse(entity);
        serviceResponse.setImageURL(serviceImageService.getUrl(entity.getTitleImage()).orElse(null));
        return serviceResponse;
    }

    public GetOwnServiceResponse mapToGetOwnService(ServiceEntity entity) {
        GetOwnServiceResponse serviceResponse = mapper.toOwnServiceResponse(entity);
        serviceResponse.setImageURL(serviceImageService.getUrl(entity.getTitleImage()).orElse(null));
        return serviceResponse;
    }

    public ServiceOrderInfoResponse mapToServiceOrderInfoResponse(ServiceEntity entity) {
        ServiceOrderInfoResponse serviceResponse = mapper.toServiceOrderInfoResponse(entity);
        serviceResponse.setImageURL(serviceImageService.getUrl(entity.getTitleImage()).orElse(null));
        return serviceResponse;
    }
}
