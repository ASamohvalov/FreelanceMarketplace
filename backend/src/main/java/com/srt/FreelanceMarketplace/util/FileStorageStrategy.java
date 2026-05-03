package com.srt.FreelanceMarketplace.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface FileStorageStrategy {
    boolean supports(MultipartFile file);
    String save(MultipartFile file);
    Path get(String fileName);
    Optional<Path> getSafely(String fileName);
    void delete(String filename);
}
