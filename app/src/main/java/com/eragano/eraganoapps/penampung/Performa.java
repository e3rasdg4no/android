package com.eragano.eraganoapps.penampung;

/**
 * Created by M Dimas Faizin on 5/20/2016.
 */
public class Performa {

    /**
     * id_performa : 1
     * user : faiz
     * nama_petani : faizfiaz
     * jenis_tanaman : cabai
     * tanggal_pelaporan : 23 May 2016
     * gambar : test.jpg
     * hambatan : test
     * keterangan : test
     */

    private String id_performa;
    private String user;
    private String nama_petani;
    private String jenis_tanaman;
    private String tanggal_pelaporan;
    private String gambar;
    private String hambatan;
    private String keterangan;

    public String getId_performa() {
        return id_performa;
    }

    public void setId_performa(String id_performa) {
        this.id_performa = id_performa;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNama_petani() {
        return nama_petani;
    }

    public void setNama_petani(String nama_petani) {
        this.nama_petani = nama_petani;
    }

    public String getJenis_tanaman() {
        return jenis_tanaman;
    }

    public void setJenis_tanaman(String jenis_tanaman) {
        this.jenis_tanaman = jenis_tanaman;
    }

    public String getTanggal_pelaporan() {
        return tanggal_pelaporan;
    }

    public void setTanggal_pelaporan(String tanggal_pelaporan) {
        this.tanggal_pelaporan = tanggal_pelaporan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getHambatan() {
        return hambatan;
    }

    public void setHambatan(String hambatan) {
        this.hambatan = hambatan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
