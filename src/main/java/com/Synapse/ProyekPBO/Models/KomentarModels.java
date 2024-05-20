package com.Synapse.ProyekPBO.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbKomentar")
public class KomentarModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String komentar;
    private Integer rating;
    private String statusTampil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatusTampil() {
        return statusTampil;
    }

    public void setStatusTampil(String statusTampil) {
        this.statusTampil = statusTampil;
    }
}
