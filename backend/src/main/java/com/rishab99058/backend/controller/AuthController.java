package com.rishab99058.backend.controller;

import com.rishab99058.backend.request.ForgotPasswordRequest;
import com.rishab99058.backend.request.ResetPasswordRequest;
import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.BaseResponse;
import com.rishab99058.backend.response.SignUpResponse;
import com.rishab99058.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${deploy.env}")
    private String env;

    @PostMapping("/sign_up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }


    @PostMapping("/log_in")
    public SignUpResponse logIn(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request,
                                HttpServletResponse response) {
        SignUpResponse signUpResponse = authService.login(signUpRequest);
        Cookie cookie = new Cookie("refreshToken", signUpResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(env));
        response.addCookie(cookie);
        return signUpResponse;
    }


    @PostMapping("/forgot_password")
    public BaseResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/reset_password")
    public BaseResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }

    @PostMapping("/refresh_token")
    public SignUpResponse refreshToken(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        return  authService.getRefreshToken(refreshToken);

    }

}
