package com.eragano.eraganoapps.lupapassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.login.LoginActivity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Deztu on 8/3/2016.
 */
public class MasukkanTokenPassword extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back= null;
    private ImageButton bantuan= null;
    private EditText txtToken= null;
    private TextView txtNumber= null;
    private Button lanjutkan= null;
    private Button batalkan= null;
    private int token= 0;
    private String username= null;
    private String phoneNumber= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masukkan_token);

        Intent intent= getIntent();
        token= intent.getIntExtra("token", 0);
        username= intent.getStringExtra("username");
        phoneNumber= intent.getStringExtra("numberPhone");

        back= (ImageButton)findViewById(R.id.imagebackInMasukkanToken);
        bantuan= (ImageButton)findViewById(R.id.imageBantuInMasukkanToken);
        txtToken= (EditText)findViewById(R.id.codeInMasukkanToken);
        txtNumber= (TextView)findViewById(R.id.numberInMasukkanToken);
        lanjutkan= (Button)findViewById(R.id.lanjutkanInMasukkanToken);
        batalkan= (Button)findViewById(R.id.batalkanInMasukkanToken);

        isiNumber();

        back.setOnClickListener(this);
        bantuan.setOnClickListener(this);
        lanjutkan.setOnClickListener(this);
        batalkan.setOnClickListener(this);
    }

    public void isiNumber(){
        String numbers= "+";

        for (int i=0;i<phoneNumber.length();i++){
            if (i == 0 || i == 1){
                numbers = numbers + phoneNumber.charAt(i);
            }
            else if ((i == (phoneNumber.length()-1)) || (i == (phoneNumber.length()-2))){
                numbers = numbers + phoneNumber.charAt(i);
            }
            else{
                numbers = numbers + "*";
            }
        }

        txtNumber.setText(numbers);
    }

    @Override
    public void onClick(View v) {
        if (v == back){
            Intent intent= new Intent(MasukkanTokenPassword.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }
        else if (v == bantuan){

        }
        else if (v == lanjutkan){
            if (!validateToken()){
                return ;
            }

            Intent intent= new Intent(MasukkanTokenPassword.this, ResetPassword.class);
            intent.putExtra("username", username);
            startActivity(intent);

            finish();
        }
        else{
            Intent intent= new Intent(MasukkanTokenPassword.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }
    }

    public boolean validateToken(){
        int code= 0;

        if (TextUtils.isEmpty(txtToken.getText())){
            txtToken.setError("Token tidak boleh kosong");
            txtToken.requestFocus();

            return false;
        }
        if (txtToken.getText().toString().length() < 6){
            txtToken.setError("Token harus enam digit");
            txtToken.requestFocus();

            return false;
        }

        code= Integer.parseInt(txtToken.getText().toString());
        if (code != token){
            txtToken.setError("Token yang Anda masukkan salah");
            txtToken.requestFocus();

            return false;
        }

        return true;
    }
}
