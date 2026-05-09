package com.srt.FreelanceMarketplace.util.impl;

import com.srt.FreelanceMarketplace.util.FileStorageStrategy;
import com.srt.FreelanceMarketplace.util.LocalFileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
public class ImageStorageStrategy implements FileStorageStrategy {
    @Value("${storage.location.images}")
    private String location;

    @Autowired
    private LocalFileSystemStorage storage;

    @Override
    public boolean supports(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    public String save(MultipartFile file) {
        return storage.saveToDisk(file, location);
    }

    @Override
    public Path get(String fileName) {
        return Path.of(location).resolve(fileName);
    }

    @Override
    public Optional<Path> getSafely(String fileName) {
        Path path = Path.of(location).resolve(fileName);
        return Files.exists(path)
                ? Optional.of(path)
                : Optional.empty();
    }

    @Override
    public void delete(String filename) {
        Path path = Path.of(location).resolve(filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
