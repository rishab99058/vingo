package com.rishab99058.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private HttpStatus httpStatus;
}
