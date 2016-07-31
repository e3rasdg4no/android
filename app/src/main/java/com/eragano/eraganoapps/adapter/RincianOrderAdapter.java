package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.RincianOrderVariable;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/15/2016.
 */
public class RincianOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<RincianOrderVariable> mPosts;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private RincianOrderVariable mPost;
    private Activity mActivity;
    WebView wv;

    public RincianOrderAdapter(Activity activity, List<RincianOrderVariable> posts) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_order, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.dikirim = (ImageView) convertView.findViewById(R.id.img_dikirim);
            mViewHolder.imgstatus = (ImageView) convertView.findViewById(R.id.imagestatus);
            mViewHolder.idpenjualan = (TextView) convertView.findViewById(R.id.txtnomororder);
            mViewHolder.tanggalpemesanan = (TextView) convertView.findViewById(R.id.txttanggalorder);
            mViewHolder.totalbayar = (TextView) convertView.findViewById(R.id.txttotalharga);
            mViewHolder.status= (TextView) convertView.findViewById(R.id.txtstatus);
            mViewHolder.status2= (TextView) convertView.findViewById(R.id.textView161);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        mViewHolder.idpenjualan.setText("Nomor #"+mPost.getId_penjualan());
        mViewHolder.tanggalpemesanan.setText(mPost.getTanggal_pemesanan());
        String x = mPost.getTotal_bayar();
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
        mViewHolder.totalbayar.setText("IDR "+x);
        mViewHolder.status.setText(mPost.getStatus_terakhir());
        //mViewHolder.content.setText(mPost.getPicture());
        String status = mPost.getDikirim();
        if(status.equals("Sedang Dikirim")){
            mViewHolder.dikirim.setImageResource(R.drawable.bulathijau);
            mViewHolder.status2.setText("Sedang Dikirim");
        }
        else{
            mViewHolder.dikirim.setImageResource(R.drawable.bulatmerah);
            mViewHolder.status2.setText("Belum Dikirim");
        }

        String status2 = mPost.getStatus_terakhir();
        if(status2.equals("Sudah Diterima")){
            mViewHolder.imgstatus.setImageResource(R.drawable.bulathijau);
        }
        else{
            mViewHolder.imgstatus.setImageResource(R.drawable.bulatmerah);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView dikirim;
        ImageView imgstatus;
        TextView idpenjualan;
        TextView tanggalpemesanan;
        TextView totalbayar;
        TextView status;
        TextView status2;

    }
}
