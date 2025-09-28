package com.rishab99058.backend.controller;

import com.rishab99058.backend.response.UserResponse;
import com.rishab99058.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me(@RequestHeader("Authorization") String token){
        return userService.findDetails(token);
    }

    @PostMapping("/upload_profile_photo")
    public UserResponse uploadProfilePhoto(@RequestHeader("Authorization") String token, @RequestParam(value = "file", required = true) MultipartFile file){
        return userService.uploadProfilePhoto(token,file);
    }
}
