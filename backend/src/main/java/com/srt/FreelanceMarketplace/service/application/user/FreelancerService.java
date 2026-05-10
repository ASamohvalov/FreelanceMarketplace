package com.srt.FreelanceMarketplace.service.application.user;

import com.srt.FreelanceMarketplace.domain.dto.request.freelancer.EditFreelancerProfileRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.mapper.ReviewMapper;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import com.srt.FreelanceMarketplace.service.domain.review.ReviewDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.JobTitleDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.UserDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository repository;
    private final FreelanceMapper mapper;
    private final FreelancerDomainService domainService;

    private final UserDomainService userDomainService;
    private final JobTitleDomainService jobTitleDomainService;
    private final AuthHelperService authHelperService;
    private final ReviewDomainService reviewDomainService;
    private final ReviewMapper reviewMapper;

    public List<FreelancerResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::freelancerEntityToResponse)
                .toList();
    }

    public FreelancerResponse getById(UUID freelancerId) {
        return mapper.freelancerEntityToResponse(
                repository.findByIdWithJobTitle(freelancerId)
                        .orElseThrow(() -> new GlobalBadRequestException("freelancer not found"))
        );
    }

    public FreelancerResponse getByUserId(UUID userId) {
        UserEntity user = userDomainService.getReferenceIfExistsById(userId);
        return mapper.freelancerEntityToResponse(
                domainService.getByUserWithJobTitle(user)
        );
    }

    public void editProfile(EditFreelancerProfileRequest request) {
        JobTitleEntity jobTitle = jobTitleDomainService.getReferenceIfExitsById(request.getJobTitleId());
        UserEntity user = authHelperService.getUser();
        FreelancerEntity freelancer = domainService.getByUser(user);

        freelancer.setJobTitle(jobTitle);
        freelancer.setAboutYourself(request.getAboutYourself());
        repository.save(freelancer);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userDomainService.save(user);
    }

    public List<ReviewResponse> reviewGet(UUID freelancerId) {
        FreelancerEntity freelancer = domainService.getReferenceById(freelancerId);
        return reviewDomainService.getByFreelancerWithService(freelancer).stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}
