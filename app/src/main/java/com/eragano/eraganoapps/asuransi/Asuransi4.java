package com.eragano.eraganoapps.asuransi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.eragano.eraganoapps.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class Asuransi4  extends android.support.v4.app.Fragment {

    //FROM FRAGMENT 1
    String provinsi, kabupaten, kecamatan, desa, kodepos;
    //FROM FRAGMENT 2
    String nama_pemilik, nama_kelompok, alamat_kelompok, no_anggota, no_ktp, alamat, nama_penggarap, jumlah_lahan;
    //FROM FRAGMENT 3
    String dusun, jumlah_petak, batas_utara, batas_selatan, batas_timur, batas_barat, luas_lahan;

    String luas_petak1, luas_petak2, luas_petak3, luas_petak4, luas_petak5, luas_petak6, luas_petak7, luas_petak8, keterangan, ttd, tanggal_pendaftaran;
    EditText petak1, petak2, petak3, petak4, petak5, petak6, petak7, petak8, ket, tanda;

    private Exception exceptionToBeThrown;
    private Dialog loadingDialog;
    String kondisi_isi;
    String ip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_asuransi4, container, false);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        ip = getActivity().getResources().getString(R.string.ip);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        petak1 = (EditText) v.findViewById(R.id.luas_petak1);
        petak2 = (EditText) v.findViewById(R.id.luas_petak2);
        petak3 = (EditText) v.findViewById(R.id.luas_petak3);
        petak4 = (EditText) v.findViewById(R.id.luas_petak4);
        petak5 = (EditText) v.findViewById(R.id.luas_petak5);
        petak6 = (EditText) v.findViewById(R.id.luas_petak6);
        petak7 = (EditText) v.findViewById(R.id.luas_petak7);
        petak8 = (EditText) v.findViewById(R.id.luas_petak8);
        ket = (EditText) v.findViewById(R.id.keterangan);
        tanda = (EditText) v.findViewById(R.id.tanda_tangan);

        provinsi = getArguments().getString("PROVINSI");
        kabupaten = getArguments().getString("KABUPATEN");
        kecamatan = getArguments().getString("KECAMATAN");
        desa = getArguments().getString("DESA");
        kodepos = getArguments().getString("KODEPOS");
        nama_pemilik = getArguments().getString("NAMA_PEMILIK");
        nama_kelompok = getArguments().getString("NAMA_KELOMPOK");
        alamat_kelompok = getArguments().getString("ALAMAT_KELOMPOK");
        no_anggota = getArguments().getString("NO_ANGGOTA");
        no_ktp = getArguments().getString("NO_KTP");
        alamat = getArguments().getString("ALAMAT");
        nama_penggarap = getArguments().getString("NAMA_PENGGARAP");
        jumlah_lahan = getArguments().getString("JUMLAH_LAHAN");
        dusun = getArguments().getString("DUSUN");
        jumlah_petak = getArguments().getString("JUMLAH_PETAK");
        batas_utara = getArguments().getString("BATAS_UTARA");
        batas_selatan = getArguments().getString("BATAS_SELATAN");
        batas_timur = getArguments().getString("BATAS_TIMUR");
        batas_barat = getArguments().getString("BATAS_BARAT");
        luas_lahan = getArguments().getString("LUAS_LAHAN");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        tanggal_pendaftaran = df.format(c.getTime());

        final Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setEnabled(false);
        btn.setAlpha(0.5f);

        CheckBox mCheckBox = (CheckBox) v.findViewById(R.id.checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btn.setEnabled(true);
                    btn.setAlpha(1f);

                } else {
                    btn.setEnabled(false);
                    btn.setAlpha(0.5f);
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if (cekisi().toString().equals("true")) {
                    luas_petak1 = petak1.getText().toString();
                    luas_petak2 = petak2.getText().toString();
                    luas_petak3 = petak3.getText().toString();
                    luas_petak4 = petak4.getText().toString();
                    luas_petak5 = petak5.getText().toString();
                    luas_petak6 = petak6.getText().toString();
                    luas_petak7 = petak7.getText().toString();
                    luas_petak8 = petak8.getText().toString();
                    keterangan = ket.getText().toString();
                    ttd = tanda.getText().toString();

                    kirimdata();
                    sendEmail();
                    if (exceptionToBeThrown != null) {

                    } else {
                        Fragment langkah5Fragment = new Asuransi5();
                        fragmentTransaction.replace(R.id.containerView, langkah5Fragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        loadingDialog.dismiss();
                    }
                }
            }
        });
        return v;
    }

    public void kirimdata() {
        loadingDialog = ProgressDialog.show(getActivity(), "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(31);

        nameValuePairs.add(new BasicNameValuePair("provinsi", provinsi));
        nameValuePairs.add(new BasicNameValuePair("kabupaten", kabupaten));
        nameValuePairs.add(new BasicNameValuePair("kecamatan", kecamatan));
        nameValuePairs.add(new BasicNameValuePair("desa", desa));
        nameValuePairs.add(new BasicNameValuePair("kode_pos", kodepos));
        nameValuePairs.add(new BasicNameValuePair("nama_pemilik", nama_pemilik));
        nameValuePairs.add(new BasicNameValuePair("nama_kelompok", nama_kelompok));
        nameValuePairs.add(new BasicNameValuePair("alamat_kelompok", alamat_kelompok));
        nameValuePairs.add(new BasicNameValuePair("no_anggota", no_anggota));
        nameValuePairs.add(new BasicNameValuePair("no_ktp", no_ktp));
        nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
        nameValuePairs.add(new BasicNameValuePair("nama_penggarap", nama_penggarap));
        nameValuePairs.add(new BasicNameValuePair("jumlah_lahan", jumlah_lahan));
        nameValuePairs.add(new BasicNameValuePair("dusun", dusun));
        nameValuePairs.add(new BasicNameValuePair("jumlah_petak", jumlah_petak));
        nameValuePairs.add(new BasicNameValuePair("batas_utara", batas_utara));
        nameValuePairs.add(new BasicNameValuePair("batas_selatan", batas_selatan));
        nameValuePairs.add(new BasicNameValuePair("batas_timur", batas_timur));
        nameValuePairs.add(new BasicNameValuePair("batas_barat", batas_barat));
        nameValuePairs.add(new BasicNameValuePair("luas_lahan", luas_lahan));
        nameValuePairs.add(new BasicNameValuePair("luas_petak1", luas_petak1));
        nameValuePairs.add(new BasicNameValuePair("luas_petak2", luas_petak2));
        nameValuePairs.add(new BasicNameValuePair("luas_petak3", luas_petak3));
        nameValuePairs.add(new BasicNameValuePair("luas_petak4", luas_petak4));
        nameValuePairs.add(new BasicNameValuePair("luas_petak5", luas_petak5));
        nameValuePairs.add(new BasicNameValuePair("luas_petak6", luas_petak6));
        nameValuePairs.add(new BasicNameValuePair("luas_petak7", luas_petak7));
        nameValuePairs.add(new BasicNameValuePair("luas_petak8", luas_petak8));
        nameValuePairs.add(new BasicNameValuePair("keterangan", keterangan));
        nameValuePairs.add(new BasicNameValuePair("tanda_tangan", ttd));
        nameValuePairs.add(new BasicNameValuePair("tanggal_pendaftaran", tanggal_pendaftaran));

        try {
            HttpPost request = new HttpPost("http://" + ip + "/android/pendaftaran_asuransi.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown = e;
        }
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }

    public String cekisi() {
        kondisi_isi = "false";
        if (petak1.getText().toString().equals("")) {
            petak1.setError("Kolom ini harus diisi");
            petak1.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        } else if (petak2.getText().toString().equals("")) {
            petak2.setError("Kolom ini harus diisi");
            petak2.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak3.getText().toString().equals("")) {
            petak3.setError("Kolom ini harus diisi");
            petak3.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak4.getText().toString().equals("")) {
            petak4.setError("Kolom ini harus diisi");
            petak4.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak5.getText().toString().equals("")) {
            petak5.setError("Kolom ini harus diisi");
            petak5.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak6.getText().toString().equals("")) {
            petak6.setError("Kolom ini harus diisi");
            petak6.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak7.getText().toString().equals("")) {
            petak7.setError("Kolom ini harus diisi");
            petak7.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (petak8.getText().toString().equals("")) {
            petak8.setError("Kolom ini harus diisi");
            petak8.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (ket.getText().toString().equals("")) {
            ket.setError("Kolom ini harus diisi");
            ket.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if (tanda.getText().toString().equals("")) {
            tanda.setError("Kolom ini harus diisi");
            tanda.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else{
            kondisi_isi="true";
        }
        return kondisi_isi;
    }

    //SENT EMAIL NOTIFICATION
    private void sendEmail() {
        //Getting content for email
        String email = "form.eragano@gmail.com";
        String subject = "PENDAFTARAN ASURANSI";
        String header = "DATA PENDAFTARAN ASURANSI\n" +
                "Provinsi : "+provinsi+"\n" +
                "Kabupaten : "+kabupaten+"\n" +
                "Kecamatan : "+kecamatan+"\n" +
                "Desa : "+desa+"\n" +
                "Kode Pos : "+kodepos+"\n" +
                "Nama Pemilik : "+nama_pemilik+"\n" +
                "Nama Kelompok : "+nama_kelompok+"\n"+
                "Alamat Kelompok : "+alamat_kelompok+"\n"+
                "Nomor Anggota : "+no_anggota+"\n" +
                "Nomor KTP : "+no_ktp+"\n" +
                "Alamat : "+alamat+"\n" +
                "Nama Penggarap : "+nama_penggarap+"\n" +
                "Jumlah Lahan : "+jumlah_lahan+"\n" +
                "Dusun : "+dusun+"\n" +
                "Jumlah Petak : "+jumlah_petak+"\n"+
                "Batas Utara : "+batas_utara+"\n" +
                "Batas Selatan : "+batas_selatan+"\n" +
                "Batas Timur : "+batas_timur+"\n" +
                "Batas Barat : "+batas_barat+"\n" +
                "Luas Lahan : "+luas_lahan+"\n" +
                "Luas Petak 1 : "+luas_petak1+"\n" +
                "Luas Petak 2 : "+luas_petak2+"\n"+
                "Luas Petak 3 : "+luas_petak3+"\n"+
                "Luas Petak 4 : "+luas_petak4+"\n" +
                "Luas Petak 5 : "+luas_petak5+"\n" +
                "Luas Petak 6 : "+luas_petak6+"\n" +
                "Luas Petak 7 : "+luas_petak7+"\n" +
                "Luas Petak 8 : "+luas_petak8+"\n" +
                "Keterangan : "+keterangan+"\n" +
                "Tanda Tangan : "+ttd+"\n"+
                "Tanggal Pendaftaran : "+tanggal_pendaftaran+"\n";
        //String message = header;

        //Creating SendMail object
        SendMail sm = new SendMail(getActivity(), email, subject, header);
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
            progressDialog = ProgressDialog.show(context, "Pendaftaran Sedang Di Proses", "Mohon Tunggu", false, false);
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
                            return new PasswordAuthentication(com.eragano.eraganoapps.ecommerce.Config.EMAIL, com.eragano.eraganoapps.ecommerce.Config.PASSWORD);
                        }
                    });

            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(session);

                //Setting sender address
                mm.setFrom(new InternetAddress(com.eragano.eraganoapps.ecommerce.Config.EMAIL));
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
}
