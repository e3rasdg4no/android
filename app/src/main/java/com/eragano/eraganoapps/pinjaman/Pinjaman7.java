package com.eragano.eraganoapps.pinjaman;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.eragano.eraganoapps.asuransi.Asuransi5;

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

public class Pinjaman7 extends android.support.v4.app.Fragment {
    //FROM LANGKAH 1 PINJAMAN
    String namalengkap, tempatlahir, tanggallahir, namagadis, pendidikan, alamat, kecamatan, kabupaten, provinsi, kodepos;
    //FROM LANGKAH 2 PINJAMAN
    String nomortelepon, noktp, tanggalktp, nonpwp, jumlahtanggungan, nokk, statustempat, lamatinggal, statuskawin;
    //FROM LANGKAH 3 Pinjaman
    String namasuamiistri, tempatlahir2, tanggallahir2, pendidikan2, pekerjaan, penghasilan;
    //FROM LANGKAH 4 Pinjaman
    String berusahasejak, bidangusaha, jumlahkaryawan, alamatusaha, nomortelepon2, statuskepemilikan, ditempatisejak;
    //FROM LANGKAH 5 Pinjaman
    String namalengkap2, jeniskelamin, hubungan, alamattinggal, nomorteleponrumah, nomorhp;
    //FROM LANGKAH 6 Pinjaman
    String penghasilanperbulan, totalbiaya, proyeksikeuntungan, penghasilanlainnya, totalpinjamanlain,
            sisawaktuangsuran, angsuranpinjamanlain, totalpenghasilan, autodebet;

    String tanggal_pendaftaran;

    String asuransijiwa, asuransikerugian, asuransikredit, baliknama, ttdcalon, ttdsuamiistri;
    EditText aj, ak, ak2, bn, tc, ts;
    CheckBox check;
    String ip;
    private Exception exceptionToBeThrown;
    private Dialog loadingDialog;
    SharedPreferences sp;
    String user;
    String kondisi_isi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pinjaman7, container, false);

        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ip = getActivity().getResources().getString(R.string.ip);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        user = sp.getString("User",null);
        //LANGKAH 1
        namalengkap = getArguments().getString("namalengkap");
        tempatlahir = getArguments().getString("tempatlahir");
        tanggallahir = getArguments().getString("tanggallahir");
        namagadis = getArguments().getString("namagadis");
        pendidikan = getArguments().getString("pendidikan");
        alamat = getArguments().getString("alamat");
        kecamatan = getArguments().getString("kecamatan");
        kabupaten = getArguments().getString("kabupaten");
        provinsi = getArguments().getString("provinsi");
        kodepos = getArguments().getString("kodepos");
        //LANGKAH 2
        nomortelepon = getArguments().getString("nomortelepon");
        noktp = getArguments().getString("noktp");
        tanggalktp = getArguments().getString("tanggalktp");
        nonpwp = getArguments().getString("nonpwp");
        jumlahtanggungan = getArguments().getString("jumlahtanggungan");
        nokk = getArguments().getString("nokk");
        statustempat = getArguments().getString("statustempat");
        lamatinggal = getArguments().getString("lamatinggal");
        statuskawin = getArguments().getString("statuskawin");
        //LANGKAH 3
        namasuamiistri =  getArguments().getString("namasuamiistri");
        tempatlahir2 =  getArguments().getString("tempatlahir2");
        tanggallahir2 = getArguments().getString("tanggallahir2");
        pekerjaan =  getArguments().getString("pekerjaan");
        penghasilan = getArguments().getString("penghasilan");
        pendidikan2 = getArguments().getString("pendidikan2");
        //LANGKAH 4
        berusahasejak =  getArguments().getString("berusahasejak");
        bidangusaha =  getArguments().getString("bidangusaha");
        jumlahkaryawan =  getArguments().getString("jumlahkaryawan");
        alamatusaha =  getArguments().getString("alamatusaha");
        nomortelepon2 =  getArguments().getString("nomortelepon2");
        statuskepemilikan =  getArguments().getString("statuskepemilikan");
        ditempatisejak =  getArguments().getString("ditempatisejak");
        //LANGKAH 5
        namalengkap2 =getArguments().getString("namalengkap2");
        jeniskelamin =getArguments().getString("jeniskelamin");
        hubungan =getArguments().getString("hubungan");
        alamattinggal =getArguments().getString("alamattinggal");
        nomorteleponrumah =getArguments().getString("nomorteleponrumah");
        nomorhp =getArguments().getString("nomorhp");
        //LANGKAH 6
        penghasilanperbulan = getArguments().getString("penghasilanperbulan");
        totalbiaya = getArguments().getString("totalbiaya");
        proyeksikeuntungan = getArguments().getString("proyeksikeuntungan");
        penghasilanlainnya = getArguments().getString("penghasilanlainnya");
        totalpinjamanlain = getArguments().getString("totalpinjamanlain");
        sisawaktuangsuran = getArguments().getString("sisawaktuangsuran");
        angsuranpinjamanlain = getArguments().getString("angsuranpinjamanlain");
        totalpenghasilan = getArguments().getString("totalpenghasilan");
        autodebet = getArguments().getString("autodebet");

        aj = (EditText) v.findViewById(R.id.pmenutupasuransijiwa);
        ak = (EditText) v.findViewById(R.id.pmenutupasuransikerugian);
        ak2 = (EditText) v.findViewById(R.id.pmenutupasuransikredit);
        bn = (EditText) v.findViewById(R.id.pbaliknama);
        tc = (EditText) v.findViewById(R.id.pttd);
        ts = (EditText) v.findViewById(R.id.pttd2);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        tanggal_pendaftaran = df.format(c.getTime());

        final Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setEnabled(false);
        btn.setAlpha(0.5f);

        CheckBox mCheckBox= ( CheckBox ) v.findViewById(R.id.checkBox2);
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
                //String asuransijiwa, asuransikerugian, asuransikredit, baliknama, ttdcalon, ttdsuamiistri;
                // EditText aj, ak, ak2, bn, tc, ts;
                cekisi();
                if (cekisi().toString().equals("true")) {
                    asuransijiwa = aj.getText().toString();
                    asuransikerugian = ak.getText().toString();
                    asuransikredit = ak2.getText().toString();
                    baliknama = bn.getText().toString();
                    ttdcalon = tc.getText().toString();
                    ttdsuamiistri = ts.getText().toString();
                    kirimdata();
                    sendEmail();
                    if (exceptionToBeThrown != null) {

                    } else {
                        Fragment terakhirFragment = new Pinjaman8();
                        fragmentTransaction.replace(R.id.containerView, terakhirFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        loadingDialog.dismiss();
                    }
                }
            }
        });
        return v;
    }

    public void kirimdata(){
        loadingDialog = ProgressDialog.show(getActivity(), "Mohon Tunggu sebentar", "Data sedang diproses");
        int TIMEOUT_MILLISEC = 10000;
        String username = user;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(55);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("nama_lengkap", namalengkap));
        nameValuePairs.add(new BasicNameValuePair("tempat_lahir", tempatlahir));
        nameValuePairs.add(new BasicNameValuePair("tanggal_lahir", tanggallahir));
        nameValuePairs.add(new BasicNameValuePair("nama_gadis", namagadis));
        nameValuePairs.add(new BasicNameValuePair("pendidikan", pendidikan));
        nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
        nameValuePairs.add(new BasicNameValuePair("kecamatan", kecamatan));
        nameValuePairs.add(new BasicNameValuePair("kabupaten", kabupaten));
        nameValuePairs.add(new BasicNameValuePair("provinsi", provinsi));
        nameValuePairs.add(new BasicNameValuePair("kode_pos", kodepos));
        nameValuePairs.add(new BasicNameValuePair("nomor_telepon", nomortelepon));
        nameValuePairs.add(new BasicNameValuePair("no_ktp", noktp));
        nameValuePairs.add(new BasicNameValuePair("tanggal_ktp", tanggalktp));
        nameValuePairs.add(new BasicNameValuePair("no_npwp", nonpwp));
        nameValuePairs.add(new BasicNameValuePair("jumlah_tanggungan", jumlahtanggungan));
        nameValuePairs.add(new BasicNameValuePair("no_kk", nokk));
        nameValuePairs.add(new BasicNameValuePair("status_tempat", statustempat));
        nameValuePairs.add(new BasicNameValuePair("lama_tinggal", lamatinggal));
        nameValuePairs.add(new BasicNameValuePair("status_kawin", statuskawin));
        nameValuePairs.add(new BasicNameValuePair("nama_suamiistri", namasuamiistri));
        nameValuePairs.add(new BasicNameValuePair("tempat_lahir2", tempatlahir2));
        nameValuePairs.add(new BasicNameValuePair("tanggal_lahir2", tanggallahir2));
        nameValuePairs.add(new BasicNameValuePair("pekerjaan", pekerjaan));
        nameValuePairs.add(new BasicNameValuePair("penghasilan", penghasilan));
        nameValuePairs.add(new BasicNameValuePair("pendidikan2", pendidikan2));
        nameValuePairs.add(new BasicNameValuePair("berusaha_sejak", berusahasejak));
        nameValuePairs.add(new BasicNameValuePair("bidang_usaha", bidangusaha));
        nameValuePairs.add(new BasicNameValuePair("jumlah_karyawan", jumlahkaryawan));
        nameValuePairs.add(new BasicNameValuePair("alamat_usaha", alamatusaha));
        nameValuePairs.add(new BasicNameValuePair("nomor_telepon2", nomortelepon));
        nameValuePairs.add(new BasicNameValuePair("status_kepemilikan", statuskepemilikan));
        nameValuePairs.add(new BasicNameValuePair("ditempati_sejak", ditempatisejak));
        nameValuePairs.add(new BasicNameValuePair("nama_lengkap2", namalengkap2));
        nameValuePairs.add(new BasicNameValuePair("jenis_kelamin", jeniskelamin));
        nameValuePairs.add(new BasicNameValuePair("hubungan", hubungan));
        nameValuePairs.add(new BasicNameValuePair("alamat_tinggal", alamattinggal));
        nameValuePairs.add(new BasicNameValuePair("nomor_teleponrumah", nomorteleponrumah));
        nameValuePairs.add(new BasicNameValuePair("nomor_hp", nomorhp));
        nameValuePairs.add(new BasicNameValuePair("penghasilan_perbulan", penghasilanperbulan));
        nameValuePairs.add(new BasicNameValuePair("total_biaya", totalbiaya));
        nameValuePairs.add(new BasicNameValuePair("proyeksi_keuntungan", proyeksikeuntungan));
        nameValuePairs.add(new BasicNameValuePair("penghasilan_lainnya", penghasilanlainnya));
        nameValuePairs.add(new BasicNameValuePair("total_pinjamanlain", totalpinjamanlain));
        nameValuePairs.add(new BasicNameValuePair("sisa_waktuangsuran", sisawaktuangsuran));
        nameValuePairs.add(new BasicNameValuePair("angsuran_pinjamanlain", angsuranpinjamanlain));
        nameValuePairs.add(new BasicNameValuePair("total_penghasilan", totalpenghasilan));
        nameValuePairs.add(new BasicNameValuePair("auto_debet", autodebet));
        nameValuePairs.add(new BasicNameValuePair("asuransi_jiwa", asuransijiwa));
        nameValuePairs.add(new BasicNameValuePair("asuransi_kerugian", asuransikerugian));
        nameValuePairs.add(new BasicNameValuePair("asuransi_kredit", asuransikredit));
        nameValuePairs.add(new BasicNameValuePair("balik_nama", baliknama));
        nameValuePairs.add(new BasicNameValuePair("ttd_calon", ttdcalon));
        nameValuePairs.add(new BasicNameValuePair("ttd_suamiistri", ttdsuamiistri));
        nameValuePairs.add(new BasicNameValuePair("tanggal_pendaftaran", tanggal_pendaftaran));
        try {
            HttpPost request = new HttpPost("http://"+ip+"/android/pinjaman_lunak.php");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Terjadi kesalahan Cek internet anda dan coba ulangi kembali", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exceptionToBeThrown =e;
        }
        //Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_LONG).show();
    }

    public String cekisi(){
        kondisi_isi="false";
        asuransijiwa = aj.getText().toString();
        asuransikerugian = ak.getText().toString();
        asuransikredit = ak2.getText().toString();
        baliknama = bn.getText().toString();
        ttdcalon = tc.getText().toString();
        ttdsuamiistri = ts.getText().toString();
        if(aj.getText().toString().equals("")){
            aj.setError("Kolom ini harus diisi");
            aj.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ak.getText().toString().equals("")){
            ak.setError("Kolom ini harus diisi");
            ak.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ak2.getText().toString().equals("")){
            ak2.setError("Kolom ini harus diisi");
            ak2.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(bn.getText().toString().equals("")){
            bn.setError("Kolom ini harus diisi");
            bn.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(tc.getText().toString().equals("")){
            tc.setError("Kolom ini harus diisi");
            tc.setFocusable(true);
            Toast.makeText(getActivity(),"Harap isi semua kolom",Toast.LENGTH_SHORT).show();
        }
        else if(ts.getText().toString().equals("")){
            ts.setError("Kolom ini harus diisi");
            ts.setFocusable(true);
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
        String subject = "PENDAFTARAN PINJAMAN LUNAK";
        String header = "DATA PENDAFTARAN PINJAMAN\n" +
                "Username : "+user+"\n" +
                "Nama Lengkap : "+namalengkap+"\n" +
                "Tempat Lahir : "+tempatlahir+"\n" +
                "Tanggal Lahir : "+tanggallahir+"\n" +
                "Nama Gadis : "+namagadis+"\n" +
                "Pendidikan : "+pendidikan+"\n" +
                "Alamat : "+alamat+"\n"+
                "Kecamatan : "+kecamatan+"\n"+
                "Kabupaten : "+kabupaten+"\n" +
                "Provinsi : "+provinsi+"\n" +
                "Kode Pos : "+kodepos+"\n" +
                "Nomor Telepon : "+nomortelepon+"\n" +
                "Nomor KTP : "+noktp+"\n" +
                "Tanggal KTP : "+tanggalktp+"\n" +
                "Nomor NPWP : "+nonpwp+"\n"+
                "Jumlah Tanggungan : "+jumlahtanggungan+"\n" +
                "Nomor KK : "+nokk+"\n" +
                "Status Tempat Tinggal : "+statustempat+"\n" +
                "Lama Tinggal : "+lamatinggal+"\n" +
                "Status Kawin : "+statuskawin+"\n" +
                "Nama Suami/Istri : "+namasuamiistri+"\n" +
                "Tempat Lahir : "+tempatlahir2+"\n"+
                "Tanggal Lahir : "+tanggallahir2+"\n"+
                "Pekerjaan : "+pekerjaan+"\n" +
                "Penghasilan : "+penghasilan+"\n" +
                "Pendidikan : "+pendidikan2+"\n" +
                "Berusaha Sejak : "+berusahasejak+"\n" +
                "Bidang Usaha : "+bidangusaha+"\n" +
                "Jumlah Karyawan : "+jumlahkaryawan+"\n" +
                "Alamat Usaha : "+alamatusaha+"\n"+
                "Nomor Telepon : "+nomortelepon2+"\n"+
                "Status Kepemilikan : "+statuskepemilikan+"\n" +
                "Ditempati Sejak : "+ditempatisejak+"\n" +
                "Nama Lengkap : "+namalengkap2+"\n"+
                "Jenis Kelamin : "+jeniskelamin+"\n" +
                "Hubungan : "+hubungan+"\n" +
                "Alamat Tinggal : "+alamattinggal+"\n" +
                "Nomor Telepon Rumah : "+nomorteleponrumah+"\n" +
                "Nomor HP : "+nomorhp+"\n" +
                "Penghasilan Perbulan : "+penghasilanperbulan+"\n" +
                "Total Biaya : "+totalbiaya+"\n"+
                "Proyeksi Keuntungan : "+proyeksikeuntungan+"\n"+
                "Penghasilan Lainnya : "+penghasilanlainnya+"\n" +
                "Total Pinjaman Lain : "+totalpinjamanlain+"\n" +
                "Sisa Waktu Angsuran : "+pendidikan2+"\n" +
                "Angsuran Pinjaman Lain : "+angsuranpinjamanlain+"\n" +
                "Total Penghasilan : "+totalpenghasilan+"\n" +
                "Auto Debet : "+autodebet+"\n" +
                "Asuransi Jiwa : "+asuransijiwa+"\n"+
                "Asuransi Kerugian : "+asuransikerugian+"\n"+
                "Asuransi Kredit : "+asuransikredit+"\n" +
                "Balik Nama : "+baliknama+"\n" +
                "Tanda Tangan Calon : "+ttdcalon+"\n" +
                "Tanda Tangan Suami Istri : "+ttdsuamiistri+"\n"+
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
