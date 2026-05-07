package com.srt.FreelanceMarketplace.domain.dto.response.order.requirement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRequirementResponse {
    private UUID id;
    private UUID orderId;
    private String description;
    private String comment;
    private int deadlineDays;
    private List<UUID> orderRequirementFileIds;
}
