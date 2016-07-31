package com.eragano.eraganoapps.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Performa;
import com.eragano.eraganoapps.adapter.PerformaAdapter;
import com.eragano.eraganoapps.performa.InputKinerjaActivity;
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

public class KinerjaFragment2 extends android.support.v4.app.Fragment {

    public static String URL = "http://103.236.201.252/android/performa/json_performa.php";

    private ListView mListView;
    private PerformaAdapter mAdapter;
    List<Performa> posts = new ArrayList<>();
    View v;
    Context context;
    Exception exceptionToBeThrown;

    TextView txtkosong;

    ImageView btnpost,imgtambah;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2_kinerja, container, false);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        String x = sp.getString("User", null).replace(" ", "%20");
        final String web = URL + "?user=" + x;

        mListView = (ListView) v.findViewById(R.id.list_kinerja);
        txtkosong = (TextView) v.findViewById(R.id.kosong);
        btnpost = (ImageView) v.findViewById(R.id.posting);
        imgtambah = (ImageView) v.findViewById(R.id.imgtambah);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InputKinerjaActivity.class);
                startActivity(i);
            }
        });
        imgtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InputKinerjaActivity.class);
                startActivity(i);
            }
        });

        txtkosong.setVisibility(TextView.INVISIBLE);


        new SimpleTask().execute(web);


        return v;
    }
    private class SimpleTask extends AsyncTask<String, Void, String> {
        String result = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Data sedang di proses");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... urls) {

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
            //pb.setVisibility(ProgressBar.INVISIBLE);

            dialog.dismiss();
            if (jsonString.equals("null")) {
                txtkosong.setVisibility(TextView.VISIBLE);
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
        posts = Arrays.asList(gson.fromJson(jsonString, Performa[].class));
        //List<Post> post = posts.;
        mAdapter = new PerformaAdapter(getActivity(), posts);
        mListView.setAdapter(mAdapter);
        mListView.deferNotifyDataSetChanged();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (mLastFirstVisibleItem < firstVisibleItem) {
                    imgtambah.setAlpha(70);
                    btnpost.setAlpha(70);
                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    imgtambah.setAlpha(1000);
                    btnpost.setAlpha(1000);
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }
}
