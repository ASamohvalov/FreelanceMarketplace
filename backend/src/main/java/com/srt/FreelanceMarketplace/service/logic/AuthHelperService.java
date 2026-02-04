package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.auth.UserDetailsImpl;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthHelperService {
    public UserEntity getUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
