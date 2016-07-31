package com.eragano.eraganoapps.fragment;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.informasi.ArtikelActivity;
import com.eragano.eraganoapps.informasi.CuacaActivity;
import com.eragano.eraganoapps.performa.InputKinerja;

public class KinerjaFragment extends android.support.v4.app.Fragment {
    private FragmentTabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.content2);

        mTabHost.addTab(mTabHost.newTabSpec("input").setIndicator("Input Kinerja"),
                InputKinerja.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("lihat").setIndicator("Lihat Kinerja"),
                ArtikelActivity.class, null);
        TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        TextView text2View = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        text2View.setTextColor(Color.parseColor("#000000"));
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#62C16F"));
        mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String tab = tabId;
                if (tab.equals("input")) {
                    TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    TextView text2View = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    text2View.setTextColor(Color.parseColor("#000000"));
                    mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#62C16F"));
                    mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    TextView text2View = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    text2View.setTextColor(Color.parseColor("#000000"));
                    mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#62C16F"));
                    mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        return mTabHost;
    }
}
