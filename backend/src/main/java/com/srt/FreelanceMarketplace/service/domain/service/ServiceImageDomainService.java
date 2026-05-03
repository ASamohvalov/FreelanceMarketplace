package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceImageEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceImageRepository;
import com.srt.FreelanceMarketplace.util.FileStorageStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ServiceImageDomainService {
    private final ServiceImageRepository repository;

    private final FileStorageStrategy imageStorageStrategy;

    // TODO!!!
    public Optional<String> getUrl(ServiceImageEntity entity) {
        return entity == null
                ? Optional.empty()
                : Optional.of("localhost:8080/api/service/image/title/" + entity.getService().getId());
    }

    public List<ServiceImageEntity> uploadImages(ServiceEntity service, MultipartFile titleImage, List<MultipartFile> images) {
        List<ServiceImageEntity> imageEntities = Stream.concat(
                        Stream.of(titleImage), images.stream())
                .map(image -> {
                    if (!imageStorageStrategy.supports(image)) {
                        throw new GlobalBadRequestException("unsupported file format");
                    }

                    String fileName = imageStorageStrategy.save(image);

                    return ServiceImageEntity.builder()
                            .service(service)
                            .imagePath(fileName)
                            .build();
                })
                .toList();

        System.out.println(imageEntities.size());

        service.setTitleImage(imageEntities.get(0));
        return imageEntities;
    }

    public void deleteImages(ServiceEntity service) {
        List<ServiceImageEntity> images = repository.findAllByService(service);

        service.setTitleImage(null);
        service.getImages().clear();

        for (ServiceImageEntity image : images) {
            imageStorageStrategy.delete(image.getImagePath());
        }

        repository.deleteAll(images);
    }

    public void saveAll(Iterable<ServiceImageEntity> entities) {
        repository.saveAll(entities);
    }

    public ServiceImageEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("image not found"));
    }
}
