package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.ImageViewerActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.ecommerce.Ecommerce2Activity;
import com.eragano.eraganoapps.penampung.Ecommerce;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/12/2016.
 */
public class EcommerceAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Ecommerce> mPosts;
    private ViewHolder mViewHolder;


    private Bitmap mBitmap;
    private Ecommerce mPost;
    private Activity mActivity;
    WebView wv;

    public EcommerceAdapter(Activity activity, List<Ecommerce> posts) {
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
            convertView = mInflater.inflate(R.layout.layout_ecommerce, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.img_produk);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.judul_produk);
            mViewHolder.isi = (TextView) convertView.findViewById(R.id.penjelasan);
            mViewHolder.price = (TextView) convertView.findViewById(R.id.harga_produk);
            mViewHolder.perusahaan = (TextView) convertView.findViewById(R.id.pt);
            mViewHolder.beli = (Button) convertView.findViewById(R.id.button);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        String value = "http://103.236.201.252/android/image/"+mPost.getPicture();
        Picasso.with(mActivity)
                .load(value)
                .into(mViewHolder.thumbnail);
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

        mViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost = mPosts.get(position);
                Intent i = new Intent(mActivity, ImageViewerActivity.class);
                i.putExtra("FOTO", mPost.getPicture());
                mActivity.startActivity(i);
            }
        });

        mViewHolder.title.setText(mPost.getProduct_name());
        mViewHolder.isi.setText(mPost.getKeterangan());
        mViewHolder.price.setText("IDR " + x);
        mViewHolder.perusahaan.setText(mPost.getProducent());
        mViewHolder.title.setText(mPost.getProduct_name());

        mViewHolder.isi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost=mPosts.get(position);
                Ecommerce2Activity as = new Ecommerce2Activity();
                Bundle bundle = new Bundle();
                bundle.putString("ID_PRODUCT", mPost.getId_product());
                bundle.putString("NAMA", mPost.getProduct_name());
                bundle.putString("KETERANGAN", mPost.getKeterangan());
                bundle.putString("PICTURE", mPost.getPicture());
                bundle.putString("UKURAN", mPost.getUkuran());
                bundle.putString("HARGA", mPost.getSale_price());
                bundle.putString("DESKRIPSI", mPost.getDeskripsi());
                bundle.putString("ANJURAN", mPost.getAnjuran());
                bundle.putString("SATUAN", mPost.getJenis_satuan());
                as.setArguments(bundle);
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.replace(R.id.containerView, as);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewHolder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost=mPosts.get(position);
                Ecommerce2Activity as = new Ecommerce2Activity();
                Bundle bundle = new Bundle();
                bundle.putString("ID_PRODUCT", mPost.getId_product());
                bundle.putString("NAMA", mPost.getProduct_name());
                bundle.putString("KETERANGAN", mPost.getKeterangan());
                bundle.putString("PICTURE", mPost.getPicture());
                bundle.putString("UKURAN", mPost.getUkuran());
                bundle.putString("HARGA", mPost.getSale_price());
                bundle.putString("DESKRIPSI", mPost.getDeskripsi());
                bundle.putString("ANJURAN", mPost.getAnjuran());
                bundle.putString("SATUAN", mPost.getJenis_satuan());
                as.setArguments(bundle);
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.replace(R.id.containerView, as);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewHolder.perusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost=mPosts.get(position);
                Ecommerce2Activity as = new Ecommerce2Activity();
                Bundle bundle = new Bundle();
                bundle.putString("ID_PRODUCT", mPost.getId_product());
                bundle.putString("NAMA", mPost.getProduct_name());
                bundle.putString("KETERANGAN", mPost.getKeterangan());
                bundle.putString("PICTURE", mPost.getPicture());
                bundle.putString("UKURAN", mPost.getUkuran());
                bundle.putString("HARGA", mPost.getSale_price());
                bundle.putString("DESKRIPSI", mPost.getDeskripsi());
                bundle.putString("ANJURAN", mPost.getAnjuran());
                bundle.putString("SATUAN", mPost.getJenis_satuan());
                as.setArguments(bundle);
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.replace(R.id.containerView, as);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost = mPosts.get(position);
                Ecommerce2Activity as = new Ecommerce2Activity();
                Bundle bundle = new Bundle();
                bundle.putString("ID_PRODUCT", mPost.getId_product());
                bundle.putString("NAMA", mPost.getProduct_name());
                bundle.putString("KETERANGAN", mPost.getKeterangan());
                bundle.putString("PICTURE", mPost.getPicture());
                bundle.putString("UKURAN", mPost.getUkuran());
                bundle.putString("HARGA", mPost.getSale_price());
                bundle.putString("DESKRIPSI", mPost.getDeskripsi());
                bundle.putString("ANJURAN", mPost.getAnjuran());
                bundle.putString("SATUAN", mPost.getJenis_satuan());
                as.setArguments(bundle);
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.replace(R.id.containerView, as);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewHolder.beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost = mPosts.get(position);
                Ecommerce2Activity as = new Ecommerce2Activity();
                Bundle bundle = new Bundle();
                bundle.putString("ID_PRODUCT", mPost.getId_product());
                bundle.putString("NAMA", mPost.getProduct_name());
                bundle.putString("KETERANGAN", mPost.getKeterangan());
                bundle.putString("PICTURE", mPost.getPicture());
                bundle.putString("UKURAN", mPost.getUkuran());
                bundle.putString("HARGA", mPost.getSale_price());
                bundle.putString("DESKRIPSI", mPost.getDeskripsi());
                bundle.putString("ANJURAN", mPost.getAnjuran());
                bundle.putString("SATUAN", mPost.getJenis_satuan());
                as.setArguments(bundle);
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.replace(R.id.containerView, as);
                ft.addToBackStack(null);
                ft.commit();
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
        Integer i;
        Button beli;
        //TextView date;
    }

}
