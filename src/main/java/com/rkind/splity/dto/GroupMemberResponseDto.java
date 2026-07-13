package com.rkind.splity.dto;

public class GroupMemberResponseDto {

    private Long userId;

    private String name;

    private String phoneNumber;

    private String profilePicture;

    private boolean admin;

    private boolean online;

    private String role;

    public GroupMemberResponseDto() {
    }

    public GroupMemberResponseDto(
            Long userId,
            String name,
            String phoneNumber,
            String profilePicture,
            boolean admin,
            boolean online,
            String role
    ) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.admin = admin;
        this.online = online;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}