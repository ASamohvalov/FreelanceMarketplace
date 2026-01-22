package com.srt.FreelanceMarketplace.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ServiceRequest {
    private MultipartFile image;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private int price;
}
