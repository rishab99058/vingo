package com.rishab99058.backend.service;

import com.rishab99058.backend.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse findDetails(String jwtToken);
    UserResponse uploadProfilePhoto(String jwtToken, MultipartFile file);
}
