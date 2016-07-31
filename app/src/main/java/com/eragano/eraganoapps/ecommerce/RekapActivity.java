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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Keranjang;
import com.eragano.eraganoapps.adapter.KeranjangAdapter;
import com.eragano.eraganoapps.penampung.User;
import com.eragano.eraganoapps.adapter.UserAdapter;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RekapActivity extends android.support.v4.app.Fragment {
    View v;
    public static final String URL = "http://103.236.201.252/android/isi_keranjang.php";
    public static final String URL2 = "http://103.236.201.252/android/token_user.php";
    List<Keranjang> posts = new ArrayList<>();
    List<User> posts2 = new ArrayList<>();
    SharedPreferences sp;
    private Exception exceptionToBeThrown;
    ProgressBar pb;
    public static String web="";
    public static String web2="";
    TextView txtlist,tgl,total,uname,notel,alamat;
    private KeranjangAdapter mAdapter;
    private UserAdapter mAdapter2;
    String tanggal_pengajuan;
    int jumlah;
    String list="";
    String nama,telepon,alm;
    String username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_rekap, container, false);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        tanggal_pengajuan = df.format(c.getTime());

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        username = sp.getString("User", null).replace(" ", "%20");;
        web = URL+"?user="+username;
        web2 = URL2+"?user="+username;

        txtlist = (TextView) v.findViewById(R.id.textView30);
        tgl = (TextView) v.findViewById(R.id.tanggal_pemesanan);
        total = (TextView) v.findViewById(R.id.total_harga);
        tgl.setText(tanggal_pengajuan);
        uname = (TextView) v.findViewById(R.id.txtusername);
        notel = (TextView) v.findViewById(R.id.txtnotelepon);
        alamat = (TextView) v.findViewById(R.id.txtalamat);

        Button btn = (Button) v.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
                kirimdata();
                hapus();
            }
        });
        new SimpleTask().execute(web);
        new SimpleTask2().execute(web2);
        return v;
    }

    private class SimpleTask2 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
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

        protected void onPostExecute(String jsonString2)  {
            if(exceptionToBeThrown != null)
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            else showData2(jsonString2);
        }
    }

    public void showData2(String jsonString2) {
        Gson gson = new Gson();
        posts2 = Arrays.asList(gson.fromJson(jsonString2, User[].class));
        mAdapter2 = new UserAdapter(getActivity(), posts2);
        nama = posts2.get(0).getNama_lengkap().trim();
        uname.setText(nama);
        telepon = posts2.get(0).getNo_telepon().trim();
        notel.setText(telepon);
        alm = posts2.get(0).getAlamat().trim();
        alamat.setText(alm);
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
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
            if(exceptionToBeThrown != null)
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            else showData(jsonString);
        }
    }

    public void showData(String jsonString) {
        Gson gson = new Gson();


        posts = Arrays.asList(gson.fromJson(jsonString, Keranjang[].class));
        mAdapter = new KeranjangAdapter(getActivity(), posts);
        list = posts.get(0).getProduct_name()+" / "+posts.get(0).getJumlah()+" "+posts.get(0).getJenis_satuan();
        jumlah = Integer.parseInt(posts.get(0).getSale_price())*Integer.parseInt(posts.get(0).getJumlah());
        int i=mAdapter.getCount();
        for(int count=1;count<i;count++) {
            list = list+"\n"+posts.get(count).getProduct_name()+" / "+posts.get(count).getJumlah()+" "+posts.get(count).getJenis_satuan();
            jumlah = jumlah+(Integer.parseInt(posts.get(count).getSale_price().trim()))*(Integer.parseInt(posts.get(count).getJumlah()));
        }
        txtlist.setText(list);
        String x = String.valueOf(jumlah);
        if(x.length()==4){
            x = x.substring(0,1)+"."+x.substring(1);
        }
        else if(x.length()==5){
            x = x.substring(0,2)+"."+x.substring(2);
        }
        else if(x.length()==6){
            x = x.substring(0,3)+"."+x.substring(3);
        }
        else if(x.length()==7){
            x = x.substring(0,1)+"."+x.substring(1,4)+"."+x.substring(4);
        }
        else if(x.length()==8){
            x = x.substring(0,2)+"."+x.substring(2,5)+"."+x.substring(5);
        }
        else if(x.length()==9){
            x = x.substring(0,3)+"."+x.substring(3,6)+"."+x.substring(6);
        }
        else if(x.length()==10){
            x = x.substring(0,4)+"."+x.substring(4,7)+"."+x.substring(7);
        }
        total.setText("IDR "+x);
    }

    //SENT EMAIL NOTIFICATION
    private void sendEmail() {
        //Getting content for email
        String u = username.replace("%20"," ");
        String email = "order.eragano@gmail.com";
        String subject = "Pesanan";
        String header = "DATA PEMBELI\n" +
                        "Nama Lengkap Pemesan : "+nama+"\n" +
                        "Username Pemesan : "+u+"\n" +
                        "Nomor Telepon : "+telepon+"\n" +
                        "Alamat Pemesan : "+alm+"\n";
        String message = header+"\n\nLIST PRODUK DIBELI\n"+list+"\n\nTotal Harga : "+jumlah;

        //Creating SendMail object
        SendMail sm = new SendMail(getActivity(), email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }
    public class SendMail extends AsyncTask<Void,Void,Void> {

        //Declaring Variables
        private Context context;
        private Session session;

        //Information to send email
        private String email;
        private String subject;
        private String message;

        //Progressdialog to show while sending email
        private ProgressDialog progressDialog;

        //Class Constructor
        public SendMail(Context context, String email, String subject, String message){
            //Initializing variables
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing progress dialog while sending email
            //progressDialog = ProgressDialog.show(context, "Silahkan Tunggu", "Pesanan sedang diproses", false, false);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Data sedang di proses");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            progressDialog.dismiss();
            //Showing a success message
            //Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            Properties props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Creating a new session
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                        }
                    });

            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(session);

                //Setting sender address
                mm.setFrom(new InternetAddress(Config.EMAIL));
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Adding subject
                mm.setSubject(subject);
                //Adding message
                mm.setText(message);

                //Sending email
                Transport.send(mm);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //KIRIM DATA
    public void kirimdata(){
        //loadingDialog = ProgressDialog.show(RegisterActivity.this, "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("list_produk", list));
        nameValuePairs.add(new BasicNameValuePair("tanggal_pemesanan", tanggal_pengajuan));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("total_bayar", String.valueOf(jumlah)));

        try {
            HttpPost request = new HttpPost("http://103.236.201.252/android/history_pembelian.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
            String str = EntityUtils.toString(response.getEntity());
            //Log.d("coba ", str);
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        if (exceptionToBeThrown != null) {

        } else {
          //  finish();
        }
        //loadingDialog.dismiss();
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }

    //HAPUS KERANJANG BELANJA
    public void hapus() {
        HttpGet httpGet = new HttpGet("http://103.236.201.252/android/full_hapus_keranjang.php?username="+username);
        HttpClient client = new DefaultHttpClient();

        try {
            HttpResponse response = client.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getActivity(),"Produk Telah dihapus dari Keranjang",Toast.LENGTH_SHORT).show();
        //mainActivity.onBackPressed();
        Fragment endActivity = new EndActivity();
        Bundle bundle = new Bundle();
        bundle.putString("NAMA", nama);
        bundle.putString("JUMLAH", String.valueOf(jumlah));
        endActivity.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, endActivity).addToBackStack("FRAGMENT");
        fragmentTransaction.commit();
    }

}
