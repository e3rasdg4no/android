package com.eragano.eraganoapps.penampung;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class Produk {

    /**
     * id_jual : 1
     * user : faiz
     * image : 1.jpg
     * nama : Tomat
     * tanggal_panen : 23-02-2016
     * harga : 20000
     * stok : 10
     * tanggal : 23-02-2016
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
}
