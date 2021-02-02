package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Kategori.Adapter_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class BookedActivity extends AppCompatActivity {
    TextView txt_namaBarang, txt_tanggal_pinjam, txt_tanggal_kembali,detail;
    API api;
    TinyDB tinyDB;
    String id_user;

    List<Model_Booked> dataBooked;
    Adapter_Booked adapter;

    TextView title;
    ImageView back;
    private RecyclerView recycler_booked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);
        api = new API();
        AndroidNetworking.initialize(this);

        title = findViewById(R.id.tv_toolbar);
        title.setText("Booked");
        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

       recycler_booked = findViewById(R.id.recycler_booked);
        recycler_booked.setHasFixedSize(true);


        dataBooked = new ArrayList<>();

        getBooked();

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
                            Log.d("tampilmenu","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                            Gson gson = new Gson();
                            dataBooked.clear();
                            for (int i=0; i<res.length(); i++){
                                JSONObject data = res.getJSONObject(i);

                                Model_Booked booked = gson.fromJson(data + "", Model_Booked.class);
                                dataBooked.add(booked);
                            }
                            Adapter_Booked adapter = new Adapter_Booked(dataBooked);
                            recycler_booked.setAdapter(adapter);
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