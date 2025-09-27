package com.rishab99058.backend.service;

import com.rishab99058.backend.response.UserResponse;

public interface UserService {

    UserResponse findDetails(String jwtToken);
}
