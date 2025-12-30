package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;

import java.util.List;

public interface ServiceEntityService {
    void save(ServiceEntity service);
    List<ServiceResponse> getAll();
}
