package com.eragano.eraganoapps.fragment;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.informasi.CuacaActivity;
import com.eragano.eraganoapps.informasi.ArtikelActivity;
import com.eragano.eraganoapps.informasi.weather_forecast.WeatherActivity;

public class InformasiFragment extends android.support.v4.app.Fragment {
    private FragmentTabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.content);

        mTabHost.addTab(mTabHost.newTabSpec("cuaca").setIndicator("Cuaca"),
                WeatherActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("artikel").setIndicator("Artikel"),
                ArtikelActivity.class, null);
        final TabWidget widget = mTabHost.getTabWidget();
        View v ;
        for(int i = 0; i < widget.getChildCount(); i++) {
             v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView) v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_bar_background_selected);
        }
        final View[] v2 = new View[2];
        v2[1] = widget.getChildAt(1);
        v2[1].setBackgroundResource(R.drawable.tab_unpressed);


        final TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        textView.setTextColor(Color.parseColor("#62C16F"));
        final TextView text2View = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        text2View.setTextColor(Color.parseColor("#5a5a5a"));
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String tab = tabId;
                if (tab.equals("cuaca")) {
                    textView.setTextColor(Color.parseColor("#62C16F"));
                    text2View.setTextColor(Color.parseColor("#5a5a5a"));
                    v2[0] = widget.getChildAt(0);
                    v2[0].setBackgroundResource(R.drawable.tab_bar_background_selected);
                    v2[1] = widget.getChildAt(1);
                    v2[1].setBackgroundResource(R.drawable.tab_unpressed);
                } else {
                    textView.setTextColor(Color.parseColor("#5a5a5a"));
                    text2View.setTextColor(Color.parseColor("#62C16F"));
                    v2[0] = widget.getChildAt(0);
                    v2[0].setBackgroundResource(R.drawable.tab_unpressed);
                    v2[1] = widget.getChildAt(1);
                    v2[1].setBackgroundResource(R.drawable.tab_bar_background_selected);
                }

            }
        });
        return mTabHost;
    }
}
