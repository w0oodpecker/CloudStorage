package com.example.cloudstorage.service;

import com.example.cloudstorage.exeptions.UnauthorizedException;
import com.example.cloudstorage.model.JwtRequest;
import com.example.cloudstorage.model.JwtResponse;
import com.example.cloudstorage.repository.CloudUsers;
import com.example.cloudstorage.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cloudstorage.component.JwtProvider;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class CloudAuthService {

    private final CloudUsers cloudUsers;
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {


            final User user = cloudUsers.findUserByLogin(authRequest.getLogin())
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            if (user.getPassword().equals(authRequest.getPassword())) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken);
            } else {
                throw new AuthException("Неправильный пароль");
            }


    }


}
