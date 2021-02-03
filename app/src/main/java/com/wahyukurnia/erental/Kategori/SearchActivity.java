package com.wahyukurnia.erental.Kategori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.databinding.ActivitySearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    API api;
    private List<Model_IsiKategori> dataIsiKategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        AndroidNetworking.initialize(this);
        dataIsiKategori = new ArrayList<>();

        binding.progres.setVisibility(View.GONE);

        binding.rvProdukS.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvProdukS.setHasFixedSize(true);
        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.searchEd.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getProduk(query);
                Log.d("key", "isi"+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getProduk(newText);
                Log.d("key", "isi"+newText);
                return false;
            }
        });

    }

    private void getProduk(String query) {
        Log.d("masuk key","url | "+api.URL_SEARCH+query);
        binding.progres.setVisibility(View.VISIBLE);
        AndroidNetworking.get(api.URL_SEARCH+query)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampil barang key","response:"+response+query);
                            binding.progres.setVisibility(View.GONE);
                            dataIsiKategori.clear();
                            if (response.getString("status").equalsIgnoreCase("gagal")){

                            }else {
                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                for (int i=0; i<res.length(); i++){
                                    JSONObject data = res.getJSONObject(i);

                                    Model_IsiKategori Isi = gson.fromJson(data + "", Model_IsiKategori.class);
                                    dataIsiKategori.add(Isi);
                                }
                            }
                            Adapter_IsiKategori adapter = new Adapter_IsiKategori(dataIsiKategori);
                            binding.rvProdukS.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        binding.progres.setVisibility(View.GONE);
                        Log.e("tampil menu key","eror:"+anError);
                    }
                });
    }
}