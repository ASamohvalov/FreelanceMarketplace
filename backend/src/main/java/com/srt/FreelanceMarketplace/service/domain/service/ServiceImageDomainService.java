package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.repository.service.ServiceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceImageDomainService {
    private final ServiceImageRepository repository;

    // TODO!!!
    public Optional<String> getUrl(ServiceImageEntity entity) {
        return entity == null
                ? Optional.empty()
                : Optional.of("localhost:8080/api/service/image/title/" + entity.getService().getId());
    }
}
