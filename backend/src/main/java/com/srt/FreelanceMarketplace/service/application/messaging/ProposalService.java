package com.srt.FreelanceMarketplace.service.application.messaging;

import com.srt.FreelanceMarketplace.domain.dto.request.messaging.ProposalRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ProposalResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.ProposalMapper;
import com.srt.FreelanceMarketplace.repository.messaging.ProposalRepository;
import com.srt.FreelanceMarketplace.service.domain.messaging.MessageDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.ProposalDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository repository;
    private final ProposalMapper mapper;
    private final ProposalDomainService domainService;

    private final ServiceDomainService serviceDomainService;
    private final AuthHelperService authHelperService;
    private final FreelancerDomainService freelancerService;
    private final MessageDomainService messageService;
    private final MessagingService messagingService;
    private final NotificationSenderService notificationSenderService;

    public void sendProposal(ProposalRequest request) {
        ServiceEntity service = serviceDomainService.getByIdWithAuthor(request.getServiceId()); // throw bad request if not found
        if (repository.existsByServiceAndAuthor(service, authHelperService.getUser())) {
            throw new GlobalBadRequestException("this proposal already been sent");
        }
        ProposalEntity proposal = ProposalEntity.builder()
                .author(authHelperService.getUser())
                .description(request.getDescription())
                .service(service)
                .build();
        repository.save(proposal);

        notificationSenderService.sendNewProposal(proposal, service.getFreelancer().getUser(), authHelperService.getUser());
    }

    public List<ProposalResponse> getAllPersonal() {
        FreelancerEntity freelancer = freelancerService.getByUser(authHelperService.getUser());
        return repository.findAllNotAcceptedByFreelancerWithAuthor(freelancer).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void sendReply(UUID proposalId) {
        ProposalEntity proposal = domainService.getById(proposalId);
        if (proposal.isAccepted()) {
            throw new GlobalBadRequestException("this proposal already accepted");
        }
        proposal.setAccepted(true);

        ConversationEntity conversation = messagingService.createConversation(
                proposal.getService().getFreelancer(),
                proposal.getAuthor()
        );

        messageService.sendMessageByAuthor(conversation, proposal.getDescription(), proposal.getAuthor());
    }
}
