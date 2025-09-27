package com.rishab99058.backend.service.impl;

import com.rishab99058.backend.entity.UserEntity;
import com.rishab99058.backend.enums.Roles;
import com.rishab99058.backend.model.UserModel;
import com.rishab99058.backend.repository.UserRepository;
import com.rishab99058.backend.request.SignUpRequest;
import com.rishab99058.backend.response.SignUpResponse;
import com.rishab99058.backend.security.CustomUserDetails;
import com.rishab99058.backend.security.JwtService;
import com.rishab99058.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
}
