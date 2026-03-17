package com.srt.FreelanceMarketplace.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorageUtil {
    void uploadFile(MultipartFile multipartFile, String filename) throws IOException;
    File downloadFile(String filename);
    boolean isValidFile(MultipartFile file);
    String getRandomName(MultipartFile file);
}
