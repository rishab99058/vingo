package com.rishab99058.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rishab99058.backend.model.UserModel;
import lombok.Data;

@Data
public class UserResponse extends BaseResponse {
    @JsonProperty("user_details")
    private UserModel user;
}
