package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.service.application.service.ServiceImageService;
import com.srt.FreelanceMarketplace.util.FileHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service/image")
public class ServiceImageController {
    private final ServiceImageService serviceImageService;
    private final FileHelperUtil fileHelperUtil;

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImageById(@PathVariable UUID imageId) {
        Path path = serviceImageService.getImageById(imageId);
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path))
                .body(resource);
    }
}
