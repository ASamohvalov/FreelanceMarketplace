package com.srt.FreelanceMarketplace.domain.dto.response.service;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class PaymentInfoResponse {
    private UUID serviceId;
    private String serviceName;
    private String freelancerFirstName;
    private String freelancerLastName;
    private long price;
    private long commission;
    private ServiceTypeEnum type;
}
