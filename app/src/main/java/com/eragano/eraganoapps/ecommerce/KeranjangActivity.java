package com.eragano.eraganoapps.ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Keranjang;
import com.eragano.eraganoapps.adapter.KeranjangAdapter;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeranjangActivity extends android.support.v4.app.Fragment {
    View v;
    public static final String URL = "http://103.236.201.252/android/isi_keranjang.php";

    private ListView mListView;
    private KeranjangAdapter mAdapter;
    List<Keranjang> posts = new ArrayList<>();

    SharedPreferences sp;
    private Exception exceptionToBeThrown;
    //ProgressBar pb;
    public static String web = "";
    TextView kosong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_keranjang, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //pb = (ProgressBar) v.findViewById(R.id.progressBar5);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        String username = sp.getString("User", null).replace(" ", "%20");
        web = URL + "?user=" + username;
        View footer = getActivity().getLayoutInflater().inflate(R.layout.footer_keranjang, null);

        kosong = (TextView) v.findViewById(R.id.keranjangkosong);
        kosong.setVisibility(TextView.INVISIBLE);
        Button lanjut = (Button) footer.findViewById(R.id.button3);
        mListView = (ListView) v.findViewById(R.id.listView3);
        mListView.addFooterView(footer);

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rekapActivity = new RekapActivity();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, rekapActivity).addToBackStack("FRAGMENT");
                fragmentTransaction.commit();
            }
        });
        Button rinci = (Button) v.findViewById(R.id.button_rincian);
        rinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rinciActivity = new RincianOrder();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, rinciActivity).addToBackStack("FRAGMENT");
                fragmentTransaction.commit();
            }
        });

        new SimpleTask().execute(web);

        return v;
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(getActivity(), "Silahkan tunggu", "Data sedang di proses", false, false);
            //pb.setVisibility(ProgressBar.VISIBLE);
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Data sedang di proses");
            dialog.show();
        }

        protected String doInBackground(String... urls) {
            String result = "";
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
                HttpConnectionParams.setSoTimeout(httpParams, 60000);
                client = new DefaultHttpClient(httpParams);
                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }


            } catch (ClientProtocolException e) {
                exceptionToBeThrown = e;
            } catch (IOException e) {
                exceptionToBeThrown = e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            dialog.dismiss();
            //pb.setVisibility(ProgressBar.INVISIBLE);
            if (jsonString.equals("null")) {
                kosong.setVisibility(TextView.VISIBLE);
            } else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
                showData(jsonString);
            }
        }
    }
        public void showData(String jsonString) {
            Gson gson = new Gson();
            //List<Post> posts = new ArrayList<>();
            posts = Arrays.asList(gson.fromJson(jsonString, Keranjang[].class));
            //List<Post> post = posts.;
            mAdapter = new KeranjangAdapter(getActivity(), posts);
            mListView.deferNotifyDataSetChanged();
            mListView.setAdapter(mAdapter);

        }

        public static void hapus(Context context, String id) {
            final MainActivity mainActivity = (MainActivity) context;
            HttpGet httpGet = new HttpGet("http://103.236.201.252/android/hapus_keranjang.php?id_keranjang=" + id);
            HttpClient client = new DefaultHttpClient();

            try {
                HttpResponse response = client.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Produk Telah dihapus dari Keranjang", Toast.LENGTH_SHORT).show();
            //mainActivity.onBackPressed();
            Fragment keranjangActivity = new KeranjangActivity();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containerView, keranjangActivity).addToBackStack("FRAGMENT2");
            fragmentTransaction.commit();
        }
    }

