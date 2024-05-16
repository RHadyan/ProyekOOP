package com.Synapse.ProyekPBO.Models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name ="tbFasilitas" )
public class FasilitasModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameFasilitas;
    private Date createAt;
    private String imageFileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFasilitas() {
        return nameFasilitas;
    }

    public void setNameFasilitas(String nameFasilitas) {
        this.nameFasilitas = nameFasilitas;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
