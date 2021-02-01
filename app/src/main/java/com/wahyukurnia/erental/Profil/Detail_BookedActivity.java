package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.databinding.ActivityDetailBookedBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class Detail_BookedActivity extends AppCompatActivity {

    String namaBarang, hargaBarang, banyakSewa, namaPenyewa, tglAwal, tglAkhir, alamatUser, status, total, gambarBarang, gambarJaminan;
    private ActivityDetailBookedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBookedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        API api = new API();
        Intent intent = new Intent(getIntent());
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);

        binding.toolbar.tvToolbar.setText("Detail Booked");
        binding.toolbar.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.namaBarang.setText(intent.getStringExtra("namaBarang"));
        binding.hargaBarang.setText(formatRupiah.format((double)Integer.valueOf(intent.getStringExtra("hargaBarang")))+" /Hari");
        binding.banyakBarang.setText(intent.getStringExtra("banyakSewa")+" item");
        binding.namaPenyewa.setText(intent.getStringExtra("namaPenyewa"));
        binding.tglAwal.setText(intent.getStringExtra("tglAwal"));
        binding.tglAkhir.setText(intent.getStringExtra("tglAkhir"));
        binding.ketBanyak.setText(intent.getStringExtra("banyakSewa")+" item");
        binding.alamat.setText(intent.getStringExtra("alamat"));
        binding.statusSewa.setText(intent.getStringExtra("status")); ///isi kk
        Picasso.get().load(api.URL_GAMBAR_U+intent.getStringExtra("gambarBarang")).into(binding.imgBarang);
        Picasso.get().load(api.URL_GAMBAR_U+intent.getStringExtra("gambarJaminan")).into(binding.imgJaminan);
        binding.totalHarga.setText(formatRupiah.format((double)Integer.valueOf(intent.getStringExtra("totaSewa"))));

    }
}