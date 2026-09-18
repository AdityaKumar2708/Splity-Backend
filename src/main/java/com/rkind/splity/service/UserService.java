package com.rkind.splity.service;

import com.rkind.splity.dto.user.UpdateUserProfileRequest;
import com.rkind.splity.dto.user.UserProfileResponse;
import com.rkind.splity.entity.User;
import com.rkind.splity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileResponse getUserProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAlternatePhoneNumber(user.getAlternatePhoneNumber());
        response.setAddress(user.getAddress());
        response.setProfilePicture(user.getProfilePicture());
        response.setProfileBackgroundPicture(user.getProfileBackgroundPicture());
        response.setFirebaseUid(user.getFirebaseUid());
        response.setGender(user.getGender());
        response.setDateOfBirth(user.getDateOfBirth());

        return  response;

    }

    public UserProfileResponse updateUserProfile(
            Long userId,
            UpdateUserProfileRequest updateUserProfileRequest
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDisplayName(updateUserProfileRequest.getDisplayName());
        user.setEmail(updateUserProfileRequest.getEmail());
        user.setAlternatePhoneNumber(updateUserProfileRequest.getAlternatePhoneNumber());
        user.setAddress(updateUserProfileRequest.getAddress());
        if (updateUserProfileRequest.getProfilePicture() != null) {
            user.setProfilePicture(
                    updateUserProfileRequest.getProfilePicture()
            );
        }

        if (updateUserProfileRequest.getProfileBackgroundPicture() != null) {
            user.setProfileBackgroundPicture(
                    updateUserProfileRequest.getProfileBackgroundPicture()
            );
        }
        user.setDateOfBirth(updateUserProfileRequest.getDateOfBirth());
        user.setGender(updateUserProfileRequest.getGender());

        userRepository.save(user);

        UserProfileResponse response = new UserProfileResponse();

        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAlternatePhoneNumber(user.getAlternatePhoneNumber());
        response.setGender(user.getGender());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setAddress(user.getAddress());
        response.setProfilePicture(user.getProfilePicture());
        response.setProfileBackgroundPicture(
                user.getProfileBackgroundPicture()
        );
        response.setFirebaseUid(user.getFirebaseUid());

        return response;
    }
}
