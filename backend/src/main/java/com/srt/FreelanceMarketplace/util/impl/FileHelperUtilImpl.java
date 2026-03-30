package com.srt.FreelanceMarketplace.util.impl;

import com.srt.FreelanceMarketplace.util.FileHelperUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileHelperUtilImpl implements FileHelperUtil {

    @Override
    public MediaType getContentType(Path path) {
        String contentType = determineContentType(path);
        return MediaType.parseMediaType(contentType);
    }

    private String determineContentType(Path path) {
        try {
            String type = Files.probeContentType(path);
            return type != null ? type : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
}
