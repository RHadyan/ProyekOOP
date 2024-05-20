package com.Synapse.ProyekPBO.Models;

public class KomentarDto {
    private String username;
    private String komentar;
    private Integer rating;
    private String statusTampil;

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
