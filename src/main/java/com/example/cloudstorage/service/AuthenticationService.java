package com.example.cloudstorage.service;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.repository.UsersRepository;
import lombok.Getter;
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

    public AuthenticationResponse authentificate(AuthenticationRequest request){
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()));
        var user = repository.findUserByLogin(request.getLogin())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

}
