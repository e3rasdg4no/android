package com.eragano.eraganoapps.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Ecommerce;
import com.eragano.eraganoapps.adapter.EcommerceAdapter;
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

public class KebutuhanFragment extends android.support.v4.app.Fragment {
    ProgressBar loading;
    private String array_spinner[];
    Spinner spin;
    public static final String URL = "http://103.236.201.252/android/ecommerce.php";
    public static final String URL2 = "http://103.236.201.252/android/search_ecommerce.php";

    private ListView mListView;
    private EcommerceAdapter mAdapter;
    List<Ecommerce> posts = new ArrayList<>();
    View v;
    Exception exceptionToBeThrown;
    FragmentTransaction fragmentTransaction;
    String web, web2, triman, websearch;
    ImageView header, icon;
    TextView txtheader;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ecommerce, container, false);

        array_spinner = new String[5];
        array_spinner[0] = "Benih";
        array_spinner[1] = "Pupuk";
        array_spinner[2] = "Pestisida";
        array_spinner[3] = "Alat dan Mesin";
        array_spinner[4] = "Lain - lain";

        spin = (Spinner) v.findViewById(R.id.spinner4);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
        spin.setAdapter(adapter);

        web = URL + "?kategori=1";
        web2 = URL2 + "?kategori=1";
        //header = (ImageView) v.findViewById(R.id.img_header);
        //icon = (ImageView) v.findViewById(R.id.icon_header);
        //txtheader = (TextView) v.findViewById(R.id.txt_header);
        //pb = (ProgressBar) v.findViewById(R.id.progressBar4);
        ImageView cari = (ImageView) v.findViewById(R.id.imgsearch);
        final EditText txtcari = (EditText) v.findViewById(R.id.txt_search);
        final SimpleTask st = new SimpleTask();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //Toast.makeText(getActivity(),"Tidak dapat Kembali, pilih menu yang tersedia", Toast.LENGTH_SHORT).show();
                        st.cancel(true);
                    }
                }
                return false;
            }
        });

                 cari.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         triman = txtcari.getText().toString().replace(" ", "%20");
                         websearch = web2 + "&search=" + triman;
                         new SimpleTask2().execute(websearch);
                         try {
                             InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                             imm.hideSoftInputFromWindow(txtcari.getWindowToken(), 0);
                         } catch (Exception e) {
                             // TODO: handle exception
                         }
                     }
                 });

                 txtcari.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                     @Override
                     public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                         if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                             triman = txtcari.getText().toString().replace(" ", "%20");
                             websearch = web2 + "&search=" + triman;
                             new SimpleTask2().execute(websearch);
                             try {
                                 InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                 imm.hideSoftInputFromWindow(txtcari.getWindowToken(), 0);
                             } catch (Exception e) {
                                 // TODO: handle exception
                             }
                             return true;
                         }
                         return false;
                     }
                 });


                 View headerview = getActivity().getLayoutInflater().inflate(R.layout.header_listview, null);
                header = (ImageView) headerview.findViewById(R.id.img_header);
                icon = (ImageView) headerview.findViewById(R.id.icon_header);
                txtheader = (TextView) headerview.findViewById(R.id.txt_header);

                 mListView = (ListView) v.findViewById(R.id.listView2);
                 mListView.addHeaderView(headerview);
                 //new SimpleTask().execute(web);
                 spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         if (spin.getSelectedItem().equals("Benih")) {
                             web = URL + "?kategori=1";
                             web2 = URL2 + "?kategori=1";
                             header.setImageResource(R.drawable.header_benih);
                             icon.setImageResource(R.drawable.jagung);
                             txtheader.setText("BENIH");
                             new SimpleTask().execute(web);
                         } else if (spin.getSelectedItem().equals("Pupuk")) {
                             web = URL + "?kategori=2";
                             web2 = URL2 + "?kategori=2";
                             header.setImageResource(R.drawable.header_pupuk);
                             icon.setImageResource(R.drawable.pupuk);
                             txtheader.setText("PUPUK");
                             new SimpleTask().execute(web);
                         } else if (spin.getSelectedItem().equals("Pestisida")) {
                             web = URL + "?kategori=3";
                             web2 = URL2 + "?kategori=3";
                             header.setImageResource(R.drawable.header_pestisida);
                             icon.setImageResource(R.drawable.pestisida);
                             txtheader.setText("PESTISIDA");
                             new SimpleTask().execute(web);
                         } else if (spin.getSelectedItem().equals("Alat dan Mesin")) {
                             web = URL + "?kategori=4";
                             web2 = URL2 + "?kategori=4";
                             header.setImageResource(R.drawable.header_alatmesin);
                             icon.setImageResource(R.drawable.mesin);
                             txtheader.setText("ALAT DAN MESIN");
                             new SimpleTask().execute(web);
                         } else {
                             web = URL + "?kategori=5";
                             web2 = URL2 + "?kategori=5";
                             header.setImageResource(R.drawable.header_lainlain);
                             icon.setImageResource(R.drawable.lainlain);
                             txtheader.setText("LAIN - LAIN");
                             new SimpleTask().execute(web);
                         }
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parent) {

                     }
                 });

            /*WebView wv = (WebView) v.findViewById(R.id.webView);
            loading = (ProgressBar) v.findViewById(R.id.progressBar3);
            loading.setVisibility(ProgressBar.VISIBLE);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new SwAWebClient());
            wv.loadUrl("http://103.236.201.252/ecommerce_fix");
            wv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        WebView webView = (WebView) v;

                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack()) {
                                    webView.goBack();
                                    return true;
                                }
                                break;
                        }
                    }

                    return false;
                }
            });
            return v;
        }

    private class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            loading.setVisibility(ProgressBar.VISIBLE);
            return false;
        }

        @Override public void onPageFinished(WebView view, String url) {
            loading.setVisibility(ProgressBar.INVISIBLE);
            super.onPageFinished(view, url);

        }*/
                 return v;
             }

             private class SimpleTask extends AsyncTask<String, Void, String> {
                ProgressDialog dialog;
                 @Override
                 protected void onPreExecute() {
                     dialog = new ProgressDialog(getActivity());
                     dialog.setMessage("Data sedang di proses");
                     dialog.setCancelable(false);
                     dialog.show();
                     //pb.setVisibility(ProgressBar.VISIBLE);
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
                         //search.setError("Produk yang anda cari tidak ada");
                         Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
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
                 posts = Arrays.asList(gson.fromJson(jsonString, Ecommerce[].class));
                 //List<Post> post = posts.;
                 mAdapter = new EcommerceAdapter(getActivity(), posts);
                 mListView.deferNotifyDataSetChanged();
                 mListView.setAdapter(mAdapter);
             }

             private class SimpleTask2 extends AsyncTask<String, Void, String> {
                 String result = "";
                 ProgressDialog dialog;
                 @Override
                 protected void onPreExecute() {
                     dialog = new ProgressDialog(getActivity());
                     dialog.setMessage("Data sedang di proses");
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
                     //pb.setVisibility(ProgressBar.INVISIBLE);
                     dialog.dismiss();
                     if (jsonString2.equals("null")) {
                         //search.setError("Produk yang anda cari tidak ada");
                         Toast.makeText(getActivity(), "Maaf, Produk yang anda Cari tidak ada", Toast.LENGTH_SHORT).show();
                     } else if (exceptionToBeThrown != null) {
                         Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
                     } else {
                         showData2(jsonString2);
                     }
                 }
             }

             private void showData2(String jsonString2) {
                 Gson gson = new Gson();
                 //List<Post> posts = new ArrayList<>();
                 posts = Arrays.asList(gson.fromJson(jsonString2, Ecommerce[].class));
                 //List<Post> post = posts.;
                 mAdapter = new EcommerceAdapter(getActivity(), posts);
                 mListView.setAdapter(mAdapter);
                 mListView.deferNotifyDataSetChanged();
             }
         }
