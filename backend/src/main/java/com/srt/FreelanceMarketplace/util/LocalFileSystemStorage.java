package com.srt.FreelanceMarketplace.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class LocalFileSystemStorage {

    public String saveToDisk(MultipartFile file, String location) {
        try {
            String fileName = UUID.randomUUID() + getExtension(file);
            Path targetPath = Path.of(location);
            Files.createDirectories(targetPath);
            Path targetFile  = targetPath.resolve(fileName);
            Files.createFile(targetFile);
            file.transferTo(targetFile);
            return fileName;
        } catch (IOException e) {
            throw new IllegalStateException("file saving error");
        }
    }

    private String getExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        return (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";
    }
}
