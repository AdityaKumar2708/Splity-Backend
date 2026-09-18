package com.rkind.splity.dto.user;

import java.time.LocalDate;

public class UpdateUserProfileRequest {

    private String displayName;
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String profilePicture;
    private String profileBackgroundPicture;

    public UpdateUserProfileRequest() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfileBackgroundPicture() {
        return profileBackgroundPicture;
    }

    public void setProfileBackgroundPicture(String profileBackgroundPicture) {
        this.profileBackgroundPicture = profileBackgroundPicture;
    }
}