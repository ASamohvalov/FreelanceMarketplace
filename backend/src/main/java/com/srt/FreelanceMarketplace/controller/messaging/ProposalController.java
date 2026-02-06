package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.request.messaging.ProposalRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ProposalResponse;
import com.srt.FreelanceMarketplace.service.entity.messaging.ProposalService;
import jakarta.validation.Valid;
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

    // todo TEST!!!!
    @PostMapping("/send")
    public void sendProposal(@RequestBody @Valid ProposalRequest request) {
        proposalService.sendProposal(request);
    }

    // return all not accepted proposals
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/personal/get_all")
    public List<ProposalResponse> getAllPersonal() {
        return proposalService.getAllPersonal();
    }

    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/reply/{proposalId}")
    public List<ProposalResponse> sendReply(@PathVariable UUID proposalId, @RequestParam boolean accept) {
        return proposalService.getAllPersonal();
    }
}
