package com.wahyukurnia.erental.Kategori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.PrefManager;
import com.wahyukurnia.erental.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Kategori extends AppCompatActivity {
    API api;

    private List<Model_Kategori> dataKategori;
    private RecyclerView recycler_kategori;
    Adapter_Kategori adapter;
    Button btn_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);


        api = new API();
        AndroidNetworking.initialize(this);

        recycler_kategori = findViewById(R.id.recycler_kategori);
        recycler_kategori.setHasFixedSize(true);
        recycler_kategori.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        dataKategori = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        getDataKategori();

    }

    public void getDataKategori(){
        AndroidNetworking.get(api.URL_Kategori)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampilmenu","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                            for(int i =0; i <res.length();i++){
                                JSONObject data = res.getJSONObject(i);
                                dataKategori.add(new Model_Kategori(
                                        data.getInt("id_kategori"),
                                        data.getString("nama_kategori")

                                ));
                            }
                            Adapter_Kategori adapter = new Adapter_Kategori(dataKategori);
                            recycler_kategori.setAdapter(adapter);
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