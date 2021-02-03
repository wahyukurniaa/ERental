package com.wahyukurnia.erental.DetailStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailStoreActivity extends AppCompatActivity {
    String id_store;
    API api;

    private List<Model_DetailStore> dataStore;
    private RecyclerView recycler_detailStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);

        api = new API();
        AndroidNetworking.initialize(this);

        recycler_detailStore = findViewById(R.id.recycler_detailStore);
        recycler_detailStore.setHasFixedSize(true);
        recycler_detailStore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dataStore = new ArrayList<>();


        Intent i = getIntent();
        id_store = i.getStringExtra("id_store");
        Log.e("id_store",id_store);

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

                            JSONArray res = response.getJSONArray("res");
                            Gson gson = new Gson();
                            for (int i=0; i<res.length(); i++){
                                JSONObject data = res.getJSONObject(i);

                                Model_DetailStore Isi = gson.fromJson(data + "", Model_DetailStore.class);
                                dataStore.add(Isi);

                            }
                            Adapter_DetailStore adapter = new Adapter_DetailStore(dataStore);
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