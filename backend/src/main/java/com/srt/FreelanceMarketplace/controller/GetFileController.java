package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.service.application.service.GetFileService;
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
@RequestMapping("/file/get")
// permit all
public class GetFileController {
    private final GetFileService getFileService;
    private final FileHelperUtil fileHelperUtil;

    @GetMapping("/service/image/{imageId}")
    public ResponseEntity<Resource> getImageById(@PathVariable UUID imageId) {
        Path path = getFileService.getServiceImageById(imageId);
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path))
                .body(resource);
    }

    @GetMapping("/report/file/{fileId}")
    public ResponseEntity<Resource> getFileById(@PathVariable UUID fileId) {
        Path path = getFileService.getReportFileById(fileId);
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path))
                .body(resource);
    }

    @GetMapping("/requirement/file/{fileId}")
    public ResponseEntity<Resource> getRequirementFileById(@PathVariable UUID fileId) {
        Path path = getFileService.getRequirementFileById(fileId);
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path))
                .body(resource);
    }
}
