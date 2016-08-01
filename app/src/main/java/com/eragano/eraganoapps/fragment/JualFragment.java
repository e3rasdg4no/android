package com.eragano.eraganoapps.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Produk;
import com.eragano.eraganoapps.adapter.ProdukAdapter;
import com.eragano.eraganoapps.jual.Jual2Activity;
import com.eragano.eraganoapps.jual.camera.JualCameraActivity;
import com.google.gson.Gson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JualFragment extends Fragment {
    private SmartTabLayout tabs= null;
    private ViewPager viewPager= null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_jual, container, false);

        tabs= (SmartTabLayout)view.findViewById(R.id.tabJual);
        viewPager= (ViewPager)view.findViewById(R.id.viewPagerJual);

        setupViewPagerAndTab();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view;
    }

    public void setupViewPagerAndTab(){
        FragmentPagerItemAdapter adapter= new FragmentPagerItemAdapter(getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getActivity())
            .add("HISTORI", FragmentHistoriJual.class)
            .add("PESANAN", FragmentPesananJual.class)
            .create());

        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);

        //
    }
}