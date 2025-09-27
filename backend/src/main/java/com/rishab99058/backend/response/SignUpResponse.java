package com.rishab99058.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rishab99058.backend.model.UserModel;
import lombok.Data;

@Data
public class SignUpResponse extends BaseResponse {
    @JsonProperty("token")
    private String jwtToken;

    @JsonProperty("user_details")
    private UserModel user;
}
