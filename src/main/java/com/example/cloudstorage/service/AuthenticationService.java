package com.example.cloudstorage.service;

import com.example.cloudstorage.dto.AuthenticationDto;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository repository;
    private final JwtService jwtService;

    public AuthenticationDto.ResponseAuth.Create authentificate(AuthenticationDto.RequestAuth.Create request){
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()));
        User user = repository.findUserByLogin(request.getLogin())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationDto.ResponseAuth.Create(jwtToken);
        //return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }
}
