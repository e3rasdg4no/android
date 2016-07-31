package com.eragano.eraganoapps.fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.asuransi.Asuransi1;
import com.eragano.eraganoapps.asuransi.klaim_asuransi.KlaimAsuransi1;
import com.eragano.eraganoapps.asuransi.rincian_klaim.RincianActivity;

public class AsuransiFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asuransi, container, false);

        TextView tv = (TextView) v.findViewById(R.id.pendaftaran);
        ImageView tv2 = (ImageView) v.findViewById(R.id.imgdaftar);
        ImageView tv3 = (ImageView) v.findViewById(R.id.imgklaim);
        TextView tv4 = (TextView) v.findViewById(R.id.klaim2);
        TextView tv5 = (TextView) v.findViewById(R.id.rincianklaim);
        ImageView tv6 = (ImageView) v.findViewById(R.id.imageklaim);

        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment langkah1Fragment = new Asuransi1();
                fragmentTransaction.replace(R.id.containerView, langkah1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment langkah1Fragment = new Asuransi1();
                fragmentTransaction.replace(R.id.containerView, langkah1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment klaim1Fragment = new KlaimAsuransi1();
                fragmentTransaction.replace(R.id.containerView, klaim1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment klaim1Fragment = new KlaimAsuransi1();
                fragmentTransaction.replace(R.id.containerView, klaim1Fragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rincianFragment = new RincianActivity();
                fragmentTransaction.replace(R.id.containerView, rincianFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rincianFragment = new RincianActivity();
                fragmentTransaction.replace(R.id.containerView, rincianFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }
}
