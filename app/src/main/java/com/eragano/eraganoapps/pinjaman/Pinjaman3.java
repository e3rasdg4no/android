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

public class Pinjaman3 extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    //FROM LANGKAH 2 PINJAMAN
    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;

    String namasuamiistri, tempatlahir2, tanggallahir2, pendidikan2, pekerjaan, penghasilan;
    EditText nsi, tl, tl2, pk, pg;
    Spinner pd;
    String array_spinner[];

    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    String kondisi_isi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman3, container, false);
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

        nsi = (EditText) v.findViewById(R.id.pnamasuamiistri);
        tl = (EditText) v.findViewById(R.id.ptempatlahir2);
        tl2 = (EditText) v.findViewById(R.id.ptanggallahir2);
        pk = (EditText) v.findViewById(R.id.ppekerjaan);
        pg = (EditText) v.findViewById(R.id.ppenghasilan);

        pd = (Spinner) v.findViewById(R.id.spinnerpendidikan2);
        array_spinner=new String[6];
        array_spinner[0]="Tidak tamat SD";
        array_spinner[1]="SD";
        array_spinner[2]="SMP";
        array_spinner[3]="SMA";
        array_spinner[4]="Diploma";
        array_spinner[5]="Sarjana";
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        pd.setAdapter(adapter);

        tl2.setOnTouchListener(new View.OnTouchListener() {
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
                tl2.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().toString().equals("true")) {
                    namasuamiistri = nsi.getText().toString();
                    tempatlahir2 = tl.getText().toString();
                    tanggallahir2 = tl2.getText().toString();
                    pekerjaan = pk.getText().toString();
                    penghasilan = pg.getText().toString();
                    pendidikan2 = pd.getSelectedItem().toString();

                    Pinjaman4 as = new Pinjaman4();
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
                    //SEKARANG
                    bundle.putString("namasuamiistri", namasuamiistri);
                    bundle.putString("tempatlahir2", tempatlahir2);
                    bundle.putString("tanggallahir2", tanggallahir2);
                    bundle.putString("pendidikan2", pendidikan2);
                    bundle.putString("pekerjaan", pekerjaan);
                    bundle.putString("penghasilan", penghasilan);
                    as.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
                }
            }
        });
        return v;
    }
    public String cekisi(){
        kondisi_isi="false";
        if(nsi.getText().toString().equals("")){
            nsi.setError("Kolom ini harus diisi");
            nsi.setFocusable(true);
            Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        }
        else if(tl.getText().toString().equals("")){
            tl.setError("Kolom ini harus diisi");
            tl.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tl2.getText().toString().equals("")){
            tl2.setError("Kolom ini harus diisi");
            tl2.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(pk.getText().toString().equals("")){
            pk.setError("Kolom ini harus diisi");
            pk.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(pg.getText().toString().equals("")){
            pg.setError("Kolom ini harus diisi");
            pg.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }
}
