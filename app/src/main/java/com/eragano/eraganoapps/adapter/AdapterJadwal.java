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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/18/2016.
 */
public class AdapterJadwal extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<JadwalAdapter> mPosts;
    private ViewHolder mViewHolder;

    private JadwalAdapter mPost;
    private Activity mActivity;


    public AdapterJadwal(Activity activity, List<JadwalAdapter> posts) {
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
            convertView = mInflater.inflate(R.layout.layout_jadwal, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.judul = (TextView) convertView.findViewById(R.id.textView166);
            mViewHolder.langkah = (TextView) convertView.findViewById(R.id.textView165);
            mViewHolder.jam = (TextView) convertView.findViewById(R.id.textView163);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);
        mViewHolder.judul.setText(mPost.getJudul_langkah());
        mViewHolder.langkah.setText(mPost.getLangkah());
        String jamawal="";
        String jamakhir="";
        if(mPost.getJam_mulai().length()<2){
            jamawal="0"+mPost.getJam_mulai()+".00";
        }
        else{
            jamawal=mPost.getJam_mulai()+".00";
        }
        if(mPost.getJam_akhir().length()<2){
            jamakhir="0"+mPost.getJam_akhir()+".00";
        }
        else{
            jamakhir=mPost.getJam_akhir()+".00";
        }
        mViewHolder.jam.setText(jamawal+"-"+jamakhir);
        return convertView;
    }

    private static class ViewHolder {
        ImageView produk;
        TextView langkah;
        TextView judul;
        TextView jam;
    }
}
