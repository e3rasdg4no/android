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
import com.eragano.eraganoapps.asuransi.Asuransi2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Pinjaman1 extends android.support.v4.app.Fragment {

    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    EditText nm,tmpt,tgl,nmgadis,alm,kec,kab,prov,kode;
    Spinner pdk;
    String array_spinner[];
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman1, container, false);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        nm = (EditText) v.findViewById(R.id.pnamalengkap);
        tmpt = (EditText) v.findViewById(R.id.ptempatlahir);
        tgl = (EditText) v.findViewById(R.id.ptanggallahir);
        nmgadis = (EditText) v.findViewById(R.id.pnamagadis);
        alm = (EditText) v.findViewById(R.id.palamat);
        kec = (EditText) v.findViewById(R.id.pkecamatan);
        kab = (EditText) v.findViewById(R.id.pkabupaten);
        prov = (EditText) v.findViewById(R.id.pprovinsi);
        kode = (EditText) v.findViewById(R.id.pkodepos);

        pdk = (Spinner) v.findViewById(R.id.spinnerpendidikan);
        array_spinner=new String[6];
        array_spinner[0]="Tidak tamat SD";
        array_spinner[1]="SD";
        array_spinner[2]="SMP";
        array_spinner[3]="SMA";
        array_spinner[4]="Diploma";
        array_spinner[5]="Sarjana";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        pdk.setAdapter(adapter);

        tgl.setOnTouchListener(new View.OnTouchListener() {
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
                tgl.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    namalengkap = nm.getText().toString();
                    tempatlahir = tmpt.getText().toString();
                    tanggallahir = tgl.getText().toString();
                    namagadis = nmgadis.getText().toString();
                    alamat = alm.getText().toString();
                    kecamatan = kec.getText().toString();
                    kabupaten = kab.getText().toString();
                    provinsi = prov.getText().toString();
                    kodepos = kode.getText().toString();
                    pendidikan = pdk.getSelectedItem().toString();

                    Pinjaman2 as = new Pinjaman2();
                    Bundle bundle = new Bundle();
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
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }
    public String cekisi(){
        kondisi_isi="false";
        namalengkap = nm.getText().toString();
        tempatlahir = tmpt.getText().toString();
        tanggallahir = tgl.getText().toString();
        namagadis = nmgadis.getText().toString();
        alamat = alm.getText().toString();
        kecamatan = kec.getText().toString();
        kabupaten = kab.getText().toString();
        provinsi = prov.getText().toString();
        kodepos = kode.getText().toString();
        pendidikan = pdk.getSelectedItem().toString();

        if(nm.getText().toString().equals("")){
            nm.setError("Kolom ini harus diisi");
            nm.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tmpt.getText().toString().equals("")){
            tmpt.setError("Kolom ini harus diisi");
            tmpt.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tgl.getText().toString().equals("")){
            tgl.setError("Kolom ini harus diisi");
            tgl.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(nmgadis.getText().toString().equals("")){
            nmgadis.setError("Kolom ini harus diisi");
            nmgadis.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(alm.getText().toString().equals("")){
            alm.setError("Kolom ini harus diisi");
            alm.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kec.getText().toString().equals("")){
            kec.setError("Kolom ini harus diisi");
            kec.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kab.getText().toString().equals("")){
            kab.setError("Kolom ini harus diisi");
            kab.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(prov.getText().toString().equals("")){
            prov.setError("Kolom ini harus diisi");
            prov.setFocusable(true);
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
