package com.rishab99058.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rishab99058.backend.enums.Roles;
import lombok.Data;

@Data
public class UserModel {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_no")
    private String phoneNo;
    @JsonProperty("role")
    private Roles role;
}
