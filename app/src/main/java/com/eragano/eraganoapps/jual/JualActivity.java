package com.eragano.eraganoapps.jual;

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
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JualActivity extends AppCompatActivity implements View.OnClickListener {

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = JualActivity.class.getSimpleName();

    private Uri fileUri; // file url to store image/video

    Spinner spin;
    private String array_spinner[];
    SharedPreferences sp;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText etNama, etTanggal, etJumlah, etHarga;
    private Bitmap bitmap;
    DatePickerDialog datepicker;
    SimpleDateFormat dateFormat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);

        cacheImage(savedInstanceState);
        ImageButton imgback = (ImageButton) findViewById(R.id.imageback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton imgbantu = (ImageButton) findViewById(R.id.imageBantu);
        imgbantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711232483"));
                if (ActivityCompat.checkSelfPermission(JualActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        ArrayAdapter adapter = new ArrayAdapter( JualActivity.this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
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
        datepicker = new DatePickerDialog(JualActivity.this, new DatePickerDialog.OnDateSetListener() {

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

                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Mengunggah Data..","Mohon Tunggu",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(JualActivity.this, s, Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(JualActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String > getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c.getTime());

                //Getting Image Name
                String user = sp.getString("User",null);
                String name = etNama.getText().toString().trim();
                String tgl = etTanggal.getText().toString().trim();
                String jml = etJumlah.getText().toString().trim();
                String stn = spin.getSelectedItem().toString();
                String hrg = etHarga.getText().toString().trim();
                String tgl2 = formattedDate.trim();
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_USER, user);
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put(KEY_PANEN, tgl);
                params.put(KEY_SATUAN, stn);
                params.put(KEY_STOK, jml);
                params.put(KEY_HARGA, hrg);
                params.put(KEY_TANGGAL, tgl2);


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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                /*bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                Log.d("TES LOG",filePath.getPath());
                imageView.setImageBitmap(bitmap);
                imageView.setPadding(1,1,1,1);*/
                applyBitmap(filePath);
            } catch (Exception e) {
                Toast.makeText(JualActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void applyBitmap(Uri filePath)
    {
        File f = new File(filePath.getPath());
        String finalPath = f.getAbsolutePath();
        Toast.makeText(JualActivity.this, finalPath, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpload){
            uploadImage();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("uriFile", fileUri);
    }

    private void cacheImage(Bundle saveInstance)
    {
        if(saveInstance != null)
        {
            fileUri = saveInstance.getParcelable("uriFile");
        }
    }
}
