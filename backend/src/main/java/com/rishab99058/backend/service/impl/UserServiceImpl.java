package com.rishab99058.backend.service.impl;

import com.rishab99058.backend.entity.UserEntity;
import com.rishab99058.backend.model.UserModel;
import com.rishab99058.backend.repository.UserRepository;
import com.rishab99058.backend.response.UserResponse;
import com.rishab99058.backend.security.JwtService;
import com.rishab99058.backend.service.UserService;
import com.rishab99058.backend.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;

    @Override
    public UserResponse findDetails(String jwtToken) {
        UserResponse  userResponse = new UserResponse();
        try{
            jwtToken = jwtToken.substring("Bearer ".length());
            String email = jwtService.getEmailFromJwtToken(jwtToken);

            UserEntity userEntity = userRepository.findByEmailAndDeletedAtNull(email);
            if(userEntity == null){
                throw new Exception("User not found");
            }

            UserModel userModel = prepareUserModel(userEntity);

            userResponse.setUser(userModel);
            userResponse.setHttpStatus(HttpStatus.OK);
            userResponse.setMessage("User details has been found successfully");

        }catch(Exception exception){
            log.error("UserServiceImpl findDetails Exception: ", exception);
            userResponse.setMessage(exception.getMessage());
            userResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return userResponse;
    }

    private UserModel prepareUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setEmail(userEntity.getEmail());
        userModel.setName(userEntity.getName());
        userModel.setPhoneNo(userEntity.getPhoneNo());
        userModel.setRole(userEntity.getRole());
        userModel.setImageUrl(userEntity.getImageUrl());
        return userModel;
    }

    @Override
    public UserResponse uploadProfilePhoto(String jwtToken, MultipartFile file) {
        UserResponse userResponse = new UserResponse();
        try{
            jwtToken = jwtToken.substring("Bearer ".length());
            String email = jwtService.getEmailFromJwtToken(jwtToken);

            UserEntity userEntity = userRepository.findByEmailAndDeletedAtNull(email);
            if(userEntity == null){
                throw new Exception("User not found");
            }
            String url = cloudinaryService.uploadFile(file);
            userEntity.setImageUrl(url);
            userRepository.save(userEntity);

            UserModel userModel = prepareUserModel(userEntity);
            userResponse.setUser(userModel);
            userResponse.setHttpStatus(HttpStatus.OK);
            userResponse.setMessage("User details has been updated successfully");

        }catch(Exception exception){
            log.error("UserServiceImpl uploadProfilePhoto Exception: ", exception);
            userResponse.setMessage(exception.getMessage());
            userResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return userResponse;
    }
}
