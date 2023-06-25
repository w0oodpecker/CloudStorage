package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;

    @JsonProperty("auth-token")
    public String getAccessToken() {
        return accessToken;
    }
}
