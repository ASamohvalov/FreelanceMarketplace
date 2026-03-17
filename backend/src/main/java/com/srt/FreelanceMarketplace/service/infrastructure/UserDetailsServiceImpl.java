package com.srt.FreelanceMarketplace.service.infrastructure;

import com.srt.FreelanceMarketplace.domain.auth.UserDetailsImpl;
import com.srt.FreelanceMarketplace.service.domain.user.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDomainService userDomainService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(
                userDomainService.findByEmailWithRoles(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found in user details"))
        );
    }
}
