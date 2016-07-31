package com.eragano.eraganoapps.pinjaman.rincian_pinjaman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.RincianPinjamanAdapter;
import com.eragano.eraganoapps.adapter.RincianPinjamanJson;
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

public class RincianPinjaman extends android.support.v4.app.Fragment {

    public static final String URL = "http://103.236.201.252/android/rincian_pinjaman.php";

    private ListView mListView;
    private RincianPinjamanAdapter mAdapter;
    List<RincianPinjamanJson> posts = new ArrayList<>();
    Exception exceptionToBeThrown;
    SharedPreferences sp;
    TextView txtkosong;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_rincian_pinjaman, container, false);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        mListView = (ListView) v.findViewById(R.id.listRincianpinjaman);
        final String web = URL+"?user="+sp.getString("User",null).replace(" ", "%20");;

        txtkosong = (TextView) v.findViewById(R.id.txtkosong);
        txtkosong.setVisibility(TextView.INVISIBLE);

        new SimpleTask().execute(web);
        return v;
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {
        String result = "";
        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls)   {

            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

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
            if (jsonString.equals("null")) {
                txtkosong.setVisibility(TextView.VISIBLE);
            }
            else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            }
            else showData(jsonString);

        }
    }

    private void showData(String jsonString) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts = Arrays.asList(gson.fromJson(jsonString, RincianPinjamanJson[].class));
        //List<Post> post = posts.;
        mAdapter = new RincianPinjamanAdapter(getActivity(), posts);
        mListView.setAdapter(mAdapter);
    }
}
