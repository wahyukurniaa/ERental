package com.wahyukurnia.erental.Barang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;
import com.wahyukurnia.erental.databinding.ActivityDetailBarangSewaBinding;
import com.wahyukurnia.erental.databinding.ActivityDetailBookedBinding;

import java.text.NumberFormat;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class Detail_BarangSewaActivity extends AppCompatActivity {
    private ActivityDetailBarangSewaBinding binding;
    String id_barang,id_user;
    API api;
    AlertDialog alertDialog;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__barang_sewa);

        binding = ActivityDetailBarangSewaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        tinyDB = new TinyDB(this);
        Intent intent = new Intent(getIntent());
        id_barang = intent.getStringExtra("id_barang");

        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mencoba Masuk ....").setCancelable(false).build();
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);

        binding.toolbar.tvToolbar.setText("Detail Barang Sewa");
        binding.toolbar.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.namaBarang.setText(intent.getStringExtra("namaBarang"));
        binding.hargaBarang.setText(formatRupiah.format((double)Integer.valueOf(intent.getStringExtra("hargaBarang")))+" /Hari");
        binding.banyakBarang.setText(intent.getStringExtra("stok")+" item");
        binding.namaPenyewa.setText(intent.getStringExtra("nama_user"));
        binding.alamat.setText(intent.getStringExtra("alamat_user"));

        Picasso.get().load(api.URL_GAMBAR_U+intent.getStringExtra("gambar_barang")).into(binding.imgBarang);





    }
}