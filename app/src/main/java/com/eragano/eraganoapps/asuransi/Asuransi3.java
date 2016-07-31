package com.eragano.eraganoapps.asuransi;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

public class Asuransi3  extends android.support.v4.app.Fragment {


    //FROM FRAGMENT 1
    String provinsi, kabupaten, kecamatan, desa, kodepos;
    //FROM FRAGMENT 2
    String nama_pemilik, nama_kelompok, alamat_kelompok, no_anggota, no_ktp, alamat, nama_penggarap, jumlah_lahan;
    //THIS FRAGMENT
    String dusun, jumlah_petak, batas_utara, batas_selatan, batas_timur, batas_barat, luas_lahan;
    EditText dsn, petak, utara, selatan, timur, barat, luas;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_asuransi3, container, false);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        dsn = (EditText) v.findViewById(R.id.dusun);
        petak = (EditText) v.findViewById(R.id.jumlah_petak);
        utara = (EditText) v.findViewById(R.id.batas_utara);
        selatan = (EditText) v.findViewById(R.id.batas_selatan);
        timur = (EditText) v.findViewById(R.id.batas_timur);
        barat = (EditText) v.findViewById(R.id.batas_barat);
        luas = (EditText) v.findViewById(R.id.luas_lahan2);

        provinsi = getArguments().getString("PROVINSI");
        kabupaten = getArguments().getString("KABUPATEN");
        kecamatan = getArguments().getString("KECAMATAN");
        desa = getArguments().getString("DESA");
        kodepos = getArguments().getString("KODEPOS");
        nama_pemilik = getArguments().getString("NAMA_PEMILIK");
        nama_kelompok = getArguments().getString("NAMA_KELOMPOK");
        alamat_kelompok = getArguments().getString("ALAMAT_KELOMPOK");
        no_anggota = getArguments().getString("NO_ANGGOTA");
        no_ktp = getArguments().getString("NO_KTP");
        alamat = getArguments().getString("ALAMAT");
        nama_penggarap = getArguments().getString("NAMA_PENGGARAP");
        jumlah_lahan = getArguments().getString("JUMLAH_LAHAN");


        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().toString().equals("true")) {
                    dusun = dsn.getText().toString();
                    jumlah_petak = petak.getText().toString();
                    batas_utara = utara.getText().toString();
                    batas_selatan = selatan.getText().toString();
                    batas_timur = timur.getText().toString();
                    batas_barat = barat.getText().toString();
                    luas_lahan = luas.getText().toString();

                    Asuransi4 as = new Asuransi4();
                    Bundle bundle = new Bundle();
                    //FROM FRAGMENT1
                    bundle.putString("PROVINSI", provinsi);
                    bundle.putString("KABUPATEN", kabupaten);
                    bundle.putString("KECAMATAN", kecamatan);
                    bundle.putString("DESA", desa);
                    bundle.putString("KODEPOS", kodepos);
                    //FROM FRAGMENT2
                    bundle.putString("NAMA_PEMILIK", nama_pemilik);
                    bundle.putString("NAMA_KELOMPOK", nama_kelompok);
                    bundle.putString("ALAMAT_KELOMPOK", alamat_kelompok);
                    bundle.putString("NO_ANGGOTA", no_anggota);
                    bundle.putString("NO_KTP", no_ktp);
                    bundle.putString("ALAMAT", alamat);
                    bundle.putString("NAMA_PENGGARAP", nama_penggarap);
                    bundle.putString("JUMLAH_LAHAN", jumlah_lahan);
                    //THIS FRAGMENT
                    bundle.putString("DUSUN", dusun);
                    bundle.putString("JUMLAH_PETAK", jumlah_petak);
                    bundle.putString("BATAS_UTARA", batas_utara);
                    bundle.putString("BATAS_SELATAN", batas_selatan);
                    bundle.putString("BATAS_TIMUR", batas_timur);
                    bundle.putString("BATAS_BARAT", batas_barat);
                    bundle.putString("LUAS_LAHAN", luas_lahan);
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }

    public String cekisi(){
        kondisi_isi="false";
        if(dsn.getText().toString().equals("")){
            dsn.setError("Kolom ini harus diisi");
            dsn.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(petak.getText().toString().equals("")){
            petak.setError("Kolom ini harus diisi");
            petak.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(utara.getText().toString().equals("")){
            utara.setError("Kolom ini harus diisi");
            utara.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(selatan.getText().toString().equals("")){
            selatan.setError("Kolom ini harus diisi");
            selatan.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(timur.getText().toString().equals("")){
            timur.setError("Kolom ini harus diisi");
            timur.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(barat.getText().toString().equals("")){
            barat.setError("Kolom ini harus diisi");
            barat.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(luas.getText().toString().equals("")){
            luas.setError("Kolom ini harus diisi");
            luas.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
