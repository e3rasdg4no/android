package com.eragano.eraganoapps.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.AdapterJadwal;
import com.eragano.eraganoapps.adapter.HariAdapter;
import com.eragano.eraganoapps.adapter.JadwalAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JadwalFragment extends android.support.v4.app.Fragment {

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private Map<Date, List<Booking>> bookings = new HashMap<>();
    List<HariAdapter> posts = new ArrayList<>();
    List<JadwalAdapter> posts2 = new ArrayList<>();
    private AdapterJadwal mAdapter;
    List<String> array;
    List<String> array2;
    ListView bookingsListView;
    public static String URL = "http://103.236.201.252/android/ambil_hari.php";
    public static String URL2 = "http://103.236.201.252/android/jadwal.php";
    CompactCalendarView compactCalendarView;
    SharedPreferences sp;
    String web;
    ProgressBar pb;
    Exception exceptionToBeThrown;

    public class Booking {
        private String title;
        private Date date;

        public Booking(String title, Date date) {
            this.title = title;
            this.date = date;
        }


        @Override
        public String toString() {
            return "Booking{" +
                    "title='" + title + '\'' +
                    ", date=" + date +
                    '}';
        }
    }
        String tanggal_tanam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);

       //ASK USER
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bulan Mulai Tanam");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.tanya_jadwal, (ViewGroup) getView(), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tanggal_tanam = input.getText().toString();
                new SimpleTask().execute(web);
                //Log.d("TANGGAL", tanggal_tanam);
            }
        });
        builder.show();


        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        web = URL+"?id="+sp.getString("ID_JADWAL",null);
        //pb = (ProgressBar) v.findViewById(R.id.progressBar8);
        final List<String> mutableBookings = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mutableBookings);
        array = new ArrayList<String>();
        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);
        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setLocale(Locale.ENGLISH);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        bookingsListView = (ListView) v.findViewById(R.id.listView);
        //bookingsListView.setAdapter(adapter);

        final TextView txtBulan = (TextView) v.findViewById(R.id.bulan);
        TextView txtNext = (TextView) v.findViewById(R.id.next);
        TextView txtPrev = (TextView) v.findViewById(R.id.prev);
        txtBulan.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //List<Booking> bookingsFromMap = bookings.get(dateClicked);
                //dateClicked = new Date("dd");
                for (String s : array) {
                    if (s.equals(dateClicked.toString())) {
                        String data = dateClicked.toString().substring(8, 10);
                        String data2 = dateClicked.toString().substring(4, 7);
                        String bulan;
                        if (data.substring(0, 1).equals("0")) {
                            data = data.substring(1, 2);
                        }
                        if (data2.equals("Jan")) {
                            bulan = "0";
                        } else if (data2.equals("Feb")) {
                            bulan = "1";
                        } else if (data2.equals("Mar")) {
                            bulan = "2";
                        } else if (data2.equals("Apr")) {
                            bulan = "3";
                        } else if (data2.equals("May") || data2.equals("Mei")) {
                            bulan = "4";
                        } else if (data2.equals("Jun")) {
                            bulan = "5";
                        } else if (data2.equals("Jul")) {
                            bulan = "6";
                        } else if (data2.equals("Aug") || data2.equals("Agu")) {
                            bulan = "7";
                        } else if (data2.equals("Sep")) {
                            bulan = "8";
                        } else if (data2.equals("Oct") || data2.equals("Okt")) {
                            bulan = "9";
                        } else if (data2.equals("Nov")) {
                            bulan = "10";
                        } else {
                            bulan = "11";
                        }
                        bulan = String.valueOf(Integer.parseInt(bulan) - (Integer.parseInt(tanggal_tanam)-1));
                        if (bulan.equals("2")) {
                            data = String.valueOf(Integer.parseInt(data) + 31);
                        }
                        String web = URL2 + "?id=" + sp.getString("ID_JADWAL", null) + "&hari=" + data + "&bulan=" + bulan;
                        new SimpleTask2().execute(web);
                        break;
                    }
                }
                /*Log.d("MainActivity", "inside onclick " + data);
                if (bookingsFromMap != null) {
                    Log.d("MainActivity", bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Booking booking : bookingsFromMap) {
                        mutableBookings.add(booking.title);
                    }
                    // below will remove events
                    // compactCalendarView.removeEvent(new CalendarDayEvent(dateClicked.getTime(), Color.argb(255, 169, 68, 65)), true);
                    adapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtBulan.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        txtPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });
        //new SimpleTask().execute(web);

        //addEvents(compactCalendarView, -1);
        //addEvents(compactCalendarView, Calendar.MARCH);
        //addEvents(compactCalendarView, Calendar.APRIL);
        //compactCalendarView.invalidate();
        //
        return v;
    }


    private List<Booking> createBookings() {
        return Arrays.asList(
                new Booking("Menyiram Hingga Tanah Basah tidak sampai menggenang - 08.00 Pagi", currentCalender.getTime()),
                new Booking("Memberi Pupuk Organik Dengan cara disebar - 09.00 Pagi", currentCalender.getTime()),
                new Booking("Menyiram Hingga Tanaman Basah Tidak perlu terlalu banyak - 16.00 Sore ", currentCalender.getTime()));
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    private class SimpleTask extends AsyncTask<String, Void, String> {
        String result = "";
        ProgressDialog dialog;
        //ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar2);
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "Silahkan tunggu", "Data sedang di proses", false, false);
            //pb.setVisibility(ProgressBar.VISIBLE);
        }

        protected String doInBackground(String... urls) {

            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
                HttpConnectionParams.setSoTimeout(httpParams, 60000);
                client = new DefaultHttpClient(httpParams);
                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (ClientProtocolException e) {
                exceptionToBeThrown = e;
            } catch (IOException e) {
                exceptionToBeThrown = e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            //pb.setVisibility(ProgressBar.INVISIBLE);
            dialog.dismiss();
            if (jsonString.equals("null")) {
                //txtkosong.setVisibility(TextView.VISIBLE);
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
                showData(jsonString);
            }
        }
    }

    private void showData(String jsonString) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts = Arrays.asList(gson.fromJson(jsonString, HariAdapter[].class));
        addEvents(compactCalendarView, Integer.parseInt(tanggal_tanam));

    }

    private void addEvents(CompactCalendarView compactCalendarView, int month) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();

        for (int i = 0; i < posts.size(); i++) {
            currentCalender.setTime(firstDayOfMonth);
            if(Integer.parseInt(posts.get(i).getBulan())>month){
                month=month+1;
            }
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            currentCalender.add(Calendar.DATE, (Integer.parseInt(posts.get(i).getHari_ke())-1));
            setToMidnight(currentCalender);
            compactCalendarView.addEvent(new CalendarDayEvent(currentCalender.getTimeInMillis(), Color.argb(255, 255, 255, 255)), false);
            //bookings.put(currentCalender.getTime(), createBookings());
            Log.d("TES",currentCalender.getTime().toString());
            array.add(currentCalender.getTime().toString());
            //currentCalender.add(Calendar.DATE, 4);
            //setToMidnight(currentCalender);
            //compactCalendarView.addEvent(new CalendarDayEvent(currentCalender.getTimeInMillis(),  Color.argb(255, 169, 68, 65)), false);
            //bookings.put(currentCalender.getTime(), createBookings());
        }
    }

    private class SimpleTask2 extends AsyncTask<String, Void, String> {
        String result = "";
        ProgressDialog dialog;

        //ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar2);
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "Silahkan tunggu", "Data sedang di proses", false, false);
            //pb.setVisibility(ProgressBar.VISIBLE);
        }

        protected String doInBackground(String... urls) {

            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
                HttpConnectionParams.setSoTimeout(httpParams, 60000);
                client = new DefaultHttpClient(httpParams);
                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (ClientProtocolException e) {
              exceptionToBeThrown = e;
            } catch (IOException e) {
                exceptionToBeThrown = e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString2) {
                    dialog.dismiss();
                    //pb.setVisibility(ProgressBar.INVISIBLE);
                  if (jsonString2.equals("null")) {

                    //txtkosong.setVisibility(TextView.VISIBLE);
                    // Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
              } else if (exceptionToBeThrown != null) {
                Toast.makeText(getActivity(), "Koneksi Internet Anda Bermasalah", Toast.LENGTH_SHORT).show();
            } else {
            showData2(jsonString2);
            }

        }
    }

    private void showData2(String jsonString2) {
        Gson gson = new Gson();
        //List<Post> posts = new ArrayList<>();
        posts2 = Arrays.asList(gson.fromJson(jsonString2, JadwalAdapter[].class));
        //List<Post> post = posts.;
        mAdapter = new AdapterJadwal(getActivity(), posts2);
        bookingsListView.setAdapter(mAdapter);
        bookingsListView.deferNotifyDataSetChanged();
    }
}
