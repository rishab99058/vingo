package com.rishab99058.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseRequest {
    @JsonProperty("page_no")
    private int pageNo = 1;
    @JsonProperty("page_size")
    private int pageSize=10;
}
