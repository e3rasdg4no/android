package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Performa;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 5/20/2016.
 */
public class PerformaAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Performa> mPosts;
    private ViewHolder mViewHolder;

    private Performa mPost;
    private Activity mActivity;


    public PerformaAdapter(Activity activity, List<Performa> posts) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mPosts = posts;
        mActivity = activity;
    }

    public void cleardata(){
        if(mPosts !=null && mPosts.isEmpty()){
            mPosts.clear();
        }
    }
    @Override
    public int getCount()  {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_performa, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.produk = (ImageView) convertView.findViewById(R.id.gambar_produk);
            mViewHolder.jenisTanaman = (TextView) convertView.findViewById(R.id.jenistanaman);
            mViewHolder.hambatan = (TextView) convertView.findViewById(R.id.hambatan);
            mViewHolder.ketHambatan = (TextView) convertView.findViewById(R.id.keteranganhambatan);
            mViewHolder.tanggalUp = (TextView) convertView.findViewById(R.id.tanggal_up);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        final String value = "http://103.236.201.252/android/performa/uploads/"+mPost.getGambar();
        Picasso.with(mActivity)
                .load(value)
                .resize(600,600)
                .centerCrop()
                .into(mViewHolder.produk);
        mViewHolder.produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog settingsDialog = new Dialog(mActivity);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                ImageView imageView = new ImageView(mActivity);
                Picasso.with(mActivity)
                        .load(value)
                        .resize(600,600)
                        .centerCrop()
                        .into(imageView);
                settingsDialog.setContentView(mInflater.inflate(R.layout.activity_display_image, null));
                settingsDialog.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                //settingsDialog.onTouchEvent()
                settingsDialog.show();
            }
        });

        mViewHolder.jenisTanaman.setText(mPost.getJenis_tanaman());
        mViewHolder.hambatan.setText(mPost.getHambatan());
        mViewHolder.ketHambatan.setText(mPost.getKeterangan());
        mViewHolder.tanggalUp.setText(mPost.getTanggal_pelaporan());

        return convertView;
    }

    private static class ViewHolder {
        ImageView produk;
        TextView jenisTanaman;
        TextView hambatan;
        TextView ketHambatan;
        TextView tanggalUp;
    }
}
