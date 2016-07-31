package com.eragano.eraganoapps.asuransi;

import android.app.Activity;
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

import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;

public class Asuransi1 extends android.support.v4.app.Fragment {
    String provinsi, kabupaten, kecamatan, desa, kodepos;
    EditText prov, kab, kec, des, kode;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_asuransi1, container, false);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        prov = (EditText) v.findViewById(R.id.provinsi);
        kab = (EditText) v.findViewById(R.id.kabupaten);
        kec = (EditText) v.findViewById(R.id.kecamatan);
        des = (EditText) v.findViewById(R.id.desa);
        kode = (EditText) v.findViewById(R.id.kode_pos);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().toString().equals("true")) {
                    provinsi = prov.getText().toString();
                    kabupaten = kab.getText().toString();
                    kecamatan = kec.getText().toString();
                    desa = des.getText().toString();
                    kodepos = kode.getText().toString();

                    Asuransi2 as = new Asuransi2();
                    Bundle bundle = new Bundle();
                    bundle.putString("PROVINSI", provinsi);
                    bundle.putString("KABUPATEN", kabupaten);
                    bundle.putString("KECAMATAN", kecamatan);
                    bundle.putString("DESA", desa);
                    bundle.putString("KODEPOS", kodepos);
                    as.setArguments(bundle);
                    //Fragment langkah2Fragment = new Asuransi2();
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                    //fragmentTransaction.replace(R.id.containerView, langkah2Fragment);
                    //fragmentTransaction.addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }

    public String cekisi(){
        kondisi_isi="false";
        if(prov.getText().toString().equals("")){
            prov.setError("Kolom ini harus diisi");
            prov.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(kab.getText().toString().equals("")){
            kab.setError("Kolom ini harus diisi");
            kab.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kec.getText().toString().equals("")){
            kec.setError("Kolom ini harus diisi");
            kec.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(des.getText().toString().equals("")){
            des.setError("Kolom ini harus diisi");
            des.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kode.getText().toString().equals("")){
            kode.setError("Kolom ini harus diisi");
            kode.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }

}

