package com.eragano.eraganoapps.ecommerce;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Ecommerce2Activity extends Fragment {
    View v;
    int i;
    String username,id,nama,picture,ukuran,harga,deskripsi,anjuran,satuan,keterangan;
    TextView nm, uk, hrg, des, anj, sat, ket,des2;
    ImageView pcr;
    Button back,beli;
    SharedPreferences sp;
    private Dialog loadingDialog;
    private Exception exceptionToBeThrown;
    String ip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_ecommerce2, container, false);

        ip = getResources().getString(R.string.ip);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        username = sp.getString("User", null);

        beli = (Button) v.findViewById(R.id.btn_beli);
        back = (Button) v.findViewById(R.id.btn_kembali);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        nm = (TextView) v.findViewById(R.id.title_produk);
        pcr = (ImageView) v.findViewById(R.id.image_produk);
        uk = (TextView) v.findViewById(R.id.ukuran);
        hrg = (TextView) v.findViewById(R.id.hargaproduk);
        des2 = (TextView) v.findViewById(R.id.deskripsi);

        id = getArguments().getString("ID_PRODUCT");
        nama = getArguments().getString("NAMA");
        keterangan = getArguments().getString("KETERANGAN");
        picture = getArguments().getString("PICTURE");
        ukuran = getArguments().getString("UKURAN");
        harga = getArguments().getString("HARGA");
        deskripsi = getArguments().getString("DESKRIPSI");
        anjuran = getArguments().getString("ANJURAN");
        satuan = getArguments().getString("SATUAN");

        nm.setText(keterangan+" "+nama);
        String value = "http://103.236.201.252/android/image/"+picture;
        Picasso.with(getActivity())
                .load(value)
                .into(pcr);
        uk.setText("(" + ukuran + ")");

        String x = harga;
        if(x.length()==4){
            x = x.substring(0,1)+"."+x.substring(1);
        }
        else if(x.length()==5){
            x = x.substring(0,2)+"."+x.substring(2);
        }
        else if(x.length()==6){
            x = x.substring(0,3)+"."+x.substring(3);
        }
        else if(x.length()==7){
            x = x.substring(0,1)+"."+x.substring(1,4)+"."+x.substring(4);
        }
        else if(x.length()==8){
            x = x.substring(0,2)+"."+x.substring(2,5)+"."+x.substring(5);
        }
        else if(x.length()==9){
            x = x.substring(0,3)+"."+x.substring(3,6)+"."+x.substring(6);
        }
        else if(x.length()==10){
            x = x.substring(0,4)+"."+x.substring(4,7)+"."+x.substring(7);
        }

        hrg.setText("IDR " + x + "/" + satuan);

        i=1;
        final View line = v.findViewById(R.id.view);
        final View line2 = v.findViewById(R.id.view2);
        final TextView des = (TextView) v.findViewById(R.id.deskripsi_pakai);
        final TextView atur = (TextView) v.findViewById(R.id.aturan);
        final TextView jumlah = (TextView) v.findViewById(R.id.jumlah_produk);
        des2.setText(deskripsi);
        des.setTextColor(Color.parseColor("#62C16F"));
        line.setVisibility(View.INVISIBLE);
        jumlah.setText(String.valueOf(i));

        final Button tambah = (Button) v.findViewById(R.id.btn_tambah);
        final Button minus = (Button) v.findViewById(R.id.btn_minus);
        minus.setEnabled(false);
        minus.setAlpha(0.5f);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus.setEnabled(true);
                minus.setAlpha(1f);
                i = i + 1;
                jumlah.setText(String.valueOf(i));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i - 1;
                jumlah.setText(String.valueOf(i));
                if (i == 1) {
                    minus.setEnabled(false);
                    minus.setAlpha(0.5f);
                }
            }
        });

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                des.setTextColor(Color.parseColor("#62C16F"));
                atur.setTextColor(Color.parseColor("#5a5a5a"));
                line.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                des2.setText(deskripsi);
            }
        });

        atur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                des.setTextColor(Color.parseColor("#5a5a5a"));
                atur.setTextColor(Color.parseColor("#62C16F"));
                line.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                des2.setText(anjuran);
            }
        });

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimdata();
            }
        });

        return v;
    }

    public void kirimdata(){
        //loadingDialog = ProgressDialog.show(getActivity(), "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("id_product", id));
        nameValuePairs.add(new BasicNameValuePair("jumlah", String.valueOf(i)));

        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/keranjang.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
            //String str = EntityUtils.toString(response.getEntity());
            //Log.d("coba ", str);
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        if (exceptionToBeThrown != null) {

        } else {
            //loadingDialog.dismiss();
            Toast.makeText(getActivity(), "Produk Ditambahkan, Silahkan Klik Menu Kasir Untuk Pembayaran", Toast.LENGTH_LONG).show();
        }
        //loadingDialog.dismiss();
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }
}
