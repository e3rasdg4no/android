package com.eragano.eraganoapps.performa;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.helper.HelperApp;
import com.eragano.eraganoapps.jual.camera.AndroidMultiPartEntity;
import com.eragano.eraganoapps.jual.camera.Config;

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

public class InputKinerjaActivity extends Activity {

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Uri fileUri; // file url to store image/video
    private String filePath = null;

    EditText nama, jenis, tanggal, keterangan;
    ImageView gambar;
    Button send;
    Spinner hambatan;
    String kondisi;
    private String array_spinner[];
    SharedPreferences sp;

    private Bitmap bitmap;
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;
    long totalSize = 0;

    String UPLOAD_URL = "http://103.236.201.252/android/performa/upload_file.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kinerja2);
        cacheImage(savedInstanceState);
        ImageButton imgback = (ImageButton) findViewById(R.id.imageButton3);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //pb = (ProgressBar) findViewById(R.id.pb);

        ImageButton imgbantu = (ImageButton) findViewById(R.id.imageButton2);
        imgbantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711232483"));
                if (ActivityCompat.checkSelfPermission(InputKinerjaActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);

        nama = (EditText) findViewById(R.id.txtnamapetani);
        jenis = (EditText) findViewById(R.id.txtjenistanaman);
        tanggal = (EditText) findViewById(R.id.txttanggalpelaporan);
        keterangan = (EditText) findViewById(R.id.txtketerangankeadaan);
        gambar = (ImageView) findViewById(R.id.gambarTampung);
        send = (Button) findViewById(R.id.btnmasukkan);


        array_spinner=new String[6];
        array_spinner[0]="Penyakit";
        array_spinner[1]="Hama";
        array_spinner[2]="Pengairan";
        array_spinner[3]="Tanah";
        array_spinner[4]="Teknik Budidaya";
        array_spinner[5]="Lain - lain";
        hambatan = (Spinner) findViewById(R.id.spinner_hambatan);
        ArrayAdapter adapter = new ArrayAdapter( InputKinerjaActivity.this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
        hambatan.setAdapter(adapter);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datepicker.show();
                return true;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(InputKinerjaActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekisi();
                if(cekisi().equals("true")) {
                    sendEmail();
                    uploadImage();
                }
            }
        });

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
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


    public String cekisi(){
        kondisi="false";
        if(nama.getText().toString().equals("")){
            nama.setError("Kolom ini harus diisi");
            nama.requestFocus();
        }
        else if(jenis.getText().toString().equals("")){
            jenis.setError("Kolom ini harus diisi");
            jenis.requestFocus();
        }
        else if(tanggal.getText().toString().equals("")){
            tanggal.setError("Kolom ini harus diisi");
            tanggal.requestFocus();
        }
        else if(keterangan.getText().toString().equals("")){
            keterangan.setError("Kolom ini harus diisi");
            keterangan.requestFocus();
        }
        else {
            kondisi="true";
        }
        return kondisi;
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
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("return-data", true);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/



        if(Build.VERSION.SDK_INT < 19){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Your Camera");
            fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent    = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        else
        {
            Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                String photoFile = null;
                try {
                    photoFile = HelperApp.createImageFileCameraDefault();
                    fileUri = Uri.parse(photoFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(photoFile));
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                }

            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

   private void cacheImage(Bundle savedInstanceState)
   {
       if(savedInstanceState != null)
       {
           fileUri = savedInstanceState.getParcelable("file_uri");
       }
   }
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if the result is capturing Image
        if (resultCode == RESULT_OK) {
            // successfully captured the image
            // launching upload activity
            launchUploadActivity(true);
            //Uri f = data.getData();
            //applyBitmap(f);
            //Toast.makeText(InputKinerjaActivity.this, data.toString(), Toast.LENGTH_SHORT).show();

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
        options.inSampleSize = 1;
        bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
        filePath = fileUri.getPath();
        Glide.with(InputKinerjaActivity.this).load(fileUri).into(gambar);
        //gambar.setImageBitmap(bitmap2);
        gambar.setPadding(1,1,1,1);
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading;
        loading = new ProgressDialog(InputKinerjaActivity.this);
        loading.setMessage("Data sedang di proses");
        loading.setCancelable(false);
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(InputKinerjaActivity.this, s, Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(InputKinerjaActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String > getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = String.valueOf(c.getTime().getHours())+String.valueOf(c.getTime().getMinutes());

                //Getting Image Name
                String user = sp.getString("User",null);
                String name = nama.getText().toString().trim();
                String jns = jenis.getText().toString().trim();
                String tgl = tanggal.getText().toString().trim();
                String ham = hambatan.getSelectedItem().toString();
                String ket = keterangan.getText().toString().trim();
                String jam = formattedDate;
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("user", user);
                params.put("image", image);
                params.put("nama_petani", name);
                params.put("tanggal_pelaporan", tgl);
                params.put("jenis_tanaman", jns);
                params.put("hambatan", ham);
                params.put("keterangan", ket);
                params.put("jam", jam);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

   /* private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //pb.setProgress(0);
            dialog = new ProgressDialog(InputKinerjaActivity.this);
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
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL2);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                String user = sp.getString("User",null);
                String name = nama.getText().toString().trim();
                String jns = jenis.getText().toString().trim();
                String tgl = tanggal.getText().toString().trim();
                String ham = hambatan.getSelectedItem().toString();
                String ket = keterangan.getText().toString().trim();

                Log.d("USER ", user);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                // Extra parameters if you want to pass to server
                entity.addPart("website", new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));
                entity.addPart("user", new StringBody(user));
                entity.addPart("nama_petani", new StringBody(name));
                entity.addPart("jenis_tanaman", new StringBody(jns));
                entity.addPart("tanggal_pelaporan", new StringBody(tgl));
                //entity.addPart("gambar", new StringBody(jml));
                entity.addPart("hambatan", new StringBody(ham));
                entity.addPart("keterangan", new StringBody(ket));

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

    }*/

    /**
     * Method to show alert dialog
     * */
    /*private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kinerja anda sudah tercatat")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

    //SENT EMAIL NOTIFICATION
    private void sendEmail() {
        /* String user = sp.getString("User",null);
                */
        //Getting content for email
        String namalengkap = sp.getString("NAMA_LENGKAP",null);
        String user = sp.getString("User",null);
        String name = nama.getText().toString().trim();
        String jns = jenis.getText().toString().trim();
        String tgl = tanggal.getText().toString().trim();
        String ham = hambatan.getSelectedItem().toString();
        String ket = keterangan.getText().toString().trim();
        String kabupaten = sp.getString("KABUPATEN", null);
        String notelepon = sp.getString("NO_TELEPON",null);
        String email = "lapor.eragano@gmail.com";

        String subject = "KINERJA";
        String header = "DATA KINERJA\n" +
                "Nama Lengkap : "+namalengkap+"\n" +
                "Username Petani : "+user+"\n" +
                "No Telepon : "+notelepon+"\n" +
                "Kabupaten/Kota : "+kabupaten+"\n" +
                "Nama Petani : "+name+"\n" +
                "Jenis Tanaman : "+jns+"\n" +
                "Tanggal : "+tgl+"\n" +
                "Hambatan : "+ham+"\n" +
                "Keterangan : "+ket+"\n";
        //String message = header;

        //Creating SendMail object
        SendMail sm = new SendMail(InputKinerjaActivity.this, email, subject, header);
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
            progressDialog = new ProgressDialog(InputKinerjaActivity.this);
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

    private void applyBitmap(Uri filePath)
    {
        if(filePath != null)
        {
            File f = new File(filePath.getPath());
            String finalPath = f.getAbsolutePath();
            Toast.makeText(InputKinerjaActivity.this, finalPath, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(InputKinerjaActivity.this, "NULL FILEPATH", Toast.LENGTH_SHORT).show();
        }
    }
}
