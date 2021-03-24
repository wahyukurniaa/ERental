package com.wahyukurnia.erental.DetailStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Kategori.Adapter_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.databinding.ActivityDetailStoreBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailStoreActivity extends AppCompatActivity {
    String id_store;
    API api;

    private List<Model_IsiKategori> dataStore;
    private RecyclerView recycler_detailStore;
    private ActivityDetailStoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoreBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_detail_store);
        setContentView(binding.getRoot());

        api = new API();
        AndroidNetworking.initialize(this);

        recycler_detailStore = findViewById(R.id.recycler_detailStore);
        recycler_detailStore.setHasFixedSize(true);
        recycler_detailStore.setLayoutManager(new GridLayoutManager(this, 2));
        dataStore = new ArrayList<>();


        Intent i = getIntent();
        id_store = i.getStringExtra("id_store");
        Log.e("id_store",id_store);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDataStore();
    }
    public void getDataStore(){
        AndroidNetworking.get(api.URL_DETAIL_STORE+id_store)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampil Store","response:"+response+id_store);
                            dataStore.clear();
                            binding.jmlSewa.setText(response.getString("sewa"));
                            JSONArray res = response.getJSONArray("res");
                            Gson gson = new Gson();
                            for (int i=0; i<res.length(); i++){
                                JSONObject data = res.getJSONObject(i);
                                binding.namaStore.setText(data.getString("nama_store"));
                                binding.alamatStore.setText(data.getString("alamat_store"));
                                Picasso.get().load(api.URL_GAMBAR_U+data.getString("gambar_store")).into(binding.fotoStore);
                                Model_IsiKategori Isi = gson.fromJson(data + "", Model_IsiKategori.class);
                                dataStore.add(Isi);
                                binding.namaToko.setText(data.getString("nama_store"));
                            }
                            binding.jmlBarang.setText(dataStore.size()+"");
                            Adapter_IsiKategori adapter = new Adapter_IsiKategori(dataStore);
                            recycler_detailStore.setAdapter(adapter);
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