package com.rishab99058.backend.service;

import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.SignUpResponse;

public interface AuthService {
    SignUpResponse login(SignUpRequest signUpRequest);
    SignUpResponse  signUp(SignUpRequest signUpRequest);
}
