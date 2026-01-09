package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.RoleEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.FreelancerRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.UserRepository;
import com.srt.FreelanceMarketplace.service.entity.JobTitleService;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import com.srt.FreelanceMarketplace.service.entity.TokenService;
import com.srt.FreelanceMarketplace.service.entity.UserService;
import com.srt.FreelanceMarketplace.service.logic.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final TokenService tokenService;
    private final JobTitleService jobTitleService;
    private final AuthHelperService authHelperService;
    private final FreelancerService freelancerService;
    private final UserMapper userMapper;
    private final ServiceEntityService serviceEntityService;

    @Override
    public void save(UserEntity entity) {
        repository.save(entity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByEmailWithRoles(String email) {
        return repository.findByEmailWithRoles(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void logout(JwtRequest request) {
        tokenService.deleteByToken(request.getRefreshToken());
    }

    @Override
    public void becomeFreelancer(FreelancerRequest request) {
        if (freelancerService.existsByUser(authHelperService.getUser())) {
            throw new GlobalBadRequestException("user already freelancer");
        }
        JobTitleEntity jobTitle = jobTitleService.findById(request.getJobTitleId())
                .orElseThrow(() -> new GlobalBadRequestException("such job title not found"));
        FreelancerEntity freelancer = FreelancerEntity.builder()
                .phoneNumber(request.getPhoneNumber())
                .jobTitle(jobTitle)
                .user(authHelperService.getUser())
                .build();
        freelancerService.save(freelancer);
    }

    @Override
    public UserInfoResponse getInfo() {
        UserEntity user = repository.findByIdWithRoles(authHelperService.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("logic error, not found id on authorize user"));
        UserInfoResponse response = userMapper.entityToInfoResponse(user);
        if (hasRole(user, RoleEnum.ROLE_FREELANCER)) {
            FreelancerEntity freelancer = freelancerService.getByUser(user);
            response.setServices(serviceEntityService.getAllByFreelancer(freelancer));
        }
        return response;
    }

    private boolean hasRole(UserEntity user, RoleEnum role) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(role.name()));
    }
}
