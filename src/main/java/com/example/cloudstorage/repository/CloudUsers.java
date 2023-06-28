package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CloudUsers extends CrudRepository<User, String> {
    Optional<User> findUserByLogin(String login);
}
