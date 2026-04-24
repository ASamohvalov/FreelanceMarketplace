package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceImageRepository;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceImageDomainService;
import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceImageService {
    private final ServiceImageRepository repository;
    private final ServiceImageDomainService domainService;

    private final FileStorageUtil fileStorageUtil;

    public Path getImageById(UUID imageId) {
        ServiceImageEntity imageEntity = repository.findById(imageId)
                        .orElseThrow(() -> new GlobalBadRequestException("image not found"));
        return fileStorageUtil.downloadFile(imageEntity.getImagePath());
    }
}
