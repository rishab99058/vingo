package com.rishab99058.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shop")
public class ShopEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String image;
    private String ownerId;
    private String address;
    private String city;
    private String country;
    private String pinCode;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Date deactivatedAt;
}
