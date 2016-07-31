package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.ecommerce.KeranjangActivity;
import com.eragano.eraganoapps.penampung.Keranjang;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/13/2016.
 */
public class KeranjangAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Keranjang> mPosts;
    private ViewHolder mViewHolder;


    private Bitmap mBitmap;
    private Keranjang mPost;
    private Activity mActivity;
    WebView wv;

    public KeranjangAdapter(Activity activity, List<Keranjang> posts) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mPosts = posts;
        mActivity = activity;
    }


    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_keranjang, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.img_produk);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.judul_produk);
            mViewHolder.isi = (TextView) convertView.findViewById(R.id.penjelasan);
            mViewHolder.price = (TextView) convertView.findViewById(R.id.harga_produk);
            mViewHolder.perusahaan = (TextView) convertView.findViewById(R.id.pt);
            mViewHolder.jumlah = (TextView) convertView.findViewById(R.id.jumlah_beli);
            mViewHolder.hapus = (Button) convertView.findViewById(R.id.btn_hapus);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        String value = "http://103.236.201.252/android/image/"+mPost.getPicture();
        Picasso.with(mActivity)
                .load(value)
                .into(mViewHolder.thumbnail);

        mViewHolder.title.setText(mPost.getProduct_name());
        mViewHolder.isi.setText(mPost.getKeterangan());

        String x = mPost.getSale_price();
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

        mViewHolder.price.setText("IDR "+x);
        mViewHolder.perusahaan.setText(mPost.getProducent());
        mViewHolder.title.setText(mPost.getProduct_name());
        mViewHolder.jumlah.setText("Jumlah: "+mPost.getJumlah());

        mViewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost = mPosts.get(position);
                KeranjangActivity.hapus(mActivity,mPost.getId_keranjang());
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView isi;
        TextView price;
        TextView perusahaan;
        TextView jumlah;
        Integer i;
        Button hapus;
        //TextView date;
    }
}
