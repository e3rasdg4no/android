package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewHomeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<HomeItem> mPosts;
    private ViewHolder mViewHolder;
    private HomeItem mPost;
    private Activity mActivity;

    public NewHomeAdapter(Activity activity, List<HomeItem> posts) {
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
            convertView = mInflater.inflate(R.layout.home_layout, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.back = (ImageView) convertView.findViewById(R.id.latar);
            mViewHolder.front = (ImageView) convertView.findViewById(R.id.iconmenu);
            mViewHolder.head1 = (TextView) convertView.findViewById(R.id.h1);
            mViewHolder.head2 = (TextView) convertView.findViewById(R.id.h2);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);

        mViewHolder.head1.setText(mPost.getHeader1());
        mViewHolder.head2.setText(mPost.getHeader2());
        Picasso.with(mActivity).load(mPost.getBackimage()).into(mViewHolder.back);
        Picasso.with(mActivity).load(mPost.getIconimage()).into(mViewHolder.front);

        return convertView;
    }

    private static class ViewHolder {
        ImageView back;
        ImageView front;
        TextView head1;
        TextView head2;
    }
}