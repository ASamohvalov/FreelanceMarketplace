package com.srt.FreelanceMarketplace.domain.dto.request.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ServiceRequest {
    @NotNull
    private MultipartFile titleImage;

    @NotNull
    @Size(max = 4)
    private List<MultipartFile> images;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(min = 100)
    private String description;

    @NotNull
    private int price;

    @NotNull
    private int deadlineDays;

    @NotNull
    private int revisionsCount;

    @NotNull
    private UUID subcategoryId;
}
