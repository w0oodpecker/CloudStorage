package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("auth-token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
