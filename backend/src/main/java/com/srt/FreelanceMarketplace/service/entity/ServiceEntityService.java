package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;

import java.util.List;
import java.util.UUID;

public interface ServiceEntityService {
    void save(ServiceEntity service);
    List<ServiceResponse> getAll();
    void create(ServiceRequest request);
    List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity);
}
