package com.eragano.eraganoapps.adapter;

/**
 * Created by M Dimas Faizin on 4/6/2016.
 */
public class RincianPinjamanJson {

    /**
     * id_pinjaman : 1
     * tanggal_pendaftaran : 06-Apr-2016
     * status : Belum Disetujui
     */

    private String id_pinjaman;
    private String tanggal_pendaftaran;
    private String status;

    public String getId_pinjaman() {
        return id_pinjaman;
    }

    public void setId_pinjaman(String id_pinjaman) {
        this.id_pinjaman = id_pinjaman;
    }

    public String getTanggal_pendaftaran() {
        return tanggal_pendaftaran;
    }

    public void setTanggal_pendaftaran(String tanggal_pendaftaran) {
        this.tanggal_pendaftaran = tanggal_pendaftaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
