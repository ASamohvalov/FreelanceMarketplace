package com.srt.FreelanceMarketplace.util;

import org.springframework.http.MediaType;

import java.nio.file.Path;

public interface FileHelperUtil {
    MediaType getContentType(Path path);
}
