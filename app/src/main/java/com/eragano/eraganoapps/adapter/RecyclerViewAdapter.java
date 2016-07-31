package com.eragano.eraganoapps.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eragano.eraganoapps.MainActivity;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.fragment.AsuransiFragment;
import com.eragano.eraganoapps.fragment.HomeFragment;
import com.eragano.eraganoapps.fragment.InformasiFragment;
import com.eragano.eraganoapps.fragment.JadwalFragment;
import com.eragano.eraganoapps.fragment.JualFragment;
import com.eragano.eraganoapps.fragment.KebutuhanFragment;
import com.eragano.eraganoapps.fragment.KinerjaFragment;
import com.eragano.eraganoapps.fragment.KinerjaFragment2;
import com.eragano.eraganoapps.fragment.PinjamanFragment;
import com.eragano.eraganoapps.fragment.RekeningFragment;
import com.eragano.eraganoapps.login.LoginActivity;

/**
 * Created by M Dimas Faizin on 3/21/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    String[] titles;
    TypedArray icons;
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String user_group;

    // The default constructor to receive titles,icons and context from MainActivity.
    public RecyclerViewAdapter(String[] titles, TypedArray icons, Context context) {

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    /**
     *Its a inner class to RecyclerViewAdapter Class.
     *This ViewHolder class implements View.OnClickListener to handle click events.
     *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
     *This ViewHolder describes an item view with respect to its place within the RecyclerView.
     *For every item there is a ViewHolder associated with it .
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView navTitle;
        ImageView navIcon;
        Context context;

        public ViewHolder(View drawerItem, int itemType, Context context) {

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if (itemType == 1) {
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);
            }
        }

        /**
         *This defines onClick for every item with respect to its position.
         */

        @Override
        public void onClick(View v) {
            final MainActivity mainActivity = (MainActivity) context;
            sp = mainActivity.getSharedPreferences("SP", Context.MODE_PRIVATE);
            mainActivity.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
            user_group = sp.getString("USER_GROUP", null);
            switch (getPosition()) {
                case 1:
                    Fragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.containerView, homeFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                case 2:
                    Fragment kebutuhanFragment = new KebutuhanFragment();
                    fragmentTransaction.replace(R.id.containerView, kebutuhanFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    final Dialog dialog2 = new Dialog(mainActivity);
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
                case 3:
                    /*if (user_group.equals("member")) {
                        Fragment jadwalFragment = new JadwalFragment();
                        fragmentTransaction.replace(R.id.containerView, jadwalFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        break;
                    } else {*/
                        final Dialog dialog5 = new Dialog(mainActivity);
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
                                if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mainActivity.startActivity(intent);
                            }
                        });
                        dialog5.show();
                        break;
                    //}
                case 4:
                    Fragment jualFragment = new JualFragment();
                    fragmentTransaction.replace(R.id.containerView, jualFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                case 5:
                    if(user_group.equals("member")) {
                        Fragment asuransiFragment = new AsuransiFragment();
                        fragmentTransaction.replace(R.id.containerView, asuransiFragment);
                        fragmentTransaction.addToBackStack("FRAGMENT").commit();
                        break;
                    }
                    else{
                        final Dialog dialog = new Dialog(mainActivity);
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
                                if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mainActivity.startActivity(intent);
                            }
                        });
                        dialog.show();
                        break;
                    }
                case 6:
                    if(user_group.equals("member")) {
                    Fragment pinjamanFragment = new PinjamanFragment();
                    fragmentTransaction.replace(R.id.containerView, pinjamanFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                    }
                    else{
                        final Dialog dialog = new Dialog(mainActivity);
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
                                if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mainActivity.startActivity(intent);
                            }
                        });
                        dialog.show();
                        break;
                    }
                /*case 7:
                    Fragment rekeningFragment = new RekeningFragment();
                    fragmentTransaction.replace(R.id.containerView, rekeningFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                    */
                case 7:
                    Fragment informasiFragment = new InformasiFragment();
                    fragmentTransaction.replace(R.id.containerView, informasiFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                case 8:
                    Fragment kinerjaFragment = new KinerjaFragment2();
                    fragmentTransaction.replace(R.id.containerView, kinerjaFragment);
                    fragmentTransaction.addToBackStack("FRAGMENT").commit();
                    break;
                case 9:
                    new AlertDialog.Builder(mainActivity)
                            .setMessage("Anda yakin ingin keluar dari akun ini?")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    editor = sp.edit();
                                    editor.clear();
                                    editor.putBoolean("Status", false);
                                    editor.commit();
                                    Intent i = new Intent(mainActivity, LoginActivity.class);
                                    mainActivity.finish();
                                    mainActivity.startActivity(i);
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .show();
                    break;
            }
        }
    }

    /**
     *This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     *Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.header_layout,null);
            return new ViewHolder(itemHeader,viewType,context);
        }



        return null;
    }

    /**
     *This method is called by RecyclerView.Adapter to display the data at the specified position.
     *This method should update the contents of the itemView to reflect the item at the given position.
     *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }

    }

    /**
     *It returns the total no. of items . We +1 count to include the header view.
     *So , it the total count is 5 , the method returns 6.
     *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


    /**
     *This methods returns 0 if the position of the item is '0'.
     *If the position is zero its a header view and if its anything else
     *its a row_item with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }
}
