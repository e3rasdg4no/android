package com.eragano.eraganoapps.informasi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.informasi.weather.CityPreference;
import com.eragano.eraganoapps.informasi.weather.WeatherFragment;

public class CuacaActivity extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cuaca, container, false);
        final EditText cuaca = (EditText) v.findViewById(R.id.txtcuaca);
        Button btn_cuaca = (Button) v.findViewById(R.id.btncuaca);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.informasipertanian, new WeatherFragment())
                    .commit();
        }
        btn_cuaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCity(cuaca.getText().toString());
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cuaca.getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });
        return v;
    }
    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.informasipertanian);
        wf.changeCity(city);
        new CityPreference(getActivity()).setCity(city);
    }

}
