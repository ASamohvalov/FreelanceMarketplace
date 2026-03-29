package com.srt.FreelanceMarketplace.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IdentifierDto {
    private UUID id;
}
