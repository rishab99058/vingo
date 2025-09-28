package com.rishab99058.backend.controller;

import com.rishab99058.backend.response.FoodResponse;
import com.rishab99058.backend.service.PublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/get_food_types")
    public FoodResponse getFoodTypes() {
        return publicService.getFoodTypes();
    }

    @GetMapping("/get_food_categories")
    public FoodResponse getFoodCategories() {
        return publicService.getFoodCategory();
    }
}
