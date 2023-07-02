package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Repository
@Entity
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_tokenblacklist")
public class AuthenticationResponse {

    @Id
    @Column(nullable = false, length = 255)
    @JsonProperty("auth-token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

}

