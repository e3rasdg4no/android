package com.eragano.eraganoapps.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.HomeAdapter;
import com.eragano.eraganoapps.adapter.HomeItem;
import com.eragano.eraganoapps.adapter.NewHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends android.support.v4.app.Fragment {
    private ListView ls;
    List<HomeItem> homeItems;
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String user_group;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        user_group=sp.getString("USER_GROUP",null);

        ls = (ListView) v.findViewById(R.id.listHome);

        String header1[] = getResources().getStringArray(R.array.header1);
        String header2[] = getResources().getStringArray(R.array.header2);
        Integer backImage[] = {R.drawable.dashboardbelikebutuhan, R.drawable.dashboardjadwalbertani, R.drawable.dashboardjualhasilpanen, R.drawable.dashboradasuransi, R.drawable.dashboardpinjaman, R.drawable.dashboardinformasipertanian, R.drawable.dashboardkinerja};
        Integer iconImage[] = {R.drawable.iconbeli, R.drawable.iconjadwal, R.drawable.iconjual, R.drawable.iconasuransi, R.drawable.iconpinjaman, R.drawable.iconinformasi, R.drawable.iconkinerja};

        homeItems = new ArrayList<HomeItem>();
        for(int i=0;i<header1.length;i++){
            HomeItem item = new HomeItem(backImage[i],iconImage[i],header1[i],header2[i]);
            homeItems.add(item);
        }

        NewHomeAdapter adapter = new NewHomeAdapter(getActivity(), homeItems);
        //HomeAdapter adapter = new HomeAdapter(getActivity(), backImage, iconImage, header1, header2);
        ls.setAdapter(adapter);

        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position+1){
                    case 1:
                        Fragment kebutuhanFragment = new KebutuhanFragment();
                        fragmentTransaction.replace(R.id.containerView, kebutuhanFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        final Dialog dialog2 = new Dialog(getActivity());
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.custom_dialog2);
                        Button btnok2 = (Button) dialog2.findViewById(R.id.btn_ok);
                        btnok2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();
                        break;
                    case 2:
                        /*if(user_group.equals("member")) {
                            Fragment jadwalFragment = new JadwalFragment();
                            fragmentTransaction.replace(R.id.containerView, jadwalFragment);
                            fragmentTransaction.addToBackStack("FRAGMENT").commit();
                            break;
                        }
                        else{*/
                            final Dialog dialog5 = new Dialog(getActivity());
                            dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog5.setContentView(R.layout.custom_dialog);
                            Button btnok5 = (Button) dialog5.findViewById(R.id.btn_ok);
                            Button btnhub5 = (Button) dialog5.findViewById(R.id.btn_hubungi);
                            btnok5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog5.dismiss();
                                }
                            });
                            btnhub5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711242493"));
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    getActivity().startActivity(intent);
                                }
                            });
                            dialog5.show();
                            break;
                        //}
                    case 3:
                        Fragment jualFragment = new JualFragment();
                        fragmentTransaction.replace(R.id.containerView, jualFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        break;
                    case 4:
                        if(user_group.equals("member")) {
                            Fragment asuransiFragment = new AsuransiFragment();
                            fragmentTransaction.replace(R.id.containerView, asuransiFragment);
                            fragmentTransaction.addToBackStack("FRAGMENT").commit();
                            break;
                        }
                        else{
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.custom_dialog);
                            Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
                            Button btnhub = (Button) dialog.findViewById(R.id.btn_hubungi);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            btnhub.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711242493"));
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    getActivity().startActivity(intent);
                                }
                            });
                            dialog.show();
                            break;
                        }
                    case 5:
                        if(user_group.equals("member")) {
                            Fragment pinjamanFragment = new PinjamanFragment();
                            fragmentTransaction.replace(R.id.containerView, pinjamanFragment);
                            fragmentTransaction.addToBackStack("FRAGMENT").commit();
                            break;
                        }
                        else{
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.custom_dialog);
                            Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
                            Button btnhub = (Button) dialog.findViewById(R.id.btn_hubungi);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            btnhub.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:087711242493"));
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    getActivity().startActivity(intent);
                                }
                            });
                            dialog.show();
                            break;
                        }
                    case 6:
                        Fragment informasiFragment = new InformasiFragment();
                        fragmentTransaction.replace(R.id.containerView, informasiFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        break;
                    case 7:
                        Fragment kinerjaFragment = new KinerjaFragment2();
                        fragmentTransaction.replace(R.id.containerView, kinerjaFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        break;
                }
            }
        });
        return v;
    }
}

