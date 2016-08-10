package com.eragano.eraganoapps.penampung;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class Produk {

    /*
     id_jual : "25"
     user : "dandi"
     image : "IMG_20160806_184616.jpg"
     nama : "nzka"
     tanggal_panen : "18-08-2016"
     satuan : "Kilogram"
     harga : "2000"
     stok : "12"
     tanggal : "06-Aug-2016"
     jumlahJual : "0"
     jumlahPesanan : "0"
     jumlahTerjual : "0"
     */

    private String id_jual;
    private String user;
    private String image;
    private String nama;
    private String tanggal_panen;
    private String satuan;
    private String harga;
    private String stok;
    private String tanggal;
    private String jumlahJual;
    private String jumlahPesanan;
    private String jumlahTerjual;
    private String hargaExternal;
    private String statusTerjual;

    public String getId_jual() {
        return id_jual;
    }

    public void setId_jual(String id_jual) {
        this.id_jual = id_jual;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal_panen() {
        return tanggal_panen;
    }

    public void setTanggal_panen(String tanggal_panen) {
        this.tanggal_panen = tanggal_panen;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getJumlahJual() {
        return jumlahJual;
    }

    public void setJumlahJual(String jumlahJual) {
        this.jumlahJual = jumlahJual;
    }

    public String getJumlahPesanan() {
        return jumlahPesanan;
    }

    public void setJumlahPesanan(String jumlahPesanan) {
        this.jumlahPesanan = jumlahPesanan;
    }

    public String getJumlahTerjual() {
        return jumlahTerjual;
    }

    public void setJumlahTerjual(String jumlahTerjual) {
        this.jumlahTerjual = jumlahTerjual;
    }

    public String getHargaExternal() {
        return hargaExternal;
    }

    public void setHargaExternal(String hargaExternal) {
        this.hargaExternal = hargaExternal;
    }

    public String getStatusTerjual() {
        return statusTerjual;
    }

    public void setStatusTerjual(String statusTerjual) {
        this.statusTerjual = statusTerjual;
    }
}
