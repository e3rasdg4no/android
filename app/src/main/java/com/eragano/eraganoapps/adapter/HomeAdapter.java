package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eragano.eraganoapps.R;
import com.squareup.picasso.Picasso;

/**
 * Created by M Dimas Faizin on 3/17/2016.
 */
public class HomeAdapter extends ArrayAdapter<String> {
    //Variabel Penampung
    private Activity context;
    private Integer[] resource;
    private String[] objects;
    private Integer[] resource2;
    private String[] objects2;

    public HomeAdapter(Activity context, Integer[] resource, Integer[] resource2, String[] objects, String[] objects2) {
        super(context, R.layout.home_layout, objects);
        this.context = context;
        this.resource = resource;
        this.resource2 = resource2;
        this.objects = objects;
        this.objects2 = objects2;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.home_layout, null);
        TextView h1 = (TextView) rowView.findViewById(R.id.h1);
        TextView h2 = (TextView) rowView.findViewById(R.id.h2);
        ImageView latar = (ImageView) rowView.findViewById(R.id.latar);
        ImageView icon = (ImageView) rowView.findViewById(R.id.iconmenu);

        h1.setText(objects[position]);
        h2.setText(objects2[position]);
        //iv.setImageResource(resource[position]);
        Picasso.with(context).load(resource[position]).into(latar);
        Picasso.with(context).load(resource2[position]).into(icon);


    return rowView;
    }
}
