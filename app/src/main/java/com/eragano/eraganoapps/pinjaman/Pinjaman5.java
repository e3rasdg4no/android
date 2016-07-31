package com.eragano.eraganoapps.pinjaman;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

public class Pinjaman5  extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    //FROM LANGKAH 2 PINJAMAN
    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;
    //FROM LANGKAH 3 Pinjaman
    String namasuamiistri, tempatlahir2, tanggallahir2, pendidikan2, pekerjaan, penghasilan;
    //FROM LANGKAH 4 Pinjaman
    String berusahasejak, bidangusaha, jumlahkaryawan, alamatusaha, nomortelepon2, statuskepemilikan, ditempatisejak;

    String namalengkap2, jeniskelamin, hubungan, alamattinggal, nomorteleponrumah, nomorhp;
    EditText nl, hb, at, nt, nh;
    Spinner jk;
    String array_spinner[];
    String kondisi_isi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman5, container, false);
        //LANGKAH 1
        namalengkap = getArguments().getString("namalengkap");
        tempatlahir = getArguments().getString("tempatlahir");
        tanggallahir = getArguments().getString("tanggallahir");
        namagadis = getArguments().getString("namagadis");
        pendidikan = getArguments().getString("pendidikan");
        alamat = getArguments().getString("alamat");
        kecamatan = getArguments().getString("kecamatan");
        kabupaten = getArguments().getString("kabupaten");
        provinsi = getArguments().getString("provinsi");
        kodepos = getArguments().getString("kodepos");
        //LANGKAH 2
        nomortelepon = getArguments().getString("nomortelepon");
        noktp = getArguments().getString("noktp");
        tanggalktp = getArguments().getString("tanggalktp");
        nonpwp = getArguments().getString("nonpwp");
        jumlahtanggungan = getArguments().getString("jumlahtanggungan");
        nokk = getArguments().getString("nokk");
        statustempat = getArguments().getString("statustempat");
        lamatinggal = getArguments().getString("lamatinggal");
        statuskawin = getArguments().getString("statuskawin");
        //LANGKAH 3
        namasuamiistri =  getArguments().getString("namasuamiistri");
        tempatlahir2 =  getArguments().getString("tempatlahir2");
        tanggallahir2 = getArguments().getString("tanggallahir2");
        pekerjaan =  getArguments().getString("pekerjaan");
        penghasilan = getArguments().getString("penghasilan");
        pendidikan2 = getArguments().getString("pendidikan2");
        //LANGKAH 4
        berusahasejak =  getArguments().getString("berusahasejak");
        bidangusaha =  getArguments().getString("bidangusaha");
        jumlahkaryawan =  getArguments().getString("jumlahkaryawan");
        alamatusaha =  getArguments().getString("alamatusaha");
        nomortelepon2 =  getArguments().getString("nomortelepon2");
        statuskepemilikan =  getArguments().getString("statuskepemilikan");
        ditempatisejak =  getArguments().getString("ditempatisejak");

        nl = (EditText) v.findViewById(R.id.pnamalengkap2);
        hb = (EditText) v.findViewById(R.id.phubungan);
        at = (EditText) v.findViewById(R.id.palamattinggal);
        nt = (EditText) v.findViewById(R.id.pnomorteleponrumah);
        nh = (EditText) v.findViewById(R.id.pnomorhp);

        jk = (Spinner) v.findViewById(R.id.pjeniskelamin);
        array_spinner=new String[2];
        array_spinner[0]="Laki-laki";
        array_spinner[1]="Perempuan";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        jk.setAdapter(adapter);

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    namalengkap2 = nl.getText().toString();
                    jeniskelamin = hb.getText().toString();
                    alamattinggal = at.getText().toString();
                    nomorteleponrumah = nt.getText().toString();
                    nomorhp = nh.getText().toString();
                    hubungan = jk.getSelectedItem().toString();

                    Pinjaman6 as = new Pinjaman6();
                    Bundle bundle = new Bundle();
                    //LANGKAH 1
                    bundle.putString("namalengkap", namalengkap);
                    bundle.putString("tempatlahir", tempatlahir);
                    bundle.putString("tanggallahir", tanggallahir);
                    bundle.putString("namagadis", namagadis);
                    bundle.putString("pendidikan", pendidikan);
                    bundle.putString("alamat", alamat);
                    bundle.putString("kecamatan", kecamatan);
                    bundle.putString("kabupaten", kabupaten);
                    bundle.putString("provinsi", provinsi);
                    bundle.putString("kodepos", kodepos);
                    //LANGKAH 2
                    bundle.putString("nomortelepon", nomortelepon);
                    bundle.putString("noktp", noktp);
                    bundle.putString("tanggalktp", tanggalktp);
                    bundle.putString("nonpwp", nonpwp);
                    bundle.putString("jumlahtanggungan", jumlahtanggungan);
                    bundle.putString("nokk", nokk);
                    bundle.putString("statustempat", statustempat);
                    bundle.putString("lamatinggal", lamatinggal);
                    bundle.putString("statuskawin", statuskawin);
                    //LANGKAH 3
                    bundle.putString("namasuamiistri", namasuamiistri);
                    bundle.putString("tempatlahir2", tempatlahir2);
                    bundle.putString("tanggallahir2", tanggallahir2);
                    bundle.putString("pendidikan2", pendidikan2);
                    bundle.putString("pekerjaan", pekerjaan);
                    bundle.putString("penghasilan", penghasilan);
                    //LANGKAH 4
                    bundle.putString("berusahasejak", berusahasejak);
                    bundle.putString("bidangusaha", bidangusaha);
                    bundle.putString("jumlahkaryawan", jumlahkaryawan);
                    bundle.putString("alamatusaha", alamatusaha);
                    bundle.putString("nomortelepon2", nomortelepon2);
                    bundle.putString("statuskepemilikan", statuskepemilikan);
                    bundle.putString("ditempatisejak", ditempatisejak);
                    //SEKARANG
                    bundle.putString("namalengkap2", namalengkap2);
                    bundle.putString("jeniskelamin", jeniskelamin);
                    bundle.putString("hubungan", hubungan);
                    bundle.putString("alamattinggal", alamattinggal);
                    bundle.putString("nomorteleponrumah", nomorteleponrumah);
                    bundle.putString("nomorhp", nomorhp);

                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }
    public String cekisi(){
        kondisi_isi="false";
        if(nl.getText().toString().equals("")){
            nl.setError("Kolom ini harus diisi");
            nl.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(hb.getText().toString().equals("")){
            hb.setError("Kolom ini harus diisi");
            hb.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(at.getText().toString().equals("")){
            at.setError("Kolom ini harus diisi");
            at.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(nt.getText().toString().equals("")){
            nt.setError("Kolom ini harus diisi");
            nt.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(nh.getText().toString().equals("")){
            nh.setError("Kolom ini harus diisi");
            nh.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
