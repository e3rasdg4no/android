package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.User;

import java.util.List;

/**
 * Created by M Dimas Faizin on 4/14/2016.
 */
public class UserAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<User> mPosts;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private User mPost;
    private Activity mActivity;
    WebView wv;

    public UserAdapter(Activity activity, List<User> posts) {
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
            convertView = mInflater.inflate(R.layout.layout_rincian_pinjaman, parent, false);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);
        return convertView;
    }

    private static class ViewHolder {
    }
}
