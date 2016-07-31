package com.eragano.eraganoapps.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eragano.eraganoapps.R;

/**
 * Created by Deztu on 7/29/2016.
 */
public class FragmentPesananJual extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pesanan_jual, container, false);

        return view;
    }
}
