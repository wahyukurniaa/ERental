package com.wahyukurnia.erental.Pesanan;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Pesanan extends Fragment {

    TextView txt_namaBarang, txt_tanggal_pinjam, txt_tanggal_kembali, detail;
    API api;
    TinyDB tinyDB;
    String id_user;

    List<Model_Booked> dataBooked;
    Adapter_Booked adapter;

    TextView title;
    ImageView back;
    private RecyclerView recycler_booked;
    AlertDialog alertDialog;
    RelativeLayout kosong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__pesanan, container, false);

        api = new API();
        AndroidNetworking.initialize(getContext());


//        back = view.findViewById(R.id.ib_back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        kosong = view.findViewById(R.id.l_kosong);
        kosong.setVisibility(View.GONE);

        tinyDB = new TinyDB(getContext());
        id_user = tinyDB.getString("keyIdUser");

        recycler_booked = view.findViewById(R.id.recycler_booked);
        recycler_booked.setHasFixedSize(true);


        dataBooked = new ArrayList<>();

        getBooked();
        return view;

    }



    public void getBooked(){
        Log.e("salah",api.URL_BOOKED+id_user);
        AndroidNetworking.get(api.URL_BOOKED+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                kosong.setVisibility(View.GONE);
                                recycler_booked.setVisibility(View.VISIBLE);
                                Log.d("tampilmenu", "response:" + response);
                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataBooked.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);

                                    Model_Booked booked = gson.fromJson(data + "", Model_Booked.class);
                                    dataBooked.add(booked);
                                }
                                Adapter_Booked adapter = new Adapter_Booked(dataBooked);
                                recycler_booked.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });
        if (dataBooked.isEmpty()){
            kosong.setVisibility(View.VISIBLE);
            recycler_booked.setVisibility(View.GONE);
        }
    }
}