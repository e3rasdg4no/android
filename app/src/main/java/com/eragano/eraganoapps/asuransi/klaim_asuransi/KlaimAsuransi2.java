package com.eragano.eraganoapps.asuransi.klaim_asuransi;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.fragment.HomeFragment;

public class KlaimAsuransi2  extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_klaim_asuransi2, container, false);

        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        Button btn = (Button) v.findViewById(R.id.lanjutkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.containerView, homeFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return v;
    }
}
