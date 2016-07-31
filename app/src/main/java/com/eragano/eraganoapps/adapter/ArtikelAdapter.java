package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.NativeArtikel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class ArtikelAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<NativeArtikel> mPosts;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private NativeArtikel mPost;
    private Activity mActivity;
    WebView wv;

    public ArtikelAdapter(Activity activity, List<NativeArtikel> posts) {
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
            convertView = mInflater.inflate(R.layout.artikel_layout, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.post_thumbnail);
            //wv = (WebView) convertView.findViewById(R.id.webView);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.post_title);
            mViewHolder.date = (TextView) convertView.findViewById(R.id.post_tanggal);
            mViewHolder.category = (TextView) convertView.findViewById(R.id.post_kategori);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mPost = mPosts.get(position);
        String kategori;
        if(mPost.getId_category().equals("1")){
            kategori = "Teknik Budidaya";
        }
        else if(mPost.getId_category().equals("2")){
            kategori = "Pengganggu Tanaman";
        }
        else if(mPost.getId_category().equals("3")){
            kategori = "Analisis Usaha";
        }
        else if(mPost.getId_category().equals("4")){
            kategori = "Benih";
        }
        else if(mPost.getId_category().equals("5")){
            kategori = "Pupuk";
        }
        else if(mPost.getId_category().equals("6")){
            kategori = "Pestisida";
        }
        else if(mPost.getId_category().equals("7")){
            kategori = "Fungisida";
        }
        else if(mPost.getId_category().equals("8")){
            kategori = "Alat-alat Pertanian";
        }
        else if(mPost.getId_category().equals("9")){
            kategori = "Hama";
        }
        else if(mPost.getId_category().equals("10")){
            kategori = "Gulma";
        }
        else if(mPost.getId_category().equals("11")){
            kategori = "Penyakit";
        }
        else if(mPost.getId_category().equals("12")){
            kategori = "Peluang Usaha";
        }
        else {
            kategori = "Harga Pasar";
        }
        String value = "http://103.236.201.252/blog/po-content/po-upload/"+mPost.getPicture();
        Picasso.with(mActivity)
                .load(value)
                .into(mViewHolder.thumbnail);

        mViewHolder.title.setText(Html.fromHtml(mPost.getTitle()));

        //Change format Date
        String date = mPost.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd mm yyyy");
        String newFormat = formatter.format(testDate);

        String tanggal = newFormat.substring(0, 2);
        String bulan = newFormat.substring(3, 5);
        String tahun = newFormat.substring(6);

        String final_tanggal, bln;
        if(bulan.equals("01")){
            bln = "Januari";
        }
        else if(bulan.equals("02")){
            bln="Februari";
        }
        else if(bulan.equals("03")){
            bln="Maret";
        }
        else if(bulan.equals("04")){
            bln="April";
        }
        else if(bulan.equals("05")){
            bln="Mei";
        }
        else if(bulan.equals("06")){
            bln="Juni";
        }
        else if(bulan.equals("07")){
            bln="Juli";
        }
        else if(bulan.equals("08")){
            bln="Agustus";
        }
        else if(bulan.equals("09")){
            bln="September";
        }
        else if(bulan.equals("10")){
            bln="Oktober";
        }
        else if(bulan.equals("11")){
            bln="November";
        }
        else {
            bln = "Desember";
        }
        final_tanggal = tanggal + " " + bln + " " + tahun;

        mViewHolder.date.setText(final_tanggal);
        mViewHolder.category.setText(kategori);

        return convertView;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView content;
        TextView date;
        TextView category;
    }
}
