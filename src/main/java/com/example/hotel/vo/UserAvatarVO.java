package com.example.hotel.vo;

import org.springframework.web.multipart.MultipartFile;

public class UserAvatarVO {
    private MultipartFile avatar;

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatarUrl(MultipartFile avatar) {
        this.avatar = avatar;
    }
}
