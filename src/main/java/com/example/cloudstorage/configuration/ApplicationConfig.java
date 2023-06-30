package com.example.cloudstorage.configuration;

import com.example.cloudstorage.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsersRepository usersRepository;
    @Bean
    public UserDetailsService userDetailsService(){
                return username -> usersRepository.findUserByLogin(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
