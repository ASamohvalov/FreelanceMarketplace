package com.srt.FreelanceMarketplace.domain.dto.request.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class ServiceRequest {
    @NotNull
    private MultipartFile titleImage;

    @NotNull
    private List<MultipartFile> images;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private int price;

    @NotNull
    private int deadlineDays;

    @NotNull
    private int revisionsCount;
}
