package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.AuthenticationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;


@Transactional
public interface TokenBlackListRepository extends JpaRepository<AuthenticationResponse, String> {

    @Override
    boolean existsById(String s);

    @Override
    <S extends AuthenticationResponse> S save(S entity);

    @Override
    void deleteById(String token);
}
