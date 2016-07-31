package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.eragano.eraganoapps.penampung.Produk;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class ProdukAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Produk> mPosts;
    private Activity mActivity;


    public ProdukAdapter(Activity activity, List<Produk> posts) {
        this.mPosts = posts;
        this.mActivity = activity;
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
        ViewHolder mViewHolder;

        if (convertView == null) {
            mInflater = (LayoutInflater) mActivity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.layout_produk, parent, false);
            mViewHolder = new ViewHolder();
            /*mViewHolder.produk = (ImageView) convertView.findViewById(R.id.gambar_produk);
            mViewHolder.namaProduk = (TextView) convertView.findViewById(R.id.nama_produk);
            mViewHolder.tanggalPanen = (TextView) convertView.findViewById(R.id.tanggal_panen);
            mViewHolder.stokHarga = (TextView) convertView.findViewById(R.id.stok_harga);
            mViewHolder.tanggalUp = (TextView) convertView.findViewById(R.id.tanggal_up);*/

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Produk mPost = mPosts.get(position);

        final String value = "http://103.236.201.252/android/uploads/"+mPost.getImage();
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

        String x = mPost.getHarga();
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

        String campur = mPost.getStok()+ " "+mPost.getSatuan()+" - RP "+x+" Per "+mPost.getSatuan();
        String nama = "";
        if(mPost.getNama().length()>11){
            nama = mPost.getNama().substring(0,8);
            nama = nama + "...";
        }
        else{
            nama = mPost.getNama();
        }
        mViewHolder.namaProduk.setText(nama);
        mViewHolder.tanggalPanen.setText(mPost.getTanggal_panen());
        mViewHolder.tanggalUp.setText(mPost.getTanggal());
        mViewHolder.stokHarga.setText(campur);

        return convertView;
    }

    private static class ViewHolder {
        ImageView produk;
        TextView namaProduk;
        TextView tanggalUp;
        TextView tanggalPanen;
        TextView stokHarga;
    }
}
