package com.rishab99058.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "otp")
public class OtpEntity {
    @Id
    private String id;
    private String otp;
    private String email;
    private boolean valid;
    private LocalDateTime createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
