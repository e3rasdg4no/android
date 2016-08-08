package com.eragano.eraganoapps.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.eragano.eraganoapps.R;
import com.eragano.eraganoapps.adapter.ProdukAdapter;
import com.eragano.eraganoapps.penampung.Produk;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Deztu on 7/29/2016.
 */
public class FragmentHistoriJual extends Fragment {
    private ListView list= null;
    private ProdukAdapter adapter= null;
    private List<Produk> produkList= null;
    private TextView kosong= null;
    private SharedPreferences sp=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_histori_jual, container, false);

        sp= getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);

        list= (ListView)view.findViewById(R.id.listHistoriJual);
        kosong= (TextView)view.findViewById(R.id.kosong);

        produkList= new ArrayList<>();

        getData();

        return view;
    }

    public void getData(){
        String url= "http://103.236.201.252/android/get_histori_jual.php";

        final ProgressDialog p= ProgressDialog.show(getActivity(), "", "Data sedang diproses...");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                p.dismiss();

                if (response.equals("null")){
                    kosong.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                }
                else{
                    kosong.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);

                    Gson gson= new Gson();
                    produkList= Arrays.asList(gson.fromJson(response, Produk[].class));

                    adapter= new ProdukAdapter(getActivity(), produkList);
                    list.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new Hashtable<>();

                String username= sp.getString("User", null);

                params.put("username", username);

                return params;
            }
        };

        int socketTimeout = 30000;
        int DEFAULT_MAX_RETRIES = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
