package com.Synapse.ProyekPBO.Models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class FasilitasDto {

    @NotEmpty(message = "required")
    private String nameFasilitas;
    private MultipartFile ImageFile;

    public String getNameFasilitas() {
        return nameFasilitas;
    }

    public void setNameFasilitas(String nameFasilitas) {
        this.nameFasilitas = nameFasilitas;
    }

    public MultipartFile getImageFile() {
        return ImageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        ImageFile = imageFile;
    }
}
