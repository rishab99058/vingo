package com.rishab99058.backend.entity;

import com.rishab99058.backend.enums.Category;
import com.rishab99058.backend.enums.FoodType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
public class FoodItemEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String image;
    private String shopId;
    private Category category;
    private double price;
    private FoodType foodType;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
