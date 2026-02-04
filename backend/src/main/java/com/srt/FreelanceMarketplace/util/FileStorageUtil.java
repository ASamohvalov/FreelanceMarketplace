package com.srt.FreelanceMarketplace.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageUtil {
    void uploadFile(MultipartFile multipartFile, String filename) throws IOException;
    byte[] downloadFile(String filename) throws IOException;
    boolean isValidFile(MultipartFile file);
    String getRandomName(MultipartFile file);
}
