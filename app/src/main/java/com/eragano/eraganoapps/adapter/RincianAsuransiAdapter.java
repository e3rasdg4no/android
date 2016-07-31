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
import com.eragano.eraganoapps.penampung.RincianAsuransi;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/6/2016.
 */
public class RincianAsuransiAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<RincianAsuransi> mPosts;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private RincianAsuransi mPost;
    private Activity mActivity;
    WebView wv;

    public RincianAsuransiAdapter(Activity activity, List<RincianAsuransi> posts) {
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
            convertView = mInflater.inflate(R.layout.layout_rincian_asuransi, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.imagestatus);
            //wv = (WebView) convertView.findViewById(R.id.webView);
            mViewHolder.tanggal = (TextView) convertView.findViewById(R.id.txttanggal);
            mViewHolder.idklaim = (TextView) convertView.findViewById(R.id.txtklaim);
            mViewHolder.statusklaim = (TextView) convertView.findViewById(R.id.txtstatus);
            //mViewHolder.content = (TextView) convertView.findViewById(R.id.post_author);
            /// / mViewHolder.date = (TextView) convertView.findViewById(R.id.post_date);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        mViewHolder.idklaim.setText("KLAIM ASURANSI #"+mPost.getId_pengajuan());
        mViewHolder.tanggal.setText(mPost.getTanggal_pengajuan());
        mViewHolder.statusklaim.setText(mPost.getStatus());
        //mViewHolder.content.setText(mPost.getPicture());
        String status = mPost.getStatus();
        if(status.equals("Belum Disetujui")){
            mViewHolder.thumbnail.setImageResource(R.drawable.bulatkuning);
        }
        else if(status.equals("Disetujui")){
            mViewHolder.thumbnail.setImageResource(R.drawable.bulathijau);
        }
        else{
            mViewHolder.thumbnail.setImageResource(R.drawable.bulatmerah);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView tanggal;
        TextView idklaim;
        TextView statusklaim;
    }
}
