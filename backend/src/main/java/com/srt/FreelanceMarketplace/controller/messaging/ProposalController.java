package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.request.messaging.ProposalRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ProposalResponse;
import com.srt.FreelanceMarketplace.service.entity.messaging.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/proposal")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping("/send/{serviceId}")
    public void sendProposal(@PathVariable UUID serviceId, @RequestBody ProposalRequest request) {
        proposalService.sendProposal(serviceId, request);
    }

    // return all not accepted proposals
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/personal/getAll")
    public List<ProposalResponse> getAllPersonal() {
        return proposalService.getAllPersonal();
    }
}
