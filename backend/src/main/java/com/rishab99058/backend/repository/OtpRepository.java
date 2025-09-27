package com.rishab99058.backend.repository;

import com.rishab99058.backend.entity.OtpEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends MongoRepository<OtpEntity, String> {

    OtpEntity findByEmailAndDeletedAtNullAndValidTrue(String email);

}
