package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.ServiceResponse;

import java.util.List;

public interface ServiceEntityService {
    List<ServiceResponse> getAll();
}
