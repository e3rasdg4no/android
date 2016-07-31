package com.eragano.eraganoapps.pinjaman;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Pinjaman4 extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    //FROM LANGKAH 2 PINJAMAN
    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;
    //FROM LANGKAH 3 Pinjaman
    String namasuamiistri, tempatlahir2, tanggallahir2, pendidikan2, pekerjaan, penghasilan;

    String berusahasejak, bidangusaha, jumlahkaryawan, alamatusaha, nomortelepon2, statuskepemilikan, ditempatisejak;
    EditText bs, bu, jk, au, nt, ds;
    Spinner sk;
    String array_spinner[];

    DatePickerDialog datepicker;
    DatePickerDialog datepicker2;
    SimpleDateFormat dateFormat;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman4, container, false);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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

        bs = (EditText) v.findViewById(R.id.pberusahasejak);
        bu = (EditText) v.findViewById(R.id.pbidangusaha);
        jk = (EditText) v.findViewById(R.id.pjumlahkaryawan);
        au = (EditText) v.findViewById(R.id.palamatusaha);
        nt = (EditText) v.findViewById(R.id.pnomortelepon2);
        ds = (EditText) v.findViewById(R.id.pditempatisejak);

        bs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                bs.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        ds.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker2.show();
                return true;
            }
        });
        Calendar newCalendar2 = Calendar.getInstance();
        datepicker2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ds.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar2.get(Calendar.YEAR), newCalendar2.get(Calendar.MONTH), newCalendar2.get(Calendar.DAY_OF_MONTH));


        sk = (Spinner) v.findViewById(R.id.spinnerstatuskepemilikan);
        array_spinner=new String[3];
        array_spinner[0]="Milik Sendiri";
        array_spinner[1]="Sewa";
        array_spinner[2]="Keluarga";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        sk.setAdapter(adapter);

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    berusahasejak = bs.getText().toString();
                    bidangusaha = bu.getText().toString();
                    jumlahkaryawan = jk.getText().toString();
                    alamatusaha = au.getText().toString();
                    nomortelepon2 = nt.getText().toString();
                    ditempatisejak = ds.getText().toString();
                    statuskepemilikan = sk.getSelectedItem().toString();

                    Pinjaman5 as = new Pinjaman5();
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
                    //SEKARANG
                    bundle.putString("berusahasejak", berusahasejak);
                    bundle.putString("bidangusaha", bidangusaha);
                    bundle.putString("jumlahkaryawan", jumlahkaryawan);
                    bundle.putString("alamatusaha", alamatusaha);
                    bundle.putString("nomortelepon2", nomortelepon2);
                    bundle.putString("statuskepemilikan", statuskepemilikan);
                    bundle.putString("ditempatisejak", ditempatisejak);
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }

    public String cekisi(){
        kondisi_isi="false";
        if(bs.getText().toString().equals("")){
            bs.setError("Kolom ini harus diisi");
            bs.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(bu.getText().toString().equals("")){
            bu.setError("Kolom ini harus diisi");
            bu.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(jk.getText().toString().equals("")){
            jk.setError("Kolom ini harus diisi");
            jk.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(au.getText().toString().equals("")){
            au.setError("Kolom ini harus diisi");
            au.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(nt.getText().toString().equals("")){
            nt.setError("Kolom ini harus diisi");
            nt.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ds.getText().toString().equals("")){
            ds.setError("Kolom ini harus diisi");
            ds.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else {
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
