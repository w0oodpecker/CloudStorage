package com.example.cloudstorage.configuration;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.service.AuthenticationService;
import com.example.cloudstorage.service.JwtService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Auth-Token";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws IOException, ServletException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String login;

        //Проверяем на отсутствие токена
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            AuthenticationRequest authenticationRequest = readRequestBody(request);

            //Проверяем существование юзера в репозитории
            final UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
            } catch (UsernameNotFoundException exc) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                filterChain.doFilter(request, response);
                return;
            }

            //Если юзер существует в репозитории, то проверяем пароль
            if (authenticationRequest.getPassword().equals(userDetails.getPassword())) {
                String token = jwtService.generateToken(userDetails);
                response.setStatus(HttpServletResponse.SC_OK);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
                String body = convertJsonToString(authenticationResponse);
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(body);
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            filterChain.doFilter(request, response);
            return;
        }


        //Если токен есть в заросе проверяем его валидность
        jwt = authHeader.substring(7);
        login = jwtService.extractUserName(jwt);
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(login); //Загрузка деталей пользователя из репозитория
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }


    public AuthenticationRequest readRequestBody(HttpServletRequest httpServletRequest) {
        try {
            byte[] inputStreamBytes = StreamUtils.copyToByteArray(httpServletRequest.getInputStream());
            Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
            String loginFromBody = jsonRequest.get("login");
            String passwordFromBody = jsonRequest.get("password");
            return new AuthenticationRequest(loginFromBody, passwordFromBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    public String convertJsonToString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(object);
        return body;
    }

}
