package com.eragano.eraganoapps.jual.camera;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.eragano.eraganoapps.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JualCameraActivity extends AppCompatActivity {

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = JualCameraActivity.class.getSimpleName();

    private Uri fileUri; // file url to store image/video

    //ProgressBar pb;
    Spinner spin;
    private String array_spinner[];
    SharedPreferences sp;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText etNama, etTanggal, etJumlah, etHarga;
    private Bitmap bitmap;
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    long totalSize = 0;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://103.236.201.252/android/upload_produk.php";

    private String KEY_IMAGE = "image";
    private String KEY_USER = "user";
    private String KEY_NAME = "nama";
    private String KEY_PANEN = "tanggal_panen";
    private String KEY_SATUAN = "satuan";
    private String KEY_HARGA = "harga";
    private String KEY_STOK = "stok";
    private String KEY_TANGGAL = "tanggal";

    private String filePath = null;

    String kondisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);

        ImageButton imgback = (ImageButton) findViewById(R.id.imageback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //pb = (ProgressBar) findViewById(R.id.pb);

        ImageButton imgbantu = (ImageButton) findViewById(R.id.imageBantu);
        imgbantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711232483"));
                if (ActivityCompat.checkSelfPermission(JualCameraActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        //MENDAFTAR SEBAGAI
        array_spinner=new String[6];
        array_spinner[0]="Gram";
        array_spinner[1]="Kilogram";
        array_spinner[2]="Kuintal";
        array_spinner[3]="Ton";
        array_spinner[4]="Ikat";
        array_spinner[5]="Buah";
        spin = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter adapter = new ArrayAdapter( JualCameraActivity.this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
        spin.setAdapter(adapter);

        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        buttonUpload = (Button) findViewById(R.id.upload);

        etNama = (EditText) findViewById(R.id.namaProduk);
        etTanggal = (EditText) findViewById(R.id.tanggalPanen);
        etJumlah = (EditText) findViewById(R.id.jumlah);
        etHarga = (EditText) findViewById(R.id.harga);

        etTanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(JualCameraActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTanggal.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        imageView  = (ImageView) findViewById(R.id.gambarTampung);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().equals("true")) {
                    sendEmail();
                    new UploadFileToServer().execute();
                }
            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }

    //MULAI DARI SINI FUNGSI UPLOAD IMAGE LEWAT CAMERA
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == RESULT_OK) {
            // successfully captured the image
            // launching upload activity
            launchUploadActivity(true);


        } else if (resultCode == RESULT_CANCELED) {

            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();

        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void launchUploadActivity(boolean isImage){
        BitmapFactory.Options options = new BitmapFactory.Options();
        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 15;
        Bitmap bitmap2 = BitmapFactory.decodeFile(fileUri.getPath(), options);
        filePath = fileUri.getPath();
        Glide.with(JualCameraActivity.this).load(fileUri).into(imageView);
        //imageView.setImageBitmap(bitmap2);
        imageView.setPadding(1,1,1,1);
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            return null;
        }
        return mediaFile;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //pb.setProgress(0);
            //dialog = ProgressDialog.show(JualCameraActivity.this, "Silahkan tunggu", "Data sedang di proses", false, false);
            dialog = new ProgressDialog(JualCameraActivity.this);
            dialog.setMessage("Data sedang di proses");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //pb.setVisibility(View.VISIBLE);

            // updating progress bar value
            //pb.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL3);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c.getTime());
                String user = sp.getString("User",null);
                String name = etNama.getText().toString().trim();
                String tgl = etTanggal.getText().toString().trim();
                String jml = etJumlah.getText().toString().trim();
                String stn = spin.getSelectedItem().toString();
                String hrg = etHarga.getText().toString().trim();
                String tgl2 = formattedDate.trim();
                Log.d("USER ", user);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("website", new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));
                entity.addPart(KEY_USER, new StringBody(user));
                entity.addPart(KEY_NAME, new StringBody(name));
                entity.addPart(KEY_PANEN, new StringBody(tgl));
                entity.addPart(KEY_SATUAN, new StringBody(stn));
                entity.addPart(KEY_STOK, new StringBody(jml));
                entity.addPart(KEY_HARGA, new StringBody(hrg));
                entity.addPart(KEY_TANGGAL, new StringBody(tgl2));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);
            dialog.dismiss();
            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Produk Anda Telah Masuk Kedalam List Penjualan Produk")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //SENT EMAIL NOTIFICATION
    private void sendEmail() {
        //Getting content for email
        String namalengkap = sp.getString("NAMA_LENGKAP",null);
        String user = sp.getString("User",null);
        String name = etNama.getText().toString().trim();
        String tgl = etTanggal.getText().toString().trim();
        String jml = etJumlah.getText().toString().trim();
        String stn = spin.getSelectedItem().toString();
        String hrg = etHarga.getText().toString().trim();
        String kabupaten = sp.getString("KABUPATEN", null);
        String notelepon = sp.getString("NO_TELEPON",null);
        String email = "panen.eragano@gmail.com";

        String subject = "Jual Hasil Panen";
        String header = "DATA PENJUAL\n" +
                "Nama Lengkap : "+namalengkap+"\n" +
                "Username Penjual : "+user+"\n" +
                "No Telepon : "+notelepon+"\n" +
                "Kabupaten/Kota : "+kabupaten+"\n" +
                "Nama Produk : "+name+"\n" +
                "Tanggal Panen : "+tgl+"\n" +
                "Jumlah : "+jml+" "+stn+"\n" +
                "Harga : Rp."+hrg+"\n";
        //String message = header;

        //Creating SendMail object
        SendMail sm = new SendMail(JualCameraActivity.this, email, subject, header);
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
            //progressDialog = ProgressDialog.show(context, "Pesanan Sedang Di Proses", "Mohon Tunggu", false, false);
            progressDialog = new ProgressDialog(JualCameraActivity.this);
            progressDialog.setMessage("Data sedang di proses");
            progressDialog.setCancelable(false);
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

    public String cekisi(){
        kondisi="false";
        if(etNama.getText().toString().equals("")){
            etNama.setError("Kolom ini harus diisi");
            etNama.requestFocus();
        }
        else if(etTanggal.getText().toString().equals("")){
            etTanggal.setError("Kolom ini harus diisi");
            etTanggal.requestFocus();
        }
        else if(etJumlah.getText().toString().equals("")){
            etJumlah.setError("Kolom ini harus diisi");
            etJumlah.requestFocus();
        }
        else if(etHarga.getText().toString().equals("")){
            etHarga.setError("Kolom ini harus diisi");
            etHarga.requestFocus();
        }
        else {
            kondisi="true";
        }
        return kondisi;
    }
}


