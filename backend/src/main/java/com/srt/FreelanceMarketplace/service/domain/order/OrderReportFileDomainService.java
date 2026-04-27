package com.srt.FreelanceMarketplace.service.domain.order;

import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportFileEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.OrderReportFileRepository;
import com.srt.FreelanceMarketplace.util.FileStorageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReportFileDomainService {
    private final OrderReportFileRepository repository;

    private final List<FileStorageStrategy> storageStrategies;

    public List<OrderReportFileEntity> uploadFiles(OrderReportEntity orderReport, List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    FileStorageStrategy storageStrategy = storageStrategies.stream()
                            .filter(strategy -> strategy.supports(file))
                            .findFirst()
                            .orElseThrow(() -> new GlobalBadRequestException("unsupported file format"));

                    String fileName = storageStrategy.save(file);

                    return OrderReportFileEntity.builder()
                            .orderReport(orderReport)
                            .filePath(fileName)
                            .build();
                })
                .toList();
    }

    public void saveAll(Iterable<OrderReportFileEntity> entities) {
        repository.saveAll(entities);
    }

    public OrderReportFileEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("file not found"));
    }
}
