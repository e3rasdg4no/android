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

import com.bumptech.glide.Glide;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Produk;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class ProdukAdapter extends BaseAdapter {
    private List<Produk> produkList= null;
    private Context context= null;

    public ProdukAdapter(Context context, List<Produk> produkList) {
        this.produkList = produkList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produkList.size();
    }

    @Override
    public Object getItem(int position) {
        return produkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder= new ViewHolder();

            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(R.layout.layout_produk, null);

            viewHolder.image= (ImageView)convertView.findViewById(R.id.imageInHistori);
            viewHolder.judul= (TextView)convertView.findViewById(R.id.title_post);
            viewHolder.jumlahJual= (TextView)convertView.findViewById(R.id.jumlahJual);
            viewHolder.jumlahPesanan= (TextView)convertView.findViewById(R.id.jumlahPesanan);
            viewHolder.jumlahTerjual= (TextView)convertView.findViewById(R.id.jumlahTerjual);
            viewHolder.idJual= (TextView)convertView.findViewById(R.id.idJual);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder)convertView.getTag();
        }

        Produk produk= produkList.get(position);

        String imagePath= "http://103.236.201.252/android/uploads/" + produk.getImage();
        Glide.with(context)
                .load(imagePath)
                .centerCrop()
                .into(viewHolder.image);
        viewHolder.judul.setText(produk.getNama());
        viewHolder.jumlahJual.setText(produk.getStok());
        viewHolder.jumlahPesanan.setText(produk.getJumlahPesanan());
        viewHolder.jumlahTerjual.setText(produk.getJumlahTerjual());
        viewHolder.idJual.setText(produk.getId_jual());

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        TextView judul;
        TextView jumlahJual;
        TextView jumlahPesanan;
        TextView jumlahTerjual;
        TextView idJual;
    }
}
