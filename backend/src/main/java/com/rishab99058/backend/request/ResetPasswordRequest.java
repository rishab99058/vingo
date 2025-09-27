package com.rishab99058.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("password")
    private String password;
}
