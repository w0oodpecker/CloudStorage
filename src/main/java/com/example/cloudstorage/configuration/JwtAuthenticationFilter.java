package com.example.cloudstorage.configuration;

import com.example.cloudstorage.model.CloudError;
import com.example.cloudstorage.repository.TokenBlackListRepository;
import com.example.cloudstorage.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;


import static com.example.cloudstorage.component.CloudTools.convertJsonToString;
import static com.example.cloudstorage.configuration.CloudMessages.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    public void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws IOException, ServletException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String login;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //Проверяем на отсутствие токена
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            jwt = authHeader.substring(7);
            //Если токен есть в заросе проверяем его блэклисте
            if (tokenBlackListRepository.existsById(jwt)) { //Проверка на блэклист
                generateBody(response, new CloudError(USERUNOUTHORIZED));
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                filterChain.doFilter(request, response);
                return;
            }
        }

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


    public static void generateBody(HttpServletResponse response, Object object) throws IOException {
        PrintWriter out = response.getWriter();
        String body = convertJsonToString(object);
        out.println(body);
        out.flush();
    }

}
