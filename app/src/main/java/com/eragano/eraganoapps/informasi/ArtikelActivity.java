package com.eragano.eraganoapps.informasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.ArtikelAdapter;
import com.eragano.eraganoapps.penampung.NativeArtikel;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtikelActivity extends android.support.v4.app.Fragment {

    public static final String URL = "http://103.236.201.252/android/native_artikel.php";

    static ProgressDialog dialog;
    private ListView mListView;
    private ArtikelAdapter mAdapter;
    List<NativeArtikel> posts = new ArrayList<>();
    View v;
    Exception exceptionToBeThrown;
    ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_artikel, container, false);

        mListView = (ListView) v.findViewById(R.id.listArtikel);

        ambildata();
        return v;
    }

    public void ambildata(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Data sedang di proses");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest strRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                //List<Post> posts = new ArrayList<>();
                posts = Arrays.asList(gson.fromJson(s, NativeArtikel[].class));
                //List<Post> post = posts.;
                mAdapter = new ArtikelAdapter(getActivity(), posts);
                mListView.setAdapter(mAdapter);
                dialog.dismiss();
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),
                        //"Click ListItem Number " + position, Toast.LENGTH_LONG)
                        //.show();
                        dialog.show();
                        String value = posts.get(position).getTitle();
                        String value2 = posts.get(position).getContent();
                        String value3 = posts.get(position).getPicture();
                        String value4 = posts.get(position).getTime();
                        String value5 = posts.get(position).getDate();
                        String value6 = posts.get(position).getId_category();

                        Intent myIntent = new Intent(getActivity(), Artikel2Activity.class);
                        myIntent.putExtra("JUDUL", value);
                        myIntent.putExtra("ISI", value2);
                        myIntent.putExtra("GAMBAR", value3);
                        myIntent.putExtra("WAKTU", value4);
                        myIntent.putExtra("DATE", value5);
                        myIntent.putExtra("ID_KATEGORI", value6);
                        startActivity(myIntent);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Please Check Your Connection", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;//30 seconds timeout
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strRequest);
    }

    public static void dismisdialog(){
        dialog.dismiss();
    }
}
