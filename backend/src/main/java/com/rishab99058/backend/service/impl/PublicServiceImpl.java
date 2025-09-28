package com.rishab99058.backend.service.impl;

import com.rishab99058.backend.enums.Category;
import com.rishab99058.backend.enums.FoodType;
import com.rishab99058.backend.response.FoodResponse;
import com.rishab99058.backend.service.PublicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicServiceImpl implements PublicService {


    @Override
    public FoodResponse getFoodCategory() {
        FoodResponse response = new FoodResponse();
        try {
            List<String> list = Arrays.stream(Category.values())
                    .map(Enum::name)
                    .toList();

            response.setFoodCategory(list);
            response.setMessage("Categories fetched successfully");
            response.setHttpStatus(HttpStatus.OK);

        }catch (Exception exception){
            log.error("Exception:{}",exception.getMessage());
            response.setMessage(exception.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public FoodResponse getFoodTypes() {
        FoodResponse response = new FoodResponse();
        try {
            List<String> list = Arrays.stream(FoodType.values())
                    .map(Enum::name)
                    .toList();

            response.setFoodTypes(list);
            response.setMessage("Food types fetched successfully");
            response.setHttpStatus(HttpStatus.OK);

        }catch (Exception exception){
            log.error("Exception:{}",exception.getMessage());
            response.setMessage(exception.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
