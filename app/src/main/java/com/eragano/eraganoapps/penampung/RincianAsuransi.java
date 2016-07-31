package com.eragano.eraganoapps.penampung;

/**
 * Created by M Dimas Faizin on 4/6/2016.
 */
public class RincianAsuransi {


    /**
     * id_pengajuan : 1
     * tanggal_pengajuan : 23-Mar-2016
     * status : Belum Disetujui
     */

    private String id_pengajuan;
    private String tanggal_pengajuan;
    private String status;

    public String getId_pengajuan() {
        return id_pengajuan;
    }

    public void setId_pengajuan(String id_pengajuan) {
        this.id_pengajuan = id_pengajuan;
    }

    public String getTanggal_pengajuan() {
        return tanggal_pengajuan;
    }

    public void setTanggal_pengajuan(String tanggal_pengajuan) {
        this.tanggal_pengajuan = tanggal_pengajuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
