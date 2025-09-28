package com.rishab99058.backend.repository;

import com.rishab99058.backend.entity.ShopEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<ShopEntity, String> {
}
