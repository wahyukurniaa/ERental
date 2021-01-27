package com.wahyukurnia.erental.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Checkout.CheckoutActivity;
import com.wahyukurnia.erental.Kategori.Adapter_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_IsiKategori;
import com.wahyukurnia.erental.Order.OrderActivity;
import com.wahyukurnia.erental.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailBarangActivity extends AppCompatActivity {
String id;
TextView judul,txt_stok,txt_deskripsi,txt_nama_penyedia,txt_alamat_penyedia,txt_tarif;
Button btn_order;
ImageView img_detail;
API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        api = new API();

        AndroidNetworking.initialize(this);

        Intent i = getIntent();
        id = i.getStringExtra("id_barang");
        Log.e("barang",""+id);

        judul = findViewById(R.id.txt_judul);
        txt_stok  = findViewById(R.id.txt_stok);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_nama_penyedia = findViewById(R.id.txt_nama_penyedia);
        txt_alamat_penyedia = findViewById(R.id.txt_alamat_penyedia);
        txt_tarif = findViewById(R.id.txt_tarif);
        img_detail = findViewById(R.id.img_detail);
        btn_order = findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBarangActivity.this, OrderActivity.class);
                i.putExtra("id_barang",""+id);
                 startActivity(i);
            }
        });


        AndroidNetworking.initialize(this);
        getDataIsiKategori();

    }

    public void getDataIsiKategori(){
        AndroidNetworking.get(api.URL_DESKRIPSI+id)
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
                                int id =  data.getInt("id_barang");
                                String nama = data.getString("nama_barang");
                                String tarif = data.getString("tarif_barang");
                                String deskripsi = data.getString("deskripsi");
                                String stok = data.getString("stok");
                                String gambar =  api.URL_GAMBAR+data.getString("gambar_barang");
                                String nama_Store = data.getString("nama_store");
                                String alamat_Store = data.getString("alamat_store");

                                judul.setText(nama);
                                txt_stok.setText("Tersedia "+stok);
                                txt_deskripsi.setText(deskripsi);
                                txt_nama_penyedia.setText(nama_Store);
                                txt_alamat_penyedia.setText(alamat_Store);
                                txt_tarif.setText("Rp. "+tarif);
                                Picasso.get().load(api.URL_GAMBAR+data.getString("gambar_barang")).into(img_detail);

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
    }






}