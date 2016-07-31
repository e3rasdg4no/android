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

public class Pinjaman2 extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;

    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;
    EditText ntel, nktp, tglktp, npwp, tanggung, nkk;
    Spinner stempat, ltinggal, stkawin;
    String array_spinner[];
    String array_spinner2[];
    String array_spinner3[];

    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman2, container, false);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

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

        ntel = (EditText) v.findViewById(R.id.pnomortelepon);
        nktp = (EditText) v.findViewById(R.id.pnomorktp);
        tglktp = (EditText) v.findViewById(R.id.ptanggalktp);
        npwp = (EditText) v.findViewById(R.id.pnomornpwp);
        tanggung = (EditText) v.findViewById(R.id.pjumlahtanggungan);
        nkk = (EditText) v.findViewById(R.id.pnomorkk);

        tglktp.setOnTouchListener(new View.OnTouchListener() {
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
                tglktp.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final Spinner stempat = (Spinner) v.findViewById(R.id.spinnerstatus);
        final Spinner ltinggal = (Spinner) v.findViewById(R.id.spinnerlamatinggal);
        final Spinner stkawin = (Spinner) v.findViewById(R.id.spinnerstatus2);

        array_spinner=new String[4];
        array_spinner[0]="Milik Sendiri";
        array_spinner[1]="Sewa/Kontrak";
        array_spinner[2]="Milik Keluarga/Orangtua";
        array_spinner[3]="Rumah Dinas/Instansi";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        stempat.setAdapter(adapter);

        array_spinner2=new String[3];
        array_spinner2[0]="< 2 Tahun";
        array_spinner2[1]="2 - 5 Tahun";
        array_spinner2[2]="> 5 Tahun";
        ArrayAdapter adapter2 = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner2);
        ltinggal.setAdapter(adapter2);

        array_spinner3=new String[3];
        array_spinner3[0]="Menikah";
        array_spinner3[1]="Belum Menikah";
        array_spinner3[2]="Janda/Duda";
        ArrayAdapter adapter3 = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner3);
        stkawin.setAdapter(adapter3);

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    nomortelepon = ntel.getText().toString();
                    noktp = nktp.getText().toString();
                    tanggalktp = tglktp.getText().toString();
                    nonpwp = npwp.getText().toString();
                    jumlahtanggungan = tanggung.getText().toString();
                    nokk = nkk.getText().toString();
                    statustempat = stempat.getSelectedItem().toString();
                    lamatinggal = ltinggal.getSelectedItem().toString();
                    statuskawin = stkawin.getSelectedItem().toString();

                    Pinjaman3 as = new Pinjaman3();
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
                    //SEKARANG
                    bundle.putString("nomortelepon", nomortelepon);
                    bundle.putString("noktp", noktp);
                    bundle.putString("tanggalktp", tanggalktp);
                    bundle.putString("nonpwp", nonpwp);
                    bundle.putString("jumlahtanggungan", jumlahtanggungan);
                    bundle.putString("nokk", nokk);
                    bundle.putString("statustempat", statustempat);
                    bundle.putString("lamatinggal", lamatinggal);
                    bundle.putString("statuskawin", statuskawin);
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }

    public String cekisi(){
        kondisi_isi="false";
        if(ntel.getText().toString().equals("")){
            ntel.setError("Kolom ini harus diisi");
            ntel.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(nktp.getText().toString().equals("")){
            nktp.setError("Kolom ini harus diisi");
            nktp.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tglktp.getText().toString().equals("")){
            tglktp.setError("Kolom ini harus diisi");
            tglktp.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(npwp.getText().toString().equals("")){
            npwp.setError("Kolom ini harus diisi");
            npwp.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tanggung.getText().toString().equals("")){
            tanggung.setError("Kolom ini harus diisi");
            tanggung.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(nkk.getText().toString().equals("")){
            nkk.setError("Kolom ini harus diisi");
            nkk.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else {
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
