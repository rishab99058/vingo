package com.rishab99058.backend.repository;

import com.rishab99058.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByEmailAndDeletedAtNull(String username);
}
