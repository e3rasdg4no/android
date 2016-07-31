package com.eragano.eraganoapps.informasi.weather_forecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eragano.eraganoapps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherActivity extends android.support.v4.app.Fragment {


    ListView list_cuaca;
    EditText edit_cari;
    Button cari;

    String link = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String link_add = "&cnt=7&units=metric&mode=json&appid=8bcb43746557a439be3864c930c693bd";
    String link_current = "http://api.openweathermap.org/data/2.5/weather?q=";
    String link_currentadd = "&units=metric&appid=8bcb43746557a439be3864c930c693bd";
    String triman;

    //Hari ini
    TextView txthariini, txtmaks, txtmin, txttemp, txtdaerah, txtlembab, txttekanan, txtangin, txticon, t, t2, t3;

    View v;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_weather, container, false);
        sp = getActivity().getSharedPreferences("Cuaca", Context.MODE_PRIVATE);
        editor = sp.edit();

        list_cuaca = (ListView) v.findViewById(R.id.lv_cuaca);
        //ADD HEADER FOOTER
        LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater2.inflate(R.layout.header_cuaca, null);
        View footer = inflater2.inflate(R.layout.footer_cuaca, null);

        list_cuaca.addHeaderView(header);
        list_cuaca.addFooterView(footer);

        edit_cari = (EditText) v.findViewById(R.id.et_cari);
        cari = (Button) v.findViewById(R.id.btn_cari);

        txthariini = (TextView) header.findViewById(R.id.txt_hariini);
        txtmaks = (TextView) header.findViewById(R.id.txt_maxtemp);
        txtmin = (TextView) header.findViewById(R.id.txt_mintemp);
        txttemp = (TextView) header.findViewById(R.id.txt_nowtemp);
        txtdaerah = (TextView) header.findViewById(R.id.txt_daerah);
        txtlembab = (TextView) header.findViewById(R.id.txt_lembab);
        txttekanan = (TextView) header.findViewById(R.id.txt_tekanan);
        txtangin = (TextView) header.findViewById(R.id.txt_angin);
        txticon = (TextView) header.findViewById(R.id.txt_icon);
        txticon.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"));

        t = (TextView) header.findViewById(R.id.textView6);
        t2 = (TextView) header.findViewById(R.id.textView7);
        t3 = (TextView) header.findViewById(R.id.textView8);

        txthariini.setVisibility(TextView.INVISIBLE);
        txtmaks.setVisibility(TextView.INVISIBLE);
        txtmin.setVisibility(TextView.INVISIBLE);
        txttemp.setVisibility(TextView.INVISIBLE);
        txtdaerah.setVisibility(TextView.INVISIBLE);
        txtlembab.setVisibility(TextView.INVISIBLE);
        txttekanan.setVisibility(TextView.INVISIBLE);
        txtangin.setVisibility(TextView.INVISIBLE);
        txticon.setVisibility(TextView.INVISIBLE);

        t.setVisibility(TextView.INVISIBLE);
        t2.setVisibility(TextView.INVISIBLE);
        t3.setVisibility(TextView.INVISIBLE);
        //v.setVisibility(View.INVISIBLE);
        String key = sp.getString("KATA_KUNCI", "kosong");
        if(key.equals("kosong")){
            Toast.makeText(getActivity(),"Masukkan Kota", Toast.LENGTH_SHORT).show();
        }
        else{
            ambil_cuaca(key);
        }

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_cari.getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                triman = edit_cari.getText().toString().replace(" ", "%20");
                ambil_cuaca(triman);

            }
        });
        return v;
    }



    public void ambil_cuaca(final String katakunci){

        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Data sedang di proses");
        dialog.setCancelable(false);
        dialog.show();

        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //FUNGSI CURRENT CONDITION
        String link2 = link_current+katakunci+link_currentadd;
        StringRequest strRequest = new StringRequest(link2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject objectAwal = null;
                try {
                    objectAwal = new JSONObject(response);
                    //ambil nama daerah dan negara
                    JSONObject daerah = objectAwal.getJSONObject("sys");
                    txtdaerah.setText(objectAwal.getString("name")+", "+daerah.getString("country"));

                    JSONArray cuaca = objectAwal.getJSONArray("weather");
                    JSONObject cuacafix = cuaca.getJSONObject(0);
                    String kon = "";
                    switch (cuacafix.getString("main")){
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
                        default:kon=cuacafix.getString("name");
                            break;
                    }
                    txthariini.setText("Saat ini "+kon);
                    JSONObject main = objectAwal.getJSONObject("main");
                    txtmaks.setText(String.format("Maks : "+"%.1f", main.getDouble("temp_max"))+"ºC");
                    txtmin.setText(String.format("Min : "+"%.1f", main.getDouble("temp_min"))+"ºC");
                    txttemp.setText(main.getString("temp")+"ºC");
                    txtlembab.setText(": "+main.getString("humidity")+"%");
                    txttekanan.setText(": "+main.getString("pressure")+" hPa");

                    JSONObject wind = objectAwal.getJSONObject("wind");
                    txtangin.setText(": "+wind.getString("speed")+" mph");

                    //Set Picture
                    int actualId = cuacafix.getInt("id");

                    long sunrise = daerah.getLong("sunrise") * 1000;
                    long sunset = daerah.getLong("sunset") * 1000;
                    int id = actualId / 100;
                    String icon = "";
                    if(actualId == 800){
                        long currentTime = new Date().getTime();
                        if(currentTime>=sunrise && currentTime<sunset) {
                            icon = getString(R.string.weather_sunny);
                        } else {
                            icon = getString(R.string.weather_clear_night);
                        }
                    } else {
                        switch(id) {
                            case 2 : icon = getString(R.string.weather_thunder);
                                break;
                            case 3 : icon = getString(R.string.weather_drizzle);
                                break;
                            case 7 : icon = getString(R.string.weather_foggy);
                                break;
                            case 8 : icon = getString(R.string.weather_cloudy);
                                break;
                            case 6 : icon = getString(R.string.weather_snowy);
                                break;
                            case 5 : icon = getString(R.string.weather_rainy);
                                break;
                        }
                    }
                    txticon.setText(icon);
                    txthariini.setVisibility(TextView.VISIBLE);
                    txtmaks.setVisibility(TextView.VISIBLE);
                    txtmin.setVisibility(TextView.VISIBLE);
                    txttemp.setVisibility(TextView.VISIBLE);
                    txtdaerah.setVisibility(TextView.VISIBLE);
                    txtlembab.setVisibility(TextView.VISIBLE);
                    txttekanan.setVisibility(TextView.VISIBLE);
                    txtangin.setVisibility(TextView.VISIBLE);
                    txticon.setVisibility(TextView.VISIBLE);
                    t.setVisibility(TextView.VISIBLE);
                    t2.setVisibility(TextView.VISIBLE);
                    t3.setVisibility(TextView.VISIBLE);
                    edit_cari.setText("");
                    //v.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Tidak dapat menemukan kota", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kesalahan koneksi, periksa internet anda", Toast.LENGTH_SHORT).show();
            }
        });


        //FUNGSI FORECAST
        String link_fix = link+katakunci+link_add;
        StringRequest stringRequest = new StringRequest(link_fix, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objectAwal = new JSONObject(response);
                    JSONArray arrayAwal = objectAwal.getJSONArray("list");

                    String[] hari = new String[arrayAwal.length()];

                    String[] max = new String[arrayAwal.length()];
                    String[] min = new String[arrayAwal.length()];
                    String[] lembab = new String[arrayAwal.length()];
                    String[] angin = new String[arrayAwal.length()];
                    String[] kondisi = new String[arrayAwal.length()];
                    String[] picture = new String[arrayAwal.length()];


                    for (int i = 0; i < arrayAwal.length(); i++) {
                        JSONObject objectCuaca = arrayAwal.getJSONObject(i);
                        //get date
                        c.add(Calendar.DATE,i);
                        hari[i] = df.format(c.getTime());
                        c.setTime(new Date());

                        JSONObject objectTemp = objectCuaca.getJSONObject("temp");
                        max[i] = objectTemp.getString("max");
                        min[i] = objectTemp.getString("min");
                        lembab[i] = objectCuaca.getString("humidity");
                        angin[i] = objectCuaca.getString("speed");
                        JSONArray weather = objectCuaca.getJSONArray("weather");
                        JSONObject weather2 = weather.getJSONObject(0);
                        kondisi[i] = weather2.getString("main");
                        picture[i] = weather2.getString("id");

                    }
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,hari);
                    CustomAdapter adapter = new CustomAdapter(getActivity(), hari, max, min, lembab, angin, kondisi, picture);
                    list_cuaca.setAdapter(adapter);
                    editor.putString("KATA_KUNCI", katakunci);
                    editor.commit();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Tidak dapat menemukan kota", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kesalahan koneksi, periksa internet anda", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;//30 seconds timeout
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strRequest);
        requestQueue.add(stringRequest);
    }

    }


