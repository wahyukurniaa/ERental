package com.wahyukurnia.erental.Barang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Notif.NotificationActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BarangActivity extends AppCompatActivity implements UpdateBarang{
    API api;
    private List<Model_Barang> dataBarang;
    private RecyclerView recycler_barang;
    TinyDB tinyDB;
    String id_user;
    TextView title;
    ImageView back;
    RelativeLayout kosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        api = new API();
        AndroidNetworking.initialize(this);

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

        kosong = findViewById(R.id.kosong);

        title = findViewById(R.id.tv_toolbar);
        title.setText("Barang Saya");
        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycler_barang = findViewById(R.id.recycler_barang);
        recycler_barang.setHasFixedSize(true);
        recycler_barang.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dataBarang = new ArrayList<>();

        getBarang();
    }

    public void getBarang(){
        AndroidNetworking.get(api.URL_BARANG_DISEWAKAN+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                kosong.setVisibility(View.GONE);
                                recycler_barang.setVisibility(View.VISIBLE);
                                Log.d("tampil Store", "response:" + response + id_user);
                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataBarang.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Barang Isi = gson.fromJson(data + "", Model_Barang.class);
                                    dataBarang.add(Isi);
                                }
                                getAdapter();
                            }else {
                                    kosong.setVisibility(View.VISIBLE);
                                    recycler_barang.setVisibility(View.GONE);
                                    Toast.makeText(BarangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
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
        if (dataBarang.isEmpty()){
            kosong.setVisibility(View.VISIBLE);
        }
    }

    private void getAdapter() {
        Adapter_Barang adapter = new Adapter_Barang(dataBarang, this::OnUpdateBarang);
        recycler_barang.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnUpdateBarang() {
        getBarang();
    }
}