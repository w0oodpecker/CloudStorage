package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.PoorToken;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;


@Transactional
public interface TokenBlackListRepository extends JpaRepository<PoorToken, String> {

    @Override
    boolean existsById(String s);

    @Override
    <S extends PoorToken> S save(S entity);

    @Override
    void deleteById(String token);
}
