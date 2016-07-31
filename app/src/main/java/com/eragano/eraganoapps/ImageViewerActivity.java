package com.eragano.eraganoapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewerActivity extends Activity {

    private PhotoViewAttacher mAttacher;
    String URL = "http://103.236.201.252/android/image/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(null);
        Intent i = getIntent();

        ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);

        String value = URL+i.getStringExtra("FOTO");
        Picasso.with(ImageViewerActivity.this)
                .load(value)
                .into(mImageView);

        // The MAGIC happens here!
        mAttacher = new PhotoViewAttacher(mImageView);

        // Lets attach some listeners, not required though!
        //mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
        //mAttacher.setOnPhotoTapListener(new PhotoTapListener());
    }
}
