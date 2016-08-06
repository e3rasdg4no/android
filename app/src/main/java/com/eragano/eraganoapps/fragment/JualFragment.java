package com.eragano.eraganoapps.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Produk;
import com.eragano.eraganoapps.adapter.ProdukAdapter;
import com.eragano.eraganoapps.jual.Jual2Activity;
import com.eragano.eraganoapps.jual.camera.JualCameraActivity;
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

public class JualFragment extends android.support.v4.app.Fragment {
    public static String URL = "http://103.236.201.252/android/produk.php";
    public static String URL2 = "http://103.236.201.252/android/search_produk.php";
    private ListView mListView;
    private ProdukAdapter mAdapter;
    List<Produk> posts = new ArrayList<>();
    View v;
    Context context;
    Exception exceptionToBeThrown;
    TextView txtkosong,tampil, muat;
    EditText search;
    String websearch,triman;
    ImageView btnpost,imgtambah;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_jual, container, false);

        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        String x = sp.getString("User", null).replace(" ", "%20");
        final String web = URL + "?user=" + x;
        final String web2 = URL2 + "?user=" + x;

        mListView = (ListView) v.findViewById(R.id.listJual);
        txtkosong = (TextView) v.findViewById(R.id.kosong);
        search = (EditText) v.findViewById(R.id.txtSearch);
        tampil = (TextView) v.findViewById(R.id.txttampilkan);
        muat = (TextView) v.findViewById(R.id.muatulang);

        txtkosong.setVisibility(TextView.INVISIBLE);
        tampil.setVisibility(TextView.INVISIBLE);

        imgtambah = (ImageView) v.findViewById(R.id.imgtambah);

        btnpost = (ImageView) v.findViewById(R.id.posting);
        btnpost.bringToFront();
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), JualCameraActivity.class);
                getActivity().startActivity(i);
            }
        });
        new SimpleTask().execute(web);
        ImageView imgsearch = (ImageView) v.findViewById(R.id.imageView7);
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triman = search.getText().toString().replace(" ", "%20");
                websearch = web2 + "&nama=" + triman;
                new SimpleTask2().execute(websearch);
                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    triman = search.getText().toString().replace(" ","%20");
                    websearch = web2 + "&nama=" + triman;
                    new SimpleTask2().execute(websearch);
                    try {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    return true;
                }
                return false;
            }
        });

        muat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleTask().execute(web);
            }
        });
        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muat.setVisibility(TextView.VISIBLE);
                tampil.setVisibility(TextView.INVISIBLE);
                new SimpleTask().execute(web);
            }
        });
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
        posts = Arrays.asList(gson.fromJson(jsonString, Produk[].class));
        //List<Post> post = posts.;
        mAdapter = new ProdukAdapter(getActivity(), posts);
        mListView.setAdapter(mAdapter);
        mListView.deferNotifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nama = posts.get(position).getNama();
                String tanggal = posts.get(position).getTanggal_panen();
                String satuan = posts.get(position).getSatuan();
                String jumlah = posts.get(position).getStok();
                String harga = posts.get(position).getHarga();
                String pict = posts.get(position).getImage();
                String id_jual = posts.get(position).getId_jual();

                Intent i = new Intent(getActivity(), Jual2Activity.class);
                i.putExtra("PICTURE", pict);
                i.putExtra("NAMA", nama);
                i.putExtra("TANGGAL", tanggal);
                i.putExtra("SATUAN", satuan);
                i.putExtra("JUMLAH", jumlah);
                i.putExtra("HARGA", harga);
                i.putExtra("ID", id_jual);
                startActivity(i);

            }
        });
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

    private class SimpleTask2 extends AsyncTask<String, Void, String> {
        String result = "";
        ProgressDialog dialog;
        //ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar2);
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

        protected void onPostExecute(String jsonString2) {
            dialog.dismiss();
            //pb.setVisibility(ProgressBar.INVISIBLE);
            if (jsonString2.equals("null")) {
                //search.setError("Produk yang anda cari tidak ada");
                Toast.makeText(getActivity(),"Maaf, Produk yang anda Cari tidak ada",Toast.LENGTH_SHORT).show();
            } else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
                muat.setVisibility(TextView.INVISIBLE);
                tampil.setVisibility(TextView.VISIBLE);
                showData2(jsonString2);
            }
        }
    }

    private void showData2(String jsonString2) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts = Arrays.asList(gson.fromJson(jsonString2, Produk[].class));
        //List<Post> post = posts.;
        mAdapter = new ProdukAdapter(getActivity(), posts);
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