package com.srt.FreelanceMarketplace.service.application.user;

import com.srt.FreelanceMarketplace.domain.dto.RoleEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.FreelancerRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.UserRepository;
import com.srt.FreelanceMarketplace.service.domain.payment.AccountDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.JobTitleDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.RoleDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.TokenDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    private final AuthHelperService authHelperService;
    private final FreelancerDomainService freelancerService;
    private final JobTitleDomainService jobTitleService;
    private final RoleDomainService roleService;
    private final ServiceDomainService serviceService;
    private final FileStorageUtil fileStorageUtil;
    private final TokenDomainService tokenService;
    private final AccountDomainService accountDomainService;

    public void logout(JwtRequest request) {
        tokenService.deleteByToken(request.getRefreshToken());
    }

    @Transactional
    public void becomeFreelancer(FreelancerRequest request) {
        UserEntity user = authHelperService.getUser();
        if (freelancerService.existsByUser(user)) {
            throw new GlobalBadRequestException("user already freelancer");
        }

        JobTitleEntity jobTitle = jobTitleService.getById(request.getJobTitleId());
        FreelancerEntity freelancer = FreelancerEntity.builder()
                .jobTitle(jobTitle)
                .user(authHelperService.getUser())
                .aboutYourself(request.getAboutYourself())
                .build();

        freelancerService.save(freelancer);
        List<RoleEntity> roles = user.getRoles();
        roles.add(roleService.getByName(RoleEnum.ROLE_FREELANCER));
        repository.save(user);

        accountDomainService.create(user);
    }

    public UserInfoResponse getInfo() {
        UserEntity user = repository.findByIdWithRoles(authHelperService.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("logic error, not found id on authorize user"));
        UserInfoResponse response = userMapper.entityToInfoResponse(user);
        if (hasRole(user, RoleEnum.ROLE_FREELANCER)) {
            FreelancerEntity freelancer = freelancerService.getByUser(user);
            response.setServices(serviceService.getAllByFreelancer(freelancer));
        }
        return response;
    }

    public void uploadAvatar(MultipartFile avatar) {
        UserEntity user = authHelperService.getUser();
        String fileName = fileStorageUtil.getRandomName(avatar);
        try {
            fileStorageUtil.uploadFile(avatar, fileName);
        } catch (IOException e) {
            throw new IllegalStateException("error file uploading: " + e);
        }
        user.setAvatarPath(fileName);
        repository.save(user);
    }

    public Optional<Path> getAvatar(UUID userId) {
        UserEntity user = repository.findById(userId)
                .orElseThrow(() -> new GlobalBadRequestException("user not found"));
        if (user.getAvatarPath() == null) {
            return Optional.empty();
        }
        return Optional.of(fileStorageUtil.downloadFile(user.getAvatarPath()));
    }

    private boolean hasRole(UserEntity user, RoleEnum role) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(role.name()));
    }
}
