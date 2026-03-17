package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.PaymentInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.service.entity.service.ServiceEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceEntityService service;

    @GetMapping("/get_all")
    public List<ServiceResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/freelancer/{freelancerId}")
    public List<ServiceResponse> getAllByFreelancer(@PathVariable UUID freelancerId) {
        return service.getAllByFreelancerId(freelancerId);
    }

    @GetMapping("/get_by_id/{serviceId}")
    public ServiceInfoResponse getById(@PathVariable UUID serviceId) {
        return service.getResponseById(serviceId);
    }

    @GetMapping("/image/title/{serviceId}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID serviceId) {
        File file = service.getImage(serviceId);
        Resource resource = new FileSystemResource(file);
        String contentType = determineContentType(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void addService(@ModelAttribute @Valid ServiceRequest request) {
        service.create(request);
    }

    @GetMapping("/payment/info/{serviceId}")
    public PaymentInfoResponse getPaymentInfo(@PathVariable UUID serviceId) {
        return service.getPaymentInfo(serviceId);
    }

    private String determineContentType(Path path) {
        try {
            String type = Files.probeContentType(path);
            return type != null ? type : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
}
