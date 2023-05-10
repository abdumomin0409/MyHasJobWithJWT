package com.company.job.myhasjobwithjwt.config.security;

import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SessionUser {
    private final UserRepository userRepository;

    public User user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!Objects.isNull(principal)) {
            return userRepository.findByPhoneNumber((String) principal);
        }
        return null;
    }

    public String id() {
        User user = user();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

}
