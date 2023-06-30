package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findUserByLogin(String login);
}
