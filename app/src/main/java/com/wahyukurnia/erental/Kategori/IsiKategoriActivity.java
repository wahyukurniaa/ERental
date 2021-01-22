package com.wahyukurnia.erental.Kategori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IsiKategoriActivity extends AppCompatActivity {
    API api;

    String id;

    private List<Model_IsiKategori> dataIsiKategori;
    private RecyclerView recycler_kategori;
    Adapter_IsiKategori adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_kategori);

        api = new API();
        AndroidNetworking.initialize(this);

        Intent i = getIntent();
        id = i.getStringExtra("id");

        recycler_kategori = findViewById(R.id.recycler_isi_kategori);
        recycler_kategori.setHasFixedSize(true);
        recycler_kategori.setLayoutManager(new GridLayoutManager(this,2));

        dataIsiKategori = new ArrayList<>();
        AndroidNetworking.initialize(this);
        getDataIsiKategori();

    }

    public void getDataIsiKategori(){
        AndroidNetworking.get(api.URL_Isi_Kategori+id)
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
                                dataIsiKategori.add(new Model_IsiKategori(
                                        data.getInt("id_barang"),
                                        data.getString("nama_barang"),
                                        data.getString("tarif_barang"),
                                       api.URL_GAMBAR+data.getString("gambar_barang")



                                ));
                            }
                            Adapter_IsiKategori adapter = new Adapter_IsiKategori(dataIsiKategori);
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