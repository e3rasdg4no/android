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

public class Pinjaman6 extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    //FROM LANGKAH 2 PINJAMAN
    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;
    //FROM LANGKAH 3 Pinjaman
    String namasuamiistri, tempatlahir2, tanggallahir2, pendidikan2, pekerjaan, penghasilan;
    //FROM LANGKAH 4 Pinjaman
    String berusahasejak, bidangusaha, jumlahkaryawan, alamatusaha, nomortelepon2, statuskepemilikan, ditempatisejak;
    //FROM LANGKAH 5 Pinjaman
    String namalengkap2, jeniskelamin, hubungan, alamattinggal, nomorteleponrumah, nomorhp;

    String penghasilanperbulan, totalbiaya, proyeksikeuntungan, penghasilanlainnya, totalpinjamanlain,
            sisawaktuangsuran, angsuranpinjamanlain, totalpenghasilan, autodebet;
    EditText pp,tb,pk,pl,tp,sw,ap,tp2;
    Spinner ad;
    String array_spinner[];
    String kondisi_isi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman6, container, false);
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
        //LANGKAH 5
        namalengkap2 =getArguments().getString("namalengkap2");
        jeniskelamin =getArguments().getString("jeniskelamin");
        hubungan =getArguments().getString("hubungan");
        alamattinggal =getArguments().getString("alamattinggal");
        nomorteleponrumah =getArguments().getString("nomorteleponrumah");
        nomorhp =getArguments().getString("nomorhp");

        pp = (EditText) v.findViewById(R.id.ppenghasilanperbulan);
        tb = (EditText) v.findViewById(R.id.ptotalbiaya);
        pk = (EditText) v.findViewById(R.id.pproyeksikeuntungan);
        pl = (EditText) v.findViewById(R.id.ppenghasilanlainnya);
        tp = (EditText) v.findViewById(R.id.ptotalpinjamanlain);
        sw = (EditText) v.findViewById(R.id.psisawaktuangsuran);
        ap = (EditText) v.findViewById(R.id.pangsuranpinjamanlain);
        tp2 = (EditText) v.findViewById(R.id.ptotalpenghasilan);

        ad = (Spinner) v.findViewById(R.id.spinnerautodebet);
        array_spinner=new String[2];
        array_spinner[0]="Bersedia";
        array_spinner[1]="Tidak Bersedia";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        ad.setAdapter(adapter);

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().toString().equals("true")) {
                    penghasilanperbulan = pp.getText().toString();
                    totalbiaya = tb.getText().toString();
                    proyeksikeuntungan = pk.getText().toString();
                    penghasilanlainnya = pl.getText().toString();
                    totalpinjamanlain = tp.getText().toString();
                    sisawaktuangsuran = sw.getText().toString();
                    angsuranpinjamanlain = ap.getText().toString();
                    totalpenghasilan = tp2.getText().toString();
                    autodebet = ad.getSelectedItem().toString();

                    Pinjaman7 as = new Pinjaman7();
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
                    //LANGKAH 5
                    bundle.putString("namalengkap2", namalengkap2);
                    bundle.putString("jeniskelamin", jeniskelamin);
                    bundle.putString("hubungan", hubungan);
                    bundle.putString("alamattinggal", alamattinggal);
                    bundle.putString("nomorteleponrumah", nomorteleponrumah);
                    bundle.putString("nomorhp", nomorhp);
                    //SEKARANG
                    //String penghasilanperbulan, totalbiaya, proyeksikeuntungan, penghasilanlainnya, totalpinjamanlain,
                    //      sisawaktuangsuran, angsuranpinjamanlain, totalpenghasilan, autodebet;
                    bundle.putString("penghasilanperbulan", penghasilanperbulan);
                    bundle.putString("totalbiaya", totalbiaya);
                    bundle.putString("proyeksikeuntungan", proyeksikeuntungan);
                    bundle.putString("penghasilanlainnya", penghasilanlainnya);
                    bundle.putString("totalpinjamanlain", totalpinjamanlain);
                    bundle.putString("sisawaktuangsuran", sisawaktuangsuran);
                    bundle.putString("angsuranpinjamanlain", angsuranpinjamanlain);
                    bundle.putString("totalpenghasilan", totalpenghasilan);
                    bundle.putString("autodebet", autodebet);

                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }
    public String cekisi(){
        kondisi_isi="false";
        penghasilanperbulan = pp.getText().toString();
        totalbiaya = tb.getText().toString();
        proyeksikeuntungan = pk.getText().toString();
        penghasilanlainnya = pl.getText().toString();
        totalpinjamanlain = tp.getText().toString();
        sisawaktuangsuran = sw.getText().toString();
        angsuranpinjamanlain = ap.getText().toString();
        totalpenghasilan = tp2.getText().toString();
        autodebet = ad.getSelectedItem().toString();

        if(pp.getText().toString().equals("")){
            pp.setError("Kolom ini harus diisi");
            pp.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(tb.getText().toString().equals("")){
            tb.setError("Kolom ini harus diisi");
            tb.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(pk.getText().toString().equals("")){
            pk.setError("Kolom ini harus diisi");
            pk.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(pl.getText().toString().equals("")){
            pl.setError("Kolom ini harus diisi");
            pl.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tp.getText().toString().equals("")){
            tp.setError("Kolom ini harus diisi");
            tp.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(sw.getText().toString().equals("")){
            sw.setError("Kolom ini harus diisi");
            sw.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ap.getText().toString().equals("")){
            ap.setError("Kolom ini harus diisi");
            ap.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tp2.getText().toString().equals("")){
            tp2.setError("Kolom ini harus diisi");
            tp2.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else {
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
