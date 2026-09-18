package com.rkind.splity.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firebase_uid", unique = true, nullable = false)
    private String firebaseUid;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "profile_background_picture", columnDefinition = "TEXT")
    private String profileBackgroundPicture;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_picture", columnDefinition = "TEXT")
    private String profilePicture;

    @Column(name = "email")
    private String email;

    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(length = 500)
    private String fcmToken;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFcmToken() {
        return  fcmToken;
    }

    public  void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfileBackgroundPicture() {
        return profileBackgroundPicture;
    }

    public void setProfileBackgroundPicture(String profileBackgroundPicture) {
        this.profileBackgroundPicture = profileBackgroundPicture;
    }
}