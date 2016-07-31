package com.eragano.eraganoapps.penampung;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class Artikel {

    @SerializedName("judul")
    private String judul;
    @SerializedName("isi")
    private String isi;
    @SerializedName("picture")
    private String picture;
    List<Artikel> artikels;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(List<Artikel> artikels) {
        this.artikels = artikels;
    }
}
