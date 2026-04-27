package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportFileEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.service.domain.order.OrderReportFileDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceImageDomainService;
import com.srt.FreelanceMarketplace.util.FileStorageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetFileService {
    private final ServiceImageDomainService serviceImageDomainService;
    private final OrderReportFileDomainService orderReportFileDomainService;
    private final FileStorageStrategy imageStorageStrategy;
    private final FileStorageStrategy documentStorageStrategy;

    public Path getServiceImageById(UUID imageId) {
        ServiceImageEntity imageEntity = serviceImageDomainService.getById(imageId);
        return imageStorageStrategy.get(imageEntity.getImagePath());
    }

    public Path getReportFileById(UUID fileId) {
        OrderReportFileEntity file = orderReportFileDomainService.getById(fileId);
        return imageStorageStrategy.getSafely(file.getFilePath())
                .orElse(documentStorageStrategy.get(file.getFilePath()));
    }
}
