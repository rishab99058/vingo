package com.rishab99058.backend.repository;

import com.rishab99058.backend.entity.FoodItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends MongoRepository<FoodItemEntity, String> {
}
