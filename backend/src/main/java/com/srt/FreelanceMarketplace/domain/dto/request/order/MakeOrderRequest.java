package com.srt.FreelanceMarketplace.domain.dto.request.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MakeOrderRequest {
    @NotNull
    private UUID serviceId;

    @NotNull
    @Min(1)
    @Max(100)
    private int deadlineDays;

    @NotBlank
    private String description;

    private String comment;

    private List<MultipartFile> files;
}
