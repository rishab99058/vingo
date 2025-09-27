package com.rishab99058.backend.service;

import com.rishab99058.backend.request.ForgotPasswordRequest;
import com.rishab99058.backend.request.ResetPasswordRequest;
import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.BaseResponse;
import com.rishab99058.backend.response.SignUpResponse;

public interface AuthService {
    SignUpResponse login(SignUpRequest signUpRequest);
    SignUpResponse  signUp(SignUpRequest signUpRequest);
    BaseResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    BaseResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    SignUpResponse getRefreshToken(String refreshToken);
}
