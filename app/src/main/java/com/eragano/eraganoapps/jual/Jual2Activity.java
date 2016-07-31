package com.eragano.eraganoapps.jual;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.squareup.picasso.Picasso;

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

public class Jual2Activity extends AppCompatActivity {
    int pilihan;
    String array_spinner[];
    String picture, nama, tanggal, satuan, jumlah, harga, id_jual;
    EditText nm, tgl, jml, hrg;
    ImageView pict;
    Spinner stn;
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    String formattedDate;

    private Exception exceptionToBeThrown;
    private Dialog loadingDialog;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual2);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout11);
        rl.bringToFront();
        ImageButton btnback = (ImageButton) findViewById(R.id.back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton btnbantu = (ImageButton) findViewById(R.id.bantu);
        btnbantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711232483"));
                if (ActivityCompat.checkSelfPermission(Jual2Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        ip = getResources().getString(R.string.ip);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent i = getIntent();
        picture = i.getStringExtra("PICTURE");
        nama = i.getStringExtra("NAMA");
        tanggal = i.getStringExtra("TANGGAL");
        satuan = i.getStringExtra("SATUAN");
        jumlah = i.getStringExtra("JUMLAH");
        harga = i.getStringExtra("HARGA");
        id_jual = i.getStringExtra("ID");

        //GET POSITION SATUAN
        if(satuan.equals("Gram")){
            pilihan = 0;
        }
        else if(satuan.equals("Kilogram")){
            pilihan = 1;
        }
        else if(satuan.equals("Kuintal")){
            pilihan = 2;
        }
        else if(satuan.equals("Ton")){
            pilihan = 3;
        }
        else if(satuan.equals("Ikat")){
            pilihan = 4;
        }
        else if(satuan.equals("Buah")){
            pilihan = 5;
        }

        pict = (ImageView) findViewById(R.id.image2);
        nm = (EditText) findViewById(R.id.namaproduk2);
        tgl = (EditText) findViewById(R.id.tanggalpanen2);
        stn = (Spinner) findViewById(R.id.spinner3);
        jml = (EditText) findViewById(R.id.jumlah2);
        hrg = (EditText) findViewById(R.id.harga2);


        array_spinner=new String[6];
        array_spinner[0]="Gram";
        array_spinner[1]="Kilogram";
        array_spinner[2]="Kuintal";
        array_spinner[3]="Ton";
        array_spinner[4]="Ikat";
        array_spinner[5]="Buah";
        ArrayAdapter adapter = new ArrayAdapter( Jual2Activity.this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
        stn.setAdapter(adapter);
        stn.setSelection(pilihan);

        final String value = "http://103.236.201.252/android/uploads/"+picture;
        Picasso.with(Jual2Activity.this)
                .load(value)
                .resize(600,600)
                .centerCrop()
                .into(pict);

        nm.setText(nama);
        tgl.setText(tanggal);
        jml.setText(jumlah);
        hrg.setText(harga);

        tgl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(Jual2Activity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());

        Button btnsimpan = (Button) findViewById(R.id.simpan);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimdata();
            }
        });
    }

    public void kirimdata(){
        loadingDialog = ProgressDialog.show(Jual2Activity.this, "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("id_jual", id_jual.trim()));
        nameValuePairs.add(new BasicNameValuePair("nama", nm.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal_panen", tgl.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("satuan", stn.getSelectedItem().toString()));
        nameValuePairs.add(new BasicNameValuePair("harga", hrg.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("stok", jml.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tanggal", formattedDate.trim()));
        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/editjual.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
        }
        catch(Exception e){
            Toast.makeText(Jual2Activity.this, "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        loadingDialog.dismiss();
        Toast.makeText(Jual2Activity.this, "Berhasil", Toast.LENGTH_LONG).show();
    }
}
