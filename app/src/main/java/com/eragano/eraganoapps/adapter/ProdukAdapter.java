package com.eragano.eraganoapps.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.penampung.Produk;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by M Dimas Faizin on 3/29/2016.
 */
public class ProdukAdapter extends BaseAdapter {
    private List<Produk> produkList= null;
    private Context context= null;

    public ProdukAdapter(Context context, List<Produk> produkList) {
        this.produkList = produkList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produkList.size();
    }

    @Override
    public Object getItem(int position) {
        return produkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder= new ViewHolder();

            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(R.layout.layout_produk, null);

            viewHolder.image= (ImageView)convertView.findViewById(R.id.imageInHistori);
            viewHolder.judul= (TextView)convertView.findViewById(R.id.title_post);
            viewHolder.jumlahJual= (TextView)convertView.findViewById(R.id.jumlahJual);
            viewHolder.jumlahPesanan= (TextView)convertView.findViewById(R.id.jumlahPesanan);
            viewHolder.jumlahTerjual= (TextView)convertView.findViewById(R.id.jumlahTerjual);
            viewHolder.idJual= (TextView)convertView.findViewById(R.id.idJual);
            viewHolder.ubahTerjual= (ImageView)convertView.findViewById(R.id.ubahTerjual);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder)convertView.getTag();
        }

        final Produk produk= produkList.get(position);

        String imagePath= "http://103.236.201.252/android/uploads/" + produk.getImage();
        Glide.with(context)
                .load(imagePath)
                .centerCrop()
                .into(viewHolder.image);
        viewHolder.judul.setText(produk.getNama());
        viewHolder.jumlahJual.setText(produk.getStok());
        viewHolder.jumlahPesanan.setText(produk.getJumlahPesanan());
        viewHolder.jumlahTerjual.setText(produk.getJumlahTerjual());
        viewHolder.idJual.setText(produk.getId_jual());

        viewHolder.ubahTerjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Produk produk1= produkList.get(position);
                String titleProduk= produk1.getNama();
                String stok= produk1.getStok();
                String harga= produk1.getHarga();

                final Dialog dialog= new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_jual);

                ImageView btnClose= (ImageView)dialog.findViewById(R.id.close);
                TextView txtClose= (TextView)dialog.findViewById(R.id.textClose);
                Button batal= (Button)dialog.findViewById(R.id.batalInDialogHistori);
                Button save= (Button)dialog.findViewById(R.id.saveInDialogHistori);
                TextView title= (TextView)dialog.findViewById(R.id.titleDialog);
                final EditText jumlah= (EditText)dialog.findViewById(R.id.jumlahInDialogHistori);
                final EditText hargas= (EditText)dialog.findViewById(R.id.hargaInDialogHistori);

                title.setText(titleProduk + ", " + stok + "Kg - " + "Rp. " + harga);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(jumlah.getText())){
                            jumlah.setError("Kolom ini tidak boleh kosong");
                            jumlah.requestFocus();

                            return ;
                        }
                        if (TextUtils.isEmpty(hargas.getText())){
                            hargas.setError("Kolom ini tidak boleh kosong");
                            hargas.requestFocus();

                            return ;
                        }

                        final String jumlahs= jumlah.getText().toString();
                        final String hargass= hargas.getText().toString();
                        final String id= produk1.getId_jual();

                        String url= "http://103.236.201.252/android/updateTerjual.php";

                        final ProgressDialog p= ProgressDialog.show(context, "", "Data sedang diproses");
                        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                p.dismiss();
                                dialog.dismiss();

                                Toast.makeText(context, "Data telah dimasukkan", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                p.dismiss();
                                dialog.dismiss();

                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params= new Hashtable<String, String>();

                                params.put("id", id);
                                params.put("harga", hargass);
                                params.put("terjual", jumlahs);

                                return params;
                            }
                        };

                        int socketTimeout = 30000;
                        int DEFAULT_MAX_RETRIES = 0;
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(policy);
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                dialog.show();
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        TextView judul;
        TextView jumlahJual;
        TextView jumlahPesanan;
        TextView jumlahTerjual;
        TextView idJual;
        ImageView ubahTerjual;
    }
}
