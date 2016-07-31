package com.eragano.eraganoapps.performa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.jual.camera.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputKinerja extends android.support.v4.app.Fragment {

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_input_kinerja, container, false);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);

        nama = (EditText) v.findViewById(R.id.txtnamapetani);
        jenis = (EditText) v.findViewById(R.id.txtjenistanaman);
        tanggal = (EditText) v.findViewById(R.id.txttanggalpelaporan);
        keterangan = (EditText) v.findViewById(R.id.txtketerangankeadaan);
        gambar = (ImageView) v.findViewById(R.id.gambarTampung);
        send = (Button) v.findViewById(R.id.btnmasukkan);


        array_spinner=new String[6];
        array_spinner[0]="Penyakit";
        array_spinner[1]="Hama";
        array_spinner[2]="Pengairan";
        array_spinner[3]="Tanah";
        array_spinner[4]="Teknik Budidaya";
        array_spinner[5]="Lain - lain";
        hambatan = (Spinner) v.findViewById(R.id.spinner_hambatan);
        ArrayAdapter adapter = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, array_spinner);
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
        datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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
                    //sendEmail();
                    //new UploadFileToServer().execute();
                }
            }
        });

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
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
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
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
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == -1) {
            // successfully captured the image
            // launching upload activity
            launchUploadActivity(true);


        } else if (resultCode == 0) {

            // user cancelled Image capture
            Toast.makeText(getActivity().getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();

        } else {
            // failed to capture image
            Toast.makeText(getActivity().getApplicationContext(),
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
        gambar.setImageBitmap(bitmap2);
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
}
