package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.GetOwnServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.PaymentInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.service.application.service.ServiceApplicationService;
import com.srt.FreelanceMarketplace.util.FileHelperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceApplicationService service;
    private final FileHelperUtil fileHelperUtil;

    @GetMapping("/get")
    public List<ServiceResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/freelancer/{freelancerId}")
    public List<ServiceResponse> getAllByFreelancer(@PathVariable UUID freelancerId) {
        return service.getAllByFreelancerId(freelancerId);
    }

    @GetMapping("/get/{serviceId}")
    public ServiceInfoResponse getById(@PathVariable UUID serviceId) {
        return service.getResponseById(serviceId);
    }

    @GetMapping("/image/title/{serviceId}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID serviceId) {
        Optional<Path> path = service.getImage(serviceId);
        if (path.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        Resource resource = new FileSystemResource(path.get());
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path.get()))
                .body(resource);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public IdentifierDto addService(@ModelAttribute @Valid ServiceRequest request) {
        return service.create(request);
    }

    @GetMapping("/payment/info/{serviceId}")
    public PaymentInfoResponse getPaymentInfo(@PathVariable UUID serviceId) {
        return service.getPaymentInfo(serviceId);
    }

    @PatchMapping("/hide/{serviceId}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void hideService(@PathVariable UUID serviceId) {
        service.hideService(serviceId, true);
    }

    @PatchMapping("/show/{serviceId}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void showService(@PathVariable UUID serviceId) {
        service.hideService(serviceId, false);
    }

    @GetMapping("/get/own")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public List<GetOwnServiceResponse> getAllOwnServices() {
        return service.getAllOwnServices();
    }
}
