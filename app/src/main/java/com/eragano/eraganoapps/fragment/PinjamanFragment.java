package com.eragano.eraganoapps.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.asuransi.Asuransi1;
import com.eragano.eraganoapps.pinjaman.Pinjaman1;
import com.eragano.eraganoapps.pinjaman.rincian_pinjaman.RincianPinjaman;

public class PinjamanFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pinjaman, container, false);
        TextView txt1 = (TextView) v.findViewById(R.id.pengajuan);
        TextView txt2 = (TextView) v.findViewById(R.id.rincian);
        ImageView img1 = (ImageView) v.findViewById(R.id.pengajuan2);
        ImageView img2 = (ImageView) v.findViewById(R.id.rincian2);

        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment langkah1Fragment = new Pinjaman1();
                fragmentTransaction.replace(R.id.containerView, langkah1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment langkah1Fragment = new Pinjaman1();
                fragmentTransaction.replace(R.id.containerView, langkah1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rincianFragment = new RincianPinjaman();
                fragmentTransaction.replace(R.id.containerView, rincianFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rincianFragment = new RincianPinjaman();
                fragmentTransaction.replace(R.id.containerView, rincianFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        return v;


    }
}
