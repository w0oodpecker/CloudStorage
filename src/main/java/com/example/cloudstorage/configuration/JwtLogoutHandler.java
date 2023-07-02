package com.example.cloudstorage.configuration;

import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.repository.TokenBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static com.example.cloudstorage.configuration.CloudMessages.AUTHORIZATION;


@Service
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    public void logout(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response,
                       @NotNull Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        String jwt;
        if (authHeader != null || authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            tokenBlackListRepository.save(new AuthenticationResponse(jwt));
        }
    }

}


