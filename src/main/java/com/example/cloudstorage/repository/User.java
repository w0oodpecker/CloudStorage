package com.example.cloudstorage.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Repository
@Setter
@Getter

@Entity
@Table (name = "t_user")
public class User {
    @Id
    @Column(nullable = false, length = 255)
    private String login;
    @Column(nullable = false, length = 32)
    private String password;
}

