package com.example.cloudstorage.configuration;

import com.example.cloudstorage.component.CloudTools;
import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.model.CloudError;
import com.example.cloudstorage.repository.TokenBlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static com.example.cloudstorage.configuration.CloudMessages.AUTHORIZATION;
import static com.example.cloudstorage.configuration.CloudMessages.USERUNOUTHORIZED;


@Service
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenBlackListRepository tokenBlackListRepository;


    @SneakyThrows
    @Override
    public void logout(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response,
                       @NotNull Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        String jwt;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (authHeader != null || authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                tokenBlackListRepository.save(new AuthenticationResponse(jwt));
            }
        } catch (NullPointerException exc) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            CloudTools.generateBody(response, new CloudError(USERUNOUTHORIZED));
        }
    }
}


