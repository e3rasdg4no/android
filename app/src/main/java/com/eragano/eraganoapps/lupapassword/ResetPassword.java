package com.eragano.eraganoapps.lupapassword;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.login.LoginActivity;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Deztu on 8/3/2016.
 */
public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back= null;
    private ImageButton bantuan= null;
    private EditText kataSandiBaru= null;
    private EditText ulangiKataSandi= null;
    private Button lanjutkan= null;
    private Button batalkan= null;
    private String username= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent= getIntent();
        username= intent.getStringExtra("username");

        back= (ImageButton)findViewById(R.id.imagebackInResetKataSandi);
        bantuan= (ImageButton)findViewById(R.id.imageBantuInResetKataSandi);
        kataSandiBaru= (EditText)findViewById(R.id.kataSandiBaru);
        ulangiKataSandi= (EditText)findViewById(R.id.ulangiKataSandiBaru);
        lanjutkan= (Button)findViewById(R.id.lanjutkanInResetKataSandi);
        batalkan= (Button)findViewById(R.id.batalkanInResetKataSandi);

        back.setOnClickListener(this);
        bantuan.setOnClickListener(this);
        lanjutkan.setOnClickListener(this);
        batalkan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back){
            onBackPressed();
        }
        else if (v == bantuan){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711242493"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            this.startActivity(intent);
        }
        else if (v == lanjutkan){
            if (!validateSandiBaru()){
                return ;
            }
            if (!validateUlangiSandi()){
                return ;
            }

            String url= "http://103.236.201.252/android/reset_password.php";

            final ProgressDialog p= ProgressDialog.show(ResetPassword.this, "", "Harap tunggu...");
            StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    p.dismiss();

                    Toast.makeText(ResetPassword.this, "Kata kunci berhasil dirubah", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(ResetPassword.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    p.dismiss();

                    Toast.makeText(ResetPassword.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new Hashtable<>();

                    params.put("username", username);
                    params.put("password", kataSandiBaru.getText().toString());

                    return params;
                }
            };

            int socketTimeout = 30000;
            int DEFAULT_MAX_RETRIES = 0;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        else{
            Intent intent= new Intent(ResetPassword.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }
    }

    public boolean validateSandiBaru(){
        if (TextUtils.isEmpty(kataSandiBaru.getText())){
            kataSandiBaru.setError("Kata kunci tidak boleh kosong");
            kataSandiBaru.requestFocus();

            return false;
        }

        return true;
    }

    public boolean validateUlangiSandi(){
        if (TextUtils.isEmpty(ulangiKataSandi.getText())){
            ulangiKataSandi.setError("Ulangi kata kunci tidak boleh kosong");
            ulangiKataSandi.requestFocus();

            return false;
        }
        if (!ulangiKataSandi.getText().toString().equals(kataSandiBaru.getText().toString())){
            ulangiKataSandi.setError("Kata kunci yang dimasukkan harus sama");
            ulangiKataSandi.requestFocus();

            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
