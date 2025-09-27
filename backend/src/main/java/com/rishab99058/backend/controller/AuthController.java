package com.rishab99058.backend.controller;

import com.rishab99058.backend.request.ForgotPasswordRequest;
import com.rishab99058.backend.request.ResetPasswordRequest;
import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.BaseResponse;
import com.rishab99058.backend.response.SignUpResponse;
import com.rishab99058.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }


    @PostMapping("/log_in")
    public SignUpResponse logIn(@RequestBody SignUpRequest signUpRequest) {
        return authService.login(signUpRequest);
    }


    @PostMapping("/forgot_password")
    public BaseResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/reset_password")
    public BaseResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }

}
