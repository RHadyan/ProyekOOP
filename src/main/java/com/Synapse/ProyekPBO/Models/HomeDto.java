package com.Synapse.ProyekPBO.Models;

import org.springframework.web.multipart.MultipartFile;

public class HomeDto {
    private MultipartFile ImageFile;


    public MultipartFile getImageFile() {
        return ImageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        ImageFile = imageFile;
    }
}
