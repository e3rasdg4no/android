package com.eragano.eraganoapps.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.TokenUser;
import com.google.gson.Gson;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class LoginActivity extends Activity implements AsyncResponse, View.OnClickListener{

    private EditText UserName;
    private EditText Password;

    public static final String USER_NAME = "USERNAME";
    public static final String URL = "http://103.236.201.252/android/token_user2.php";
    List<TokenUser> posts = new ArrayList<>();

    EditText txtusername;
    EditText txtpassword;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Exception exceptionToBeThrown;

    String ip;
    String web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getBoolean("Status", false) == true) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Button btnLogin = (Button) findViewById(R.id.buttonLogin);
            TextView txtdaftar = (TextView) findViewById(R.id.daftar);
            txtdaftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(i);
                }
            });

            txtusername = (EditText) findViewById(R.id.usernameLogin);
            txtpassword = (EditText) findViewById(R.id.passwordLogin);
            btnLogin.setOnClickListener(this);
            }
        }

        @Override
        public void processFinish (String result){
            if (result.equals("success")) {
                ambildata();
                editor.putString("User", txtusername.getText().toString());
                editor.putBoolean("Status", true);
                editor.commit();
                Intent inten = new Intent(this, MainActivity.class);
                finish();
                startActivity(inten);
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Kesalahan nama pengguna atau kata kunci, dan Cek Internet anda", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onClick (View v){
            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("txtUsername", txtusername.getText().toString());
            postData.put("txtPassword", txtpassword.getText().toString());
            String triman = txtusername.getText().toString().replace(" ", "%20");
            web = URL+"?user="+triman;
            //new SimpleTask().execute(web);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("http://103.236.201.252/android/login.php");
        }

    public void ambildata(){
        StringRequest stringRequest = new StringRequest(web, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                posts = Arrays.asList(gson.fromJson(response, TokenUser[].class));
                editor.putString("ID_USER", posts.get(0).getId_user());
                editor.putString("NAMA_LENGKAP", posts.get(0).getNama_lengkap());
                editor.putString("SEBAGAI", posts.get(0).getSebagai());
                editor.putString("TEMPAT_LAHIR", posts.get(0).getTempat_lahir());
                editor.putString("TANGGAL_LAHIR", posts.get(0).getTanggal_lahir());
                editor.putString("ALAMAT", posts.get(0).getAlamat());
                editor.putString("DESA_KELURAHAN", posts.get(0).getDesa_kelurahan());
                editor.putString("KECAMATAN", posts.get(0).getKecamatan());
                editor.putString("KABUPATEN", posts.get(0).getKabupaten_kota());
                editor.putString("PROVINSI", posts.get(0).getProvinsi());
                editor.putString("KODE_POS", posts.get(0).getKodepos());
                editor.putString("NO_TELEPON", posts.get(0).getNo_telepon());
                editor.putString("NO_KTP", posts.get(0).getNo_ktp());
                editor.putString("TANGGAL_KTP", posts.get(0).getTanggal_ktp());
                editor.putString("USER_GROUP", posts.get(0).getUsergroup());
                editor.putString("ID_JADWAL", posts.get(0).getId_jadwal());
                editor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Kesalahan terjadi, Periksa internet anda", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;//30 seconds timeout
        int DEFAULT_MAX_RETRIES = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*
    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        protected String doInBackground(String... urls) {
            String result = "";
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

        protected void onPostExecute(String jsonString) {
            //pb.setVisibility(ProgressBar.INVISIBLE);
            if (jsonString.equals("null")) {
                //search.setError("Produk yang anda cari tidak ada");
                Toast.makeText(LoginActivity.this, "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else if (exceptionToBeThrown != null) {
                Toast.makeText(LoginActivity.this, "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
                showData(jsonString);
            }
        }
    }

    private void showData(String jsonString) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts = Arrays.asList(gson.fromJson(jsonString, TokenUser[].class));
        //List<Post> post = posts.;
        editor.putString("NAMA_LENGKAP", posts.get(0).getNama_lengkap());
        editor.putString("SEBAGAI", posts.get(0).getSebagai());
        editor.putString("TEMPAT_LAHIR", posts.get(0).getTempat_lahir());
        editor.putString("TANGGAL_LAHIR", posts.get(0).getTanggal_lahir());
        editor.putString("ALAMAT", posts.get(0).getAlamat());
        editor.putString("DESA_KELURAHAN", posts.get(0).getDesa_kelurahan());
        editor.putString("KECAMATAN", posts.get(0).getKecamatan());
        editor.putString("KABUPATEN", posts.get(0).getKabupaten_kota());
        editor.putString("PROVINSI", posts.get(0).getProvinsi());
        editor.putString("KODE_POS", posts.get(0).getKodepos());
        editor.putString("NO_TELEPON", posts.get(0).getNo_telepon());
        editor.putString("NO_KTP", posts.get(0).getNo_ktp());
        editor.putString("TANGGAL_KTP", posts.get(0).getTanggal_ktp());
        editor.putString("USER_GROUP", posts.get(0).getUsergroup());
        editor.putString("ID_JADWAL", posts.get(0).getId_jadwal());
        editor.commit();
    }*/
}
