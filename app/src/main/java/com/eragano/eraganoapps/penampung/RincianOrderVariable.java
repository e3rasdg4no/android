package com.eragano.eraganoapps.penampung;

/**
 * Created by M Dimas Faizin on 4/15/2016.
 */
public class RincianOrderVariable {


    /**
     * id_penjualan : 1
     * list_produk : Pilar F1 / 1 Pack
     Panex F1 / 1 Pack
     Transformer / 1 Pack
     Starner / 8 Pack
     Diva F1 / 3 Pack
     * tanggal_pemesanan : 14 April 2016
     * username : faiz
     * total_bayar : 1795000
     * dikirim : Sedang Diki
     * status_terakhir : Sudah Diterima
     */

    private String id_penjualan;
    private String list_produk;
    private String tanggal_pemesanan;
    private String username;
    private String total_bayar;
    private String dikirim;
    private String status_terakhir;

    public String getId_penjualan() {
        return id_penjualan;
    }

    public void setId_penjualan(String id_penjualan) {
        this.id_penjualan = id_penjualan;
    }

    public String getList_produk() {
        return list_produk;
    }

    public void setList_produk(String list_produk) {
        this.list_produk = list_produk;
    }

    public String getTanggal_pemesanan() {
        return tanggal_pemesanan;
    }

    public void setTanggal_pemesanan(String tanggal_pemesanan) {
        this.tanggal_pemesanan = tanggal_pemesanan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getDikirim() {
        return dikirim;
    }

    public void setDikirim(String dikirim) {
        this.dikirim = dikirim;
    }

    public String getStatus_terakhir() {
        return status_terakhir;
    }

    public void setStatus_terakhir(String status_terakhir) {
        this.status_terakhir = status_terakhir;
    }
}
