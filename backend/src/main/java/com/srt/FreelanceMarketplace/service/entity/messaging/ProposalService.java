package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.dto.request.messaging.ProposalRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ProposalResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.mapper.ProposalMapper;
import com.srt.FreelanceMarketplace.repository.messaging.ProposalRepository;
import com.srt.FreelanceMarketplace.service.entity.FreelancerService;
import com.srt.FreelanceMarketplace.service.entity.service.ServiceEntityService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository repository;
    private final ProposalMapper mapper;

    private final ServiceEntityService serviceEntityService;
    private final AuthHelperService authHelperService;
    private final FreelancerService freelancerService;

    public void sendProposal(UUID serviceId, ProposalRequest request) {
        ServiceEntity service = serviceEntityService.getById(serviceId); // throw bad request if not found
        ProposalEntity proposal = ProposalEntity.builder()
                .author(authHelperService.getUser())
                .description(request.getDescription())
                .service(service)
                .build();
        repository.save(proposal);
    }

    public List<ProposalResponse> getAllPersonal() {
        FreelancerEntity freelancer = freelancerService.getByUser(authHelperService.getUser());
        return repository.findAllNotAcceptedByFreelancerWithAuthor(freelancer).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
