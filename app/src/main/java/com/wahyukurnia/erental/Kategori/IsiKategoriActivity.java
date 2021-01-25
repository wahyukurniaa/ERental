package com.wahyukurnia.erental.Kategori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
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
    ImageView back;
    TextView title;

    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout koneksi;

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

        title = findViewById(R.id.tv_toolbar);
        title.setText("Barang");

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout = findViewById(R.id.swBarang);
        shimmerFrameLayout = findViewById(R.id.shimmer_barang);
        koneksi = findViewById(R.id.layout_koneksi);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataIsiKategori();
            }
        });

        dataIsiKategori = new ArrayList<>();
        getDataIsiKategori();
    }

    private void setLoading() {
        swipeRefreshLayout.setRefreshing(true);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        koneksi.setVisibility(View.GONE);
        recycler_kategori.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
    }

    private void setSukses() {
        swipeRefreshLayout.setRefreshing(false);
        shimmerFrameLayout.setVisibility(View.GONE);
        koneksi.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        recycler_kategori.setVisibility(View.VISIBLE);
    }

    private void setGagal() {
        recycler_kategori.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        shimmerFrameLayout.setVisibility(View.GONE);
        koneksi.setVisibility(View.VISIBLE);
        shimmerFrameLayout.stopShimmer();
    }



    public void getDataIsiKategori(){
        setLoading();
        AndroidNetworking.get(api.URL_Isi_Kategori+id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            setSukses();
                            Log.d("tampil barang","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                            dataIsiKategori.clear();
                            if (response.getString("status").equalsIgnoreCase("gagal")){
                                setGagal();
                            }else {
                                Gson gson = new Gson();
                                for (int i=0; i<res.length(); i++){
                                    JSONObject data = res.getJSONObject(i);

                                    Model_IsiKategori Isi = gson.fromJson(data + "", Model_IsiKategori.class);
                                    dataIsiKategori.add(Isi);
                                }
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
                        setGagal();
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }

}