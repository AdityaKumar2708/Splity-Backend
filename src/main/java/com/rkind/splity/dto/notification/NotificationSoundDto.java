package com.rkind.splity.dto.notification;

public class NotificationSoundDto {

    private String name;

    private String fileName;

    public NotificationSoundDto() {
    }

    public NotificationSoundDto(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}