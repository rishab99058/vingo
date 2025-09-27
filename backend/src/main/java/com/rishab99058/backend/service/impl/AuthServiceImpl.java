package com.rishab99058.backend.service.impl;

import com.rishab99058.backend.entity.OtpEntity;
import com.rishab99058.backend.entity.UserEntity;
import com.rishab99058.backend.enums.Roles;
import com.rishab99058.backend.model.UserModel;
import com.rishab99058.backend.repository.OtpRepository;
import com.rishab99058.backend.repository.UserRepository;
import com.rishab99058.backend.request.ForgotPasswordRequest;
import com.rishab99058.backend.request.ResetPasswordRequest;
import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.BaseResponse;
import com.rishab99058.backend.response.SignUpResponse;
import com.rishab99058.backend.security.CustomUserDetails;
import com.rishab99058.backend.security.JwtService;
import com.rishab99058.backend.service.AuthService;
import com.rishab99058.backend.utils.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpRepository otpRepository;
    private final MailService mailService;

    @Override
    public SignUpResponse login(SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = new SignUpResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signUpRequest.getEmail(),
                            signUpRequest.getPassword()
                    )
            );

            CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
            UserEntity userEntity = customUser.getUserEntity();
            String jwt = jwtService.generateJwtToken(userEntity);

            signUpResponse.setJwtToken(jwt);
            prepareUserModel(signUpResponse, userEntity);
            signUpResponse.setMessage("User logged in successfully");
            signUpResponse.setHttpStatus(HttpStatus.OK);

        } catch (Exception exception) {
            log.error("Exception logging in user: {}", exception.getMessage());
            signUpResponse.setMessage(exception.getMessage());
            signUpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return signUpResponse;
    }


    private void prepareUserModel(SignUpResponse signUpResponse, UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setEmail(userEntity.getEmail());
        userModel.setName(userEntity.getName());
        userModel.setPhoneNo(userEntity.getPhoneNo());
        userModel.setId(userEntity.getId());
        userModel.setRole(userEntity.getRole());

        signUpResponse.setUser(userModel);
    }

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        SignUpResponse  signUpResponse = new SignUpResponse();
        try{
            UserEntity user = userRepository.findByEmailAndDeletedAtNull( signUpRequest.getEmail());
            if(user != null){
                throw new Exception("User already exists");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(signUpRequest.getEmail());
            userEntity.setName(signUpRequest.getEmail());
            userEntity.setPassword(signUpRequest.getPassword());
            userEntity.setRole(Roles.valueOf(signUpRequest.getRole()));
            userEntity.setCreatedAt(new Date());
            userEntity.setUpdateAt(new  Date());
            userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userEntity.setPhoneNo(signUpRequest.getMobile());
            userEntity = userRepository.save(userEntity);

            prepareUserModel(signUpResponse, userEntity);

            signUpResponse.setMessage("User registered in successfully");
            signUpResponse.setHttpStatus(HttpStatus.OK);
        }
        catch (Exception exception){
            log.error("exception in registering the user {}", exception.getMessage());
            signUpResponse.setMessage(exception.getMessage());
            signUpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return signUpResponse;
    }

    @Override
    public BaseResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try{
            UserEntity user  = userRepository.findByEmailAndDeletedAtNull(forgotPasswordRequest.getEmail());
            if(user == null)
            {
                throw new Exception("User doesn't exist with the given email id");
            }
            OtpEntity otpEntity = otpRepository.findByEmailAndDeletedAtNullAndValidTrue(forgotPasswordRequest.getEmail());
            if(otpEntity != null)
            {

                otpEntity.setDeletedAt(new  Date());
                otpEntity.setValid(false);
                otpRepository.save(otpEntity);
            }
            OtpEntity otp  = prepareOtp(forgotPasswordRequest.getEmail());
            otp = otpRepository.save(otp);
            mailService.sendForgotPasswordOtp(forgotPasswordRequest.getEmail(), otp.getOtp());
            baseResponse.setHttpStatus(HttpStatus.OK);
            baseResponse.setMessage("Verification otp has been send to the mail");
        }catch (Exception exception){
            log.error("exception in sending the otp {}", exception.getMessage());
            baseResponse.setMessage(exception.getMessage());
            baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return baseResponse;
    }

    @Override
    public BaseResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserEntity user = userRepository.findByEmailAndDeletedAtNull(resetPasswordRequest.getEmail());
            if (user == null) {
                throw new Exception("User doesn't exist with the given email id");
            }

            OtpEntity otpEntity = otpRepository.findByEmailAndDeletedAtNullAndValidTrue(resetPasswordRequest.getEmail());
            if (otpEntity == null) {
                throw new Exception("Something went wrong, please request a new OTP");
            }
            LocalDateTime otpCreatedAt = otpEntity.getCreatedAt();
            if (otpCreatedAt.plusMinutes(15).isBefore(LocalDateTime.now()) || !otpEntity.isValid()) {
                otpEntity.setDeletedAt(new Date());
                otpEntity.setValid(false);
                otpRepository.save(otpEntity);
                OtpEntity newOtp = prepareOtp(resetPasswordRequest.getEmail());
                otpRepository.save(newOtp);
                mailService.sendForgotPasswordOtp(resetPasswordRequest.getEmail(), newOtp.getOtp());

                baseResponse.setHttpStatus(HttpStatus.OK);
                baseResponse.setMessage("Previous OTP expired. A new OTP has been sent to your email.");
                return baseResponse;
            }
            if (!otpEntity.getOtp().equals(resetPasswordRequest.getOtp())) {
                throw new Exception("OTP is incorrect. Please try again.");
            }
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
            user.setUpdateAt(new Date());
            userRepository.save(user);
            otpEntity.setValid(false);
            otpEntity.setDeletedAt(new Date());
            otpRepository.save(otpEntity);

            baseResponse.setHttpStatus(HttpStatus.OK);
            baseResponse.setMessage("Password has been reset successfully.");

        } catch (Exception exception) {
            log.error("Exception in resetting the password: {}", exception.getMessage());
            baseResponse.setMessage(exception.getMessage());
            baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return baseResponse;
    }


    private OtpEntity prepareOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setValid(true);
        otpEntity.setCreatedAt(LocalDateTime.now());
        otpEntity.setUpdatedAt(new Date());
        otpEntity.setDeletedAt(null);

        return otpEntity;
    }


}
