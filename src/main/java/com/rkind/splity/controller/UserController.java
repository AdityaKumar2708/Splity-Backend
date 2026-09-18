package com.rkind.splity.controller;

import com.rkind.splity.dto.user.UpdateUserProfileRequest;
import com.rkind.splity.dto.user.UserProfileResponse;
import com.rkind.splity.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public  UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/profile")
    public UserProfileResponse getUserProfile(
            @PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }

    @PutMapping("/{userId}/profile")
    public UserProfileResponse updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UpdateUserProfileRequest request
            ) {
        return userService.updateUserProfile(userId, request);
    }
}
