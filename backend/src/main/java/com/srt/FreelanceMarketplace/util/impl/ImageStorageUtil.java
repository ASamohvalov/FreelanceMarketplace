package com.srt.FreelanceMarketplace.util.impl;

import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Component
public class ImageStorageUtil implements FileStorageUtil {
    String storageLocation;

    public ImageStorageUtil(
            @Value("${storage.location}") String storageLocation
    ) {
        this.storageLocation = storageLocation;

        if (!Files.exists(Path.of(storageLocation)) &&
                !new File(storageLocation).mkdirs()) {
            throw new IllegalStateException("storage location make dir error");
        }
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String filename) throws IOException {
        File file = new File(storageLocation + filename);
        multipartFile.transferTo(file);
    }

    @Override
    public byte[] downloadFile(String filename) throws IOException {
        File file = new File(storageLocation + filename);
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public boolean isValidFile(MultipartFile file) {
        return true;
    }

    @Override
    public String getRandomName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            log.error("original filename - null, uploading failed");
            return "base_avatar.png";
        }
        String extension = originalFilename.substring(originalFilename.indexOf('.'));
        return UUID.randomUUID() + extension;
    }
}
