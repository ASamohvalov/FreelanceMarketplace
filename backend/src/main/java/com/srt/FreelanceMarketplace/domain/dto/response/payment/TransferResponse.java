package com.srt.FreelanceMarketplace.domain.dto.response.payment;

import com.srt.FreelanceMarketplace.domain.dto.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TransferResponse {
    private UUID id;
    private String serviceTitle;
    private long amount;
    private Instant createdAt;
    private UserNameResponse sender;
    private TransferStatusEnum status;
}
