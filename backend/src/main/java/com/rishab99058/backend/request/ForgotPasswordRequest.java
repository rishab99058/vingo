package com.rishab99058.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @JsonProperty("email")
    private String email;
}
