package com.srt.FreelanceMarketplace.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStorageUtil {
    void uploadFile(MultipartFile multipartFile, String filename) throws IOException;
    Path downloadFile(String filename);
    boolean isValidFile(MultipartFile file);
    String getRandomName(MultipartFile file);
}
