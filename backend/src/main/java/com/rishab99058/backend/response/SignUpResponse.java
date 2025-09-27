package com.rishab99058.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rishab99058.backend.model.UserModel;
import lombok.Data;

@Data
public class SignUpResponse extends BaseResponse {
    @JsonProperty("access_token")
    private String jwtToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("user_details")
    private UserModel user;
}
