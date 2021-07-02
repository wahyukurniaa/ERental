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
import android.widget.Toast;

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
    TextView title,kategori,jdl,isi;

    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout koneksi, info;

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
        koneksi = findViewById(R.id.l_kosong);
        info = findViewById(R.id.l_info);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataIsiKategori();
            }
        });
        koneksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataIsiKategori();
            }
        });

        dataIsiKategori = new ArrayList<>();
        getDataIsiKategori();

        kategori = findViewById(R.id.kategori);
        jdl = findViewById(R.id.jdl);
        isi = findViewById(R.id.isi);
        set_l_info();

    }

    private void set_l_info(){
        if (id.equals("5")){
            kategori.setText(R.string.s5_0);
            jdl.setText(R.string.s5_1);
            isi.setText(R.string.s5_2);
        }else if(id.equals("6")){
            kategori.setText(R.string.s6_0);
            jdl.setText(R.string.s6_1);
            isi.setText(R.string.s6_2);
        }else if (id.equals("3")){
            kategori.setText(R.string.s3_0);
            jdl.setText(R.string.s3_1);
            isi.setText(R.string.s3_2);
        }else if (id.equals("1")){
            kategori.setText(R.string.s1_0);
            jdl.setText(R.string.s1_1);
            isi.setText(R.string.s1_2);
        }else if (id.equals("7")){
            kategori.setText(R.string.s7_0);
            jdl.setText(R.string.s7_1);
            isi.setText(R.string.s7_2);
        }else if (id.equals("2")){
            kategori.setText(R.string.s2_0);
            jdl.setText(R.string.s2_1);
            isi.setText(R.string.s2_2);
        }else if (id.equals("4")){
            kategori.setText(R.string.s4_0);
            jdl.setText(R.string.s4_1);
            isi.setText(R.string.s4_2);
        }

    }

    private void setLoading() {
        swipeRefreshLayout.setRefreshing(true);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        koneksi.setVisibility(View.GONE);
        recycler_kategori.setVisibility(View.GONE);
        info.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
    }

    private void setSukses() {
        swipeRefreshLayout.setRefreshing(false);
        shimmerFrameLayout.setVisibility(View.GONE);
        koneksi.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        recycler_kategori.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);
    }

    private void setGagal() {
        recycler_kategori.setVisibility(View.GONE);
        info.setVisibility(View.GONE);
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
                            Log.d("tampil barang","response:"+response+id);

                            dataIsiKategori.clear();
                            if (response.getString("status").equalsIgnoreCase("gagal")){
                                setGagal();
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