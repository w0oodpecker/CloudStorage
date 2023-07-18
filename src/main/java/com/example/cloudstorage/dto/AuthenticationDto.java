package com.example.cloudstorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public enum AuthenticationDto {;
    private interface Login { @NotBlank String getLogin(); }
    private interface Password { @NotBlank String getPassword(); }
    private interface AccessToken { @NotBlank String getAccessToken(); }
    private interface Email { @NotBlank String getEmail(); }

    public enum RequestAuth {;
        @Value public static class Create implements Login, Password {
            String Login;
            String Password;
        }
    }

    public enum ResponseAuth {;
        @Value public static class Create implements AccessToken {
            @JsonProperty("auth-token")
            String AccessToken;
        }
    }

    public enum ResponseAuthErr {;
        @Value public static class Create implements Email, Password {
            String Email;
            String Password;
        }
    }
}
