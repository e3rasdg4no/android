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
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

public class Asuransi2 extends android.support.v4.app.Fragment {

    //FROM FRAGMENT 1
    String provinsi, kabupaten, kecamatan, desa, kodepos;
    //THIS FRAGMENT
    String nama_pemilik, nama_kelompok, alamat_kelompok, no_anggota, no_ktp, alamat, nama_penggarap, jumlah_lahan;
    EditText pemilik, kelompok, al_kelompok, no_angg, ktp, alm, n_penggarap, lahan;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_asuransi2, container, false);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Button btn = (Button) v.findViewById(R.id.lanjutkan);

        pemilik = (EditText) v.findViewById(R.id.nama_pemilik);
        kelompok = (EditText) v.findViewById(R.id.nama_kelompok);
        al_kelompok = (EditText) v.findViewById(R.id.alamat_kelompok);
        no_angg = (EditText) v.findViewById(R.id.no_anggota);
        ktp = (EditText) v.findViewById(R.id.no_ktp);
        alm = (EditText) v.findViewById(R.id.alamat);
        n_penggarap = (EditText) v.findViewById(R.id.nama_penggarap);
        lahan = (EditText) v.findViewById(R.id.jumlah_lahan);

        provinsi = getArguments().getString("PROVINSI");
        kabupaten = getArguments().getString("KABUPATEN");
        kecamatan = getArguments().getString("KECAMATAN");
        desa = getArguments().getString("DESA");
        kodepos = getArguments().getString("KODEPOS");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().toString().equals("true")) {
                    nama_pemilik = pemilik.getText().toString();
                    nama_kelompok = kelompok.getText().toString();
                    alamat_kelompok = al_kelompok.getText().toString();
                    no_anggota = no_angg.getText().toString();
                    no_ktp = ktp.getText().toString();
                    alamat = alm.getText().toString();
                    nama_penggarap = n_penggarap.getText().toString();
                    jumlah_lahan = lahan.getText().toString();

                    Asuransi3 as = new Asuransi3();
                    Bundle bundle = new Bundle();
                    //FROM FRAGMENT 1
                    bundle.putString("PROVINSI", provinsi);
                    bundle.putString("KABUPATEN", kabupaten);
                    bundle.putString("KECAMATAN", kecamatan);
                    bundle.putString("DESA", desa);
                    bundle.putString("KODEPOS", kodepos);
                    //THIS FRAGMENT
                    bundle.putString("NAMA_PEMILIK", nama_pemilik);
                    bundle.putString("NAMA_KELOMPOK", nama_kelompok);
                    bundle.putString("ALAMAT_KELOMPOK", alamat_kelompok);
                    bundle.putString("NO_ANGGOTA", no_anggota);
                    bundle.putString("NO_KTP", no_ktp);
                    bundle.putString("ALAMAT", alamat);
                    bundle.putString("NAMA_PENGGARAP", nama_penggarap);
                    bundle.putString("JUMLAH_LAHAN", jumlah_lahan);
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }

    public String cekisi(){
        kondisi_isi="false";
        if(pemilik.getText().toString().equals("")){
            pemilik.setError("Kolom ini harus diisi");
            pemilik.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(kelompok.getText().toString().equals("")){
            kelompok.setError("Kolom ini harus diisi");
            kelompok.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(al_kelompok.getText().toString().equals("")){
            al_kelompok.setError("Kolom ini harus diisi");
            al_kelompok.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(no_angg.getText().toString().equals("")){
            no_angg.setError("Kolom ini harus diisi");
            no_angg.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ktp.getText().toString().equals("")){
            ktp.setError("Kolom ini harus diisi");
            ktp.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(alm.getText().toString().equals("")){
            alm.setError("Kolom ini harus diisi");
            alm.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(n_penggarap.getText().toString().equals("")){
            n_penggarap.setError("Kolom ini harus diisi");
            n_penggarap.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(lahan.getText().toString().equals("")){
            lahan.setError("Kolom ini harus diisi");
            lahan.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}