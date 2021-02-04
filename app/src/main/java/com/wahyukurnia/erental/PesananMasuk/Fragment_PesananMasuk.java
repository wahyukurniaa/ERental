package com.wahyukurnia.erental.PesananMasuk;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Pesanan.Adapter_Booked;
import com.wahyukurnia.erental.Pesanan.Model_Booked;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_PesananMasuk extends Fragment {

    API api;
    TinyDB tinyDB;
    String id_user;

    List<Model_PesananMasuk> dataPesananMasuk;
    Adapter_PesananMasuk adapter;

    TextView title;
    ImageView back;
    private RecyclerView recycler_pesananMasuk;
    AlertDialog alertDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__pesanan_masuk, container, false);

        api = new API();
        AndroidNetworking.initialize(getContext());

        tinyDB = new TinyDB(getContext());
        id_user = tinyDB.getString("keyIdUser");

        recycler_pesananMasuk = view.findViewById(R.id.recycler_pesananMasuk);
        recycler_pesananMasuk.setHasFixedSize(true);


        dataPesananMasuk = new ArrayList<>();

        getPesananMasuk();

        return view;
    }
    public void getPesananMasuk(){
        Log.e("salah",api.URL_ORDER+id_user);
        AndroidNetworking.get(api.URL_ORDER+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampilmenu","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                            Gson gson = new Gson();
                            dataPesananMasuk.clear();
                            for (int i=0; i<res.length(); i++){
                                JSONObject data = res.getJSONObject(i);

                                Model_PesananMasuk booked = gson.fromJson(data + "", Model_PesananMasuk.class);
                                dataPesananMasuk.add(booked);
                            }
                            Adapter_PesananMasuk adapter = new Adapter_PesananMasuk(dataPesananMasuk);
                            recycler_pesananMasuk.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }
}