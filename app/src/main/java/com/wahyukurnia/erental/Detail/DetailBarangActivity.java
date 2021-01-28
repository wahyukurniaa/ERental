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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailBarangActivity extends AppCompatActivity {
    String id;
    TextView judul, txt_stok, txt_deskripsi, txt_nama_penyedia, txt_alamat_penyedia, txt_tarif,txt_store,txt_telp,txt_WA;
    Button btn_order;
    ImageView img_detail, back;
    TextView title;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        api = new API();

        AndroidNetworking.initialize(this);

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = findViewById(R.id.tv_toolbar);

        Intent i = getIntent();
        id = i.getStringExtra("id_barang");
        Log.e("barang",""+id);


        judul = findViewById(R.id.txt_judul);
        txt_stok = findViewById(R.id.txt_stok);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_nama_penyedia = findViewById(R.id.txt_nama_penyedia);
        txt_alamat_penyedia = findViewById(R.id.txt_alamat_penyedia);
        txt_store = findViewById(R.id.txt_store);
        txt_telp = findViewById(R.id.txt_telp);
        txt_WA = findViewById(R.id.txt_WA);
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

    public void getDataIsiKategori() {
        Intent i = getIntent();
        id = i.getStringExtra("id_barang");

        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);

        String nama = i.getStringExtra("nama_barang");
        String tarif = formatRupiah.format((double) Integer.valueOf(i.getStringExtra("tarif_barang")));
        String deskripsi = i.getStringExtra("deskripsi");
        String stok = i.getStringExtra("stok");
        String nama_Store = i.getStringExtra("nama_store");
        String telp_store = i.getStringExtra("telp_store");
        String WA_store = i.getStringExtra("wa_store");
        String alamat_Store = i.getStringExtra("alamat_store");
        String nama_user = i.getStringExtra("nama_user");

        judul.setText(nama);
        txt_stok.setText("Tersedia " + stok);
        txt_deskripsi.setText(deskripsi);
        txt_nama_penyedia.setText(nama_user);
        txt_alamat_penyedia.setText(alamat_Store);
        txt_store.setText(nama_Store);
        txt_telp.setText(telp_store);
        txt_WA.setText(WA_store);
        txt_tarif.setText(tarif + " /hari");
        Picasso.get().load(api.URL_GAMBAR_U + i.getStringExtra("gambar_barang")).into(img_detail);

        title.setText("Detail Barang");
    }


}