package com.eragano.eraganoapps.login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Entity;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.asuransi.klaim_asuransi.KlaimAsuransi2;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends Activity {
    private String array_spinner[];

    EditText nama, uname, pass, pass2, tempat, tanggal, alamat, desa, kec, kab, prov, kode, tel, ktp, tglktp;
    String namalengkap, username, password, password2, tempatlahir, tanggallahir, alamatreg, desakelurahan, kecamatanreg,
            kabupatenkota, provinsireg,kodepos, notelepon, noktp, tanggalktp;
    Spinner spin;
    DatePickerDialog datepicker;
    DatePickerDialog datepicker2;
    SimpleDateFormat dateFormat;
    String ip;
    String kondisi_isi;
    private Exception exceptionToBeThrown;
    private Dialog loadingDialog;
    String cek_ada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        ip = getResources().getString(R.string.ip);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        nama = (EditText) findViewById(R.id.namalengkap);
        uname = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);
        tempat = (EditText) findViewById(R.id.tempatlahir);
        tanggal = (EditText) findViewById(R.id.tanggallahir);
        alamat = (EditText) findViewById(R.id.alamatreg);
        desa = (EditText) findViewById(R.id.desakelurahan);
        kec = (EditText) findViewById(R.id.kecamatanreg);
        kab = (EditText) findViewById(R.id.kabupatenkota);
        prov = (EditText) findViewById(R.id.provinsireg);
        kode = (EditText) findViewById(R.id.kodepos);
        tel = (EditText) findViewById(R.id.notelepon);
        ktp = (EditText) findViewById(R.id.noktp);
        tglktp = (EditText) findViewById(R.id.tanggalktp);

        tanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });

        tglktp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker2.show();
                return true;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar newCalendar2 = Calendar.getInstance();
        datepicker2 = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tglktp.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar2.get(Calendar.YEAR), newCalendar2.get(Calendar.MONTH), newCalendar2.get(Calendar.DAY_OF_MONTH));

        //MENDAFTAR SEBAGAI
        array_spinner=new String[2];
        array_spinner[0]="Petani";
        array_spinner[1]="Umum";
        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter( RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
        spin.setAdapter(adapter);

        /*
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Drawable warning = (Drawable) getResources().getDrawable(R.drawable.cek);
                    warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());
                    uname.setError("Nama Pengguna dapat dipakai", warning);
                    uname.setFocusable(true);
                    cekdata();
                }
            }
        });*/
        /*
        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (pass2.getText().toString().equals(pass.getText().toString())) {
                        Drawable warning = (Drawable) getResources().getDrawable(R.drawable.cek);
                        warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());
                        pass2.setError("Diterima", warning);
                    } else {
                        pass2.setError("Kata Kunci Tidak sama");
                        pass2.setFocusable(true);
                    }
                }
            }
        });
        */
        Button btndaftar = (Button) findViewById(R.id.lanjutkan);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekisi();
                if(cekisi().equals("true")){
                cekdata();
                    if(cekdata().equals("ada")) {
                        kirimdata();
                    }
                    else{
                        uname.setError("Nama Pengguna Telah dipakai");
                        uname.requestFocus();
                    }
                }
                else{
                }
            }
        });
    }
    public String cekdata(){
        loadingDialog = ProgressDialog.show(RegisterActivity.this, "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("username", uname.getText().toString()));
        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/cekdata.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(request);
            String str = EntityUtils.toString(response.getEntity());
            //Log.d("coba ",str);
            if(str.equals("tersedia")){
               // kirimdata();
                cek_ada="ada";
                loadingDialog.dismiss();
            }
            else{
                cek_ada="tidak";
                loadingDialog.dismiss();
                //uname.setError("Nama Pengguna Telah dipakai..");
                //uname.setFocusable(true);
                Toast.makeText(RegisterActivity.this,"Rubah nama pengguna anda",Toast.LENGTH_SHORT).show();
                // /Toast.makeText(RegisterActivity.this,"USERNAME TELAH DIPAKAI, UBAH USERNAME LAIN",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            loadingDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        return cek_ada;
    }

    public void kirimdata(){
        loadingDialog = ProgressDialog.show(RegisterActivity.this, "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(15);
        nameValuePairs.add(new BasicNameValuePair("nama_lengkap", nama.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("username", uname.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", pass.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("sebagai", spin.getSelectedItem().toString()));
        nameValuePairs.add(new BasicNameValuePair("tempat_lahir", tempat.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal_lahir", tanggal.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("alamat", alamat.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("desa_kelurahan", desa.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("kecamatan", kec.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("kabupaten_kota", kab.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("provinsi", prov.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("kodepos", kode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("no_telepon", tel.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("no_ktp", ktp.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal_ktp", tglktp.getText().toString()));

        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/registrasi.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
            String str = EntityUtils.toString(response.getEntity());
            //Log.d("coba ",str);
        }
        catch(Exception e){
            loadingDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        if (exceptionToBeThrown != null) {
            loadingDialog.dismiss();
        } else {
            loadingDialog.dismiss();
            finish();
        }
        //loadingDialog.dismiss();
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }

    public String cekisi(){
        kondisi_isi="false";
        if(nama.getText().toString().equals("")){
            nama.setError("Kolom ini harus diisi");
            nama.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(uname.getText().toString().equals("")){
            uname.setError("Kolom ini harus diisi");
            uname.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals("")){
            pass.setError("Kolom ini harus diisi");
            pass.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tempat.getText().toString().equals("")){
            tempat.setError("Kolom ini harus diisi");
            tempat.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tanggal.getText().toString().equals("")){
            tanggal.setError("Kolom ini harus diisi");
            tanggal.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(alamat.getText().toString().equals("")){
            alamat.setError("Kolom ini harus diisi");
            alamat.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(desa.getText().toString().equals("")){
            desa.setError("Kolom ini harus diisi");
            desa.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kec.getText().toString().equals("")){
            kec.setError("Kolom ini harus diisi");
            kec.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kab.getText().toString().equals("")){
            kab.setError("Kolom ini harus diisi");
            kab.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(prov.getText().toString().equals("")){
            prov.setError("Kolom ini harus diisi");
            prov.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(kode.getText().toString().equals("")){
            kode.setError("Kolom ini harus diisi");
            kode.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tel.getText().toString().equals("")){
            tel.setError("Kolom ini harus diisi");
            tel.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ktp.getText().toString().equals("")){
            ktp.setError("Kolom ini harus diisi");
            ktp.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tglktp.getText().toString().equals("")){
            tglktp.setError("Kolom ini harus diisi");
            tglktp.requestFocus();
            Toast.makeText(RegisterActivity.this,"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }

}
