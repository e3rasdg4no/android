package com.eragano.eraganoapps.informasi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eragano.eraganoapps.Chat.EntryActivity;
import com.eragano.eraganoapps.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Artikel2Activity extends AppCompatActivity {

    ImageButton back, bantuan;
    ListView list_artikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel2);

        back = (ImageButton) findViewById(R.id.back);
        bantuan = (ImageButton) findViewById(R.id.bantu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ArtikelActivity.dismisdialog();
            }
        });
        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Artikel2Activity.this, EntryActivity.class);
                startActivity(intent);
            }
        });
        list_artikel = (ListView) findViewById(R.id.list_deskripsi_artikel);

        LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater2.inflate(R.layout.header_deskripsi_artikel, null);
        View header2 = inflater2.inflate(R.layout.header_isi_artikel, null);
        list_artikel.addHeaderView(header);
        list_artikel.addHeaderView(header2);

        //DEFINISI ISI HEADER
        TextView date = (TextView) header.findViewById(R.id.txt_date);
        TextView judul = (TextView) header.findViewById(R.id.txt_judul);
        TextView kategori = (TextView) header.findViewById(R.id.txt_kategori);
        ImageView image = (ImageView) header.findViewById(R.id.img_artikel);

        String test[] = {" "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Artikel2Activity.this,android.R.layout.simple_list_item_1,test);
        list_artikel.setAdapter(adapter);

        Intent i = getIntent();

        String jdl = i.getStringExtra("JUDUL");
        String gbr = i.getStringExtra("GAMBAR");
        String wkt = i.getStringExtra("WAKTU");
        String dt = i.getStringExtra("DATE");
        String idkategori = i.getStringExtra("ID_KATEGORI");
        String posting2 = i.getStringExtra("ISI");
        String value = "http://103.236.201.252/blog/po-content/po-upload/"+gbr;

        String ktg;
        if(idkategori.equals("1")){
            ktg = "Teknik Budidaya";
        }
        else if(idkategori.equals("2")){
            ktg = "Pengganggu Tanaman";
        }
        else if(idkategori.equals("3")){
            ktg = "Analisis Usaha";
        }
        else if(idkategori.equals("4")){
            ktg = "Benih";
        }
        else if(idkategori.equals("5")){
            ktg = "Pupuk";
        }
        else if(idkategori.equals("6")){
            ktg = "Pestisida";
        }
        else if(idkategori.equals("7")){
            ktg = "Fungisida";
        }
        else if(idkategori.equals("8")){
            ktg = "Alat-alat Pertanian";
        }
        else if(idkategori.equals("9")){
            ktg = "Hama";
        }
        else if(idkategori.equals("10")){
            ktg = "Gulma";
        }
        else if(idkategori.equals("11")){
            ktg = "Penyakit";
        }
        else if(idkategori.equals("12")){
            ktg = "Peluang Usaha";
        }
        else {
            ktg = "Harga Pasar";
        }

        judul.setText(jdl);
        Picasso.with(Artikel2Activity.this).load(value).into(image);
        String waktufix = wkt.substring(0,5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(dt);
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
        String final_date = waktufix+" WIB - "+ final_tanggal;
        date.setText(final_date);
        kategori.setText(ktg);



        Spanned hasil = Html.fromHtml(posting2);
        Spanned hasil2 = Html.fromHtml(hasil.toString());
        TextView txt = (TextView) header2.findViewById(R.id.textView170);
        txt.setText(hasil2);
        //WORK WITH WEBVIEW
        /*String strBody = "<html>"
                + "<head>"
                + "     <style type=\"text/css\">"
                + "     #text"
                + "         {   font-size:32;"
                + "             text-align: center;"
                + "         }"
                + "     </style>"
                + ""
                + "</head>"
                + "<body dir=\"rtl\" id=\"text\">"
                + hasil
                + " </body></html>  ";

        String htmlText = " %s ";
        String coba = setWebViewWithImageFit(hasil.toString());
        WebView WebView1 = (WebView) header2.findViewById(R.id.webisi);
        WebView1.loadData(String.format(htmlText, coba), "text/html; charset=utf-8", "UTF-8");
        WebView1.setInitialScale(3);
        WebView1.getSettings().setJavaScriptEnabled(true);
        WebView1.getSettings().setLoadWithOverviewMode(true);
        WebView1.getSettings().setUseWideViewPort(true);
        WebView1.getSettings().setDefaultFontSize(40);
        WebView1.setVerticalScrollBarEnabled(false);
        */
    }

    /*public String setWebViewWithImageFit(String content) {

        // content is the content of the HTML or XML.
        String stringToAdd = "width=\"100%\" ";

        // Create a StringBuilder to insert string in the middle of content.
        StringBuilder sb = new StringBuilder(content);

        int i = 0;
        int cont = 0;

        // Check for the "src" substring, if it exists, take the index where
        // it appears and insert the stringToAdd there, then increment a counter
        // because the string gets altered and you should sum the length of the inserted substring
        while (i != -1) {
            i = content.indexOf("src", i + 1);
            if (i != -1) sb.insert(i + (cont * stringToAdd.length()), stringToAdd);
            ++cont;
        }
        return sb.toString();
    }*/

    @Override
    public void onBackPressed() {
        finish();
        ArtikelActivity.dismisdialog();
    }
}
