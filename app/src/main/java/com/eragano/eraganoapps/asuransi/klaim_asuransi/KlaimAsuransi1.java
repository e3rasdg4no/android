package com.eragano.eraganoapps.asuransi.klaim_asuransi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class KlaimAsuransi1  extends android.support.v4.app.Fragment {

    SharedPreferences sp;
    EditText nama;
    EditText luas;
    EditText tanaman;
    EditText tanggal;
    EditText lokasi;
    EditText tandaTangan;
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    String ip,tanggal_pengajuan;
    private Exception exceptionToBeThrown;
    private Dialog loadingDialog;
    String user;
    String kondisi_isi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_klaim_asuransi1, container, false);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        final Button btn = (Button) v.findViewById(R.id.lanjutkan);

        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        ip = getActivity().getResources().getString(R.string.ip);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        user = sp.getString("User",null);
        nama = (EditText) v.findViewById(R.id.nama_petani);
        luas =  (EditText) v.findViewById(R.id.luas_lahan);
        tanaman =  (EditText) v.findViewById(R.id.jenis_tanaman);
        tanggal =  (EditText) v.findViewById(R.id.tanggal_mulai);
        lokasi =  (EditText) v.findViewById(R.id.lokasi);
        tandaTangan = (EditText) v.findViewById(R.id.tandatangan);

        tanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        tanggal_pengajuan = df.format(c.getTime());

        btn.setEnabled(false);
        btn.setAlpha(0.5f);

        CheckBox mCheckBox= ( CheckBox ) v.findViewById(R.id.checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btn.setEnabled(true);
                    btn.setAlpha(1f);

                } else {
                    btn.setEnabled(false);
                    btn.setAlpha(0.5f);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    kirimdata();
                    if (exceptionToBeThrown != null) {

                    } else {
                        Fragment klaim2Fragment = new KlaimAsuransi2();
                        fragmentTransaction.replace(R.id.containerView, klaim2Fragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        return v;
    }
    public void kirimdata(){
        loadingDialog = ProgressDialog.show(getActivity(),  "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        String username = user;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("nama", nama.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("luas_lahan", luas.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("jenis_tanaman", tanaman.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal", tanggal.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("lokasi", lokasi.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanda_tangan", tandaTangan.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal_pengajuan", tanggal_pengajuan));
        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/klaim_asuransi.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }

    public String cekisi(){
        kondisi_isi="false";
        if(nama.getText().toString().equals("")){
            nama.setError("Kolom ini harus diisi");
            nama.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(luas.getText().toString().equals("")){
            luas.setError("Kolom ini harus diisi");
            luas.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tanaman.getText().toString().equals("")){
            tanaman.setError("Kolom ini harus diisi");
            tanaman.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tanggal.getText().toString().equals("")){
            tanggal.setError("Kolom ini harus diisi");
            tanggal.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(lokasi.getText().toString().equals("")){
            lokasi.setError("Kolom ini harus diisi");
            lokasi.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tandaTangan.getText().toString().equals("")){
            tandaTangan.setError("Kolom ini harus diisi");
            tandaTangan.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }

}
