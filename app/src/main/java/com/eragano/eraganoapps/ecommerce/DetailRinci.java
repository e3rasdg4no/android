package com.eragano.eraganoapps.ecommerce;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eragano.eraganoapps.R;

public class DetailRinci  extends android.support.v4.app.Fragment {
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_detail_rinci, container, false);

        TextView no = (TextView) v.findViewById(R.id.noorder);
        TextView ls = (TextView) v.findViewById(R.id.list);
        TextView tgl = (TextView) v.findViewById(R.id.tanggal_pemesanan);
        TextView total = (TextView) v.findViewById(R.id.hargatotal);

        String nomor = getArguments().getString("NOMOR");
        String list = getArguments().getString("PRODUK");
        String tanggal = getArguments().getString("TANGGAL");
        String totalharga = getArguments().getString("TOTAL");

        no.setText("Nomor Order #"+nomor);
        ls.setText(list);
        tgl.setText(tanggal);
        total.setText("IDR "+totalharga);

        return v;
    }
}
