package com.eragano.eraganoapps.informasi.weather_forecast;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eragano.eraganoapps.R;

/**
 * Created by M Dimas Faizin on 7/13/2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    //Variabel Penampung
    private Activity context;
    private String[] hari;
    private String[] tanggal;
    private String[] bulan;
    private String[] max;
    private String[] min;
    private String[] lembab;
    private String[] angin;
    private String[] kondisi;
    private String[] picture;

    public CustomAdapter(Activity context, String[] hari, String[] max, String[] min, String[] lembab, String[] angin, String[] kondisi, String[] picture) {
        super(context, R.layout.custom_adapter, hari);
        this.context = context;
        this.hari = hari;
        this.max = max;
        this.min = min;
        this.lembab = lembab;
        this.angin = angin;
        this.kondisi = kondisi;
        this.picture = picture;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_adapter, null);
        TextView ttanggal = (TextView) rowView.findViewById(R.id.txt_tanggal);
        TextView tmax = (TextView) rowView.findViewById(R.id.txt_maks);
        TextView tmin = (TextView) rowView.findViewById(R.id.txt_min);
        TextView tlembab = (TextView) rowView.findViewById(R.id.txt_lembab);
        TextView tangin = (TextView) rowView.findViewById(R.id.txt_angin);
        TextView tkondisi = (TextView) rowView.findViewById(R.id.txt_kondisi);
        //ImageView pic = (ImageView) rowView.findViewById(R.id.gambar);
        TextView weatherIcon = (TextView) rowView.findViewById(R.id.txt_icon);
        weatherIcon.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf"));

        ttanggal.setText(hari[position]);
        tmax.setText("Maks : "+max[position]+"ºC");
        tmin.setText("Min  : "+min[position]+"ºC");
        tlembab.setText("Kelembaban : "+lembab[position]+"%");
        tangin.setText("Angin      : "+angin[position]+" mph");
        String kon="";
        switch (kondisi[position]){
            case "Rain":kon="Hujan";
                break;
            case "Clouds":kon="Berawan";
                break;
            case "Clear":kon="Cerah";
                break;
            case "Drizzle":kon="Gerimis";
                break;
            case "Thunderstorm":kon="Badai Petir";
                break;
            default:kon=kondisi[position];
                break;
        }

        tkondisi.setText(kon);

        //iv.setImageResource(resource[position]);
        //Picasso.with(context).load(picture[position]).into(pic);
        int id = Integer.parseInt(picture[position]) / 100;
        String icon = "";
        if( Integer.parseInt(picture[position]) == 800){
                icon = context.getString(R.string.weather_sunny);
        } else {
            switch(id) {
                case 2 : icon = context.getString(R.string.weather_thunder);
                    break;
                case 3 : icon = context.getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = context.getString(R.string.weather_foggy);
                    break;
                case 8 : icon = context.getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = context.getString(R.string.weather_snowy);
                    break;
                case 5 : icon = context.getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);

        return rowView;
    }
}
