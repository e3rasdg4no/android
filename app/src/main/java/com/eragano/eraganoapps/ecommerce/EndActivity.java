package com.eragano.eraganoapps.ecommerce;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.fragment.HomeFragment;

public class EndActivity extends android.support.v4.app.Fragment {
    Button rincian,back;
    String nama,jumlah;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_end, container, false);
        nama = getArguments().getString("NAMA");
        jumlah = getArguments().getString("JUMLAH");
        TextView txt = (TextView) v.findViewById(R.id.textView157);
        String x = jumlah;
        if(x.length()==4){
            x = x.substring(0,1)+"."+x.substring(1);
        }
        else if(x.length()==5){
            x = x.substring(0,2)+"."+x.substring(2);
        }
        else if(x.length()==6){
            x = x.substring(0,3)+"."+x.substring(3);
        }
        else if(x.length()==7){
            x = x.substring(0,1)+"."+x.substring(1,4)+"."+x.substring(4);
        }
        else if(x.length()==8){
            x = x.substring(0,2)+"."+x.substring(2,5)+"."+x.substring(5);
        }
        else if(x.length()==9){
            x = x.substring(0,3)+"."+x.substring(3,6)+"."+x.substring(6);
        }
        else if(x.length()==10){
            x = x.substring(0,4)+"."+x.substring(4,7)+"."+x.substring(7);
        }
        txt.setText("Pesanan anda atas nama "+nama+" Sejumlah IDR "+x+" akan segera kami proses"+"\n"+"Jika butuh bantuan silahkan hubungi kami");
        //HANDLE KEY BACK PRESS
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //Toast.makeText(getActivity(),"Tidak dapat Kembali, pilih menu yang tersedia", Toast.LENGTH_SHORT).show();
                        Fragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.containerView, homeFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return true;
                    }
                }
                return false;
            }
        });

        rincian = (Button) v.findViewById(R.id.btnrincianorder);
        back = (Button) v.findViewById(R.id.btnkembaliberbelanja);

        rincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment rincianFragment = new RincianOrder();
                fragmentTransaction.replace(R.id.containerView, rincianFragment);
                fragmentTransaction.addToBackStack("FRAGMENT").commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
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
