package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class JwtResponse {

    @JsonProperty("auth-token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
