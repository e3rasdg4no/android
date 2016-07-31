package com.eragano.eraganoapps.ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.RincianOrderAdapter;
import com.eragano.eraganoapps.penampung.RincianOrderVariable;
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

public class RincianOrder extends android.support.v4.app.Fragment {
    public static final String URL = "http://103.236.201.252/android/rincian_order.php";

    private ListView mListView;
    private RincianOrderAdapter mAdapter;
    List<RincianOrderVariable> posts = new ArrayList<>();
    View v;
    Exception exceptionToBeThrown;
    SharedPreferences sp;
    TextView txtKosong;
    //ProgressBar pb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_rincian_order, container, false);

        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        final String web = URL + "?user=" + sp.getString("User", null).replace(" ", "%20");;
        //pb = (ProgressBar) v.findViewById(R.id.progressBar6);
        mListView = (ListView) v.findViewById(R.id.listView4);
        txtKosong = (TextView) v.findViewById(R.id.txtkosongrincian);
        txtKosong.setVisibility(TextView.INVISIBLE);
        /*v.setFocusableInTouchMode(true);
        v.requestFocus();
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //Toast.makeText(getActivity(),"Tidak dapat Kembali, pilih menu yang tersedia", Toast.LENGTH_SHORT).show();
                        Fragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.containerView, homeFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return true;
                    }
                }
                return false;
            }
        });*/
        new SimpleTask().execute(web);
        return v;
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "Silahkan tunggu", "Data sedang di proses", false, false);
            //pb.setVisibility(ProgressBar.VISIBLE);
        }

        protected String doInBackground(String... urls)   {
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

        protected void onPostExecute(String jsonString)  {
            dialog.dismiss();
            //pb.setVisibility(ProgressBar.INVISIBLE);
            if (jsonString.equals("null")) {
                txtKosong.setVisibility(TextView.VISIBLE);
            } else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
                showData(jsonString);
            }
        }
    }

    private void showData(String jsonString) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts = Arrays.asList(gson.fromJson(jsonString, RincianOrderVariable[].class));
        //List<Post> post = posts.;
        mAdapter = new RincianOrderAdapter(getActivity(), posts);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailRinci as = new DetailRinci();
                Bundle bundle = new Bundle();
                bundle.putString("PRODUK", posts.get(position).getList_produk());
                bundle.putString("TANGGAL", posts.get(position).getTanggal_pemesanan());
                bundle.putString("NOMOR", posts.get(position).getId_penjualan());
                bundle.putString("TOTAL", posts.get(position).getTotal_bayar());
                as.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.containerView, as).addToBackStack("FRAGMENT").commit();
            }
        });
    }
}
