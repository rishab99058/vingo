package com.rishab99058.backend.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FoodResponse extends BaseResponse {

    @JsonProperty("food_types")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> foodTypes;

    @JsonProperty("food_category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> foodCategory;
}
