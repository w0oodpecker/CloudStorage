package com.example.cloudstorage.configuration;

import com.example.cloudstorage.component.CloudTools;
import com.example.cloudstorage.model.CloudError;
import com.example.cloudstorage.model.PoorToken;
import com.example.cloudstorage.repository.TokenBlackListRepository;
import com.example.cloudstorage.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static com.example.cloudstorage.configuration.CloudMessages.*;

@Service
@RequiredArgsConstructor
@Log4j2
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

        if (authHeader != null) {
            if (authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                tokenBlackListRepository.save(new PoorToken(jwt));
                log.info(LOGOUTOK);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            CloudTools.generateBody(response, new CloudError(USERUNOUTHORIZED));
            log.info(USERUNOUTHORIZED);
        }
    }
}


