package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Checkout.CheckoutActivity;
import com.wahyukurnia.erental.Kategori.Adapter_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_IsiKategori;
import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.Rating.RatingActivity;
import com.wahyukurnia.erental.TinyDB;
import com.wahyukurnia.erental.databinding.ActivityDetailBookedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class Detail_BookedActivity extends AppCompatActivity {

    String namaBarang, hargaBarang, banyakSewa, namaPenyewa, tglAwal, tglAkhir, alamatUser, status, total, gambarBarang, gambarJaminan;
    private ActivityDetailBookedBinding binding;
    String id_barang,id_user;
    API api;
    AlertDialog alertDialog;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBookedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        tinyDB = new TinyDB(this);
        Intent intent = new Intent(getIntent());
        id_barang = intent.getStringExtra("id_barang");
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mencoba Masuk ....").setCancelable(false).build();
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
        binding.alamat.setText(intent.getStringExtra("alamat"));
        binding.statusSewa.setText(intent.getStringExtra("status")); ///isi kk
        Picasso.get().load(api.URL_GAMBAR_U+intent.getStringExtra("gambarBarang")).into(binding.imgBarang);
        Picasso.get().load(api.URL_GAMBAR_U+intent.getStringExtra("gambarJaminan")).into(binding.imgJaminan);
        binding.totalHarga.setText(formatRupiah.format((double)Integer.valueOf(intent.getStringExtra("totaSewa"))));

        getRating();


    }
        public void getRating(){
        alertDialog.show();
        AndroidNetworking.post(api.URL_SELECT_RATING)
                .addBodyParameter("id_barang",id_barang)
                .addBodyParameter("id_user",tinyDB.getString("keyIdUser"))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            Log.d("tampilmenu", "response:" + response);
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                JSONArray res = response.getJSONArray("res");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Toast.makeText(Detail_BookedActivity.this, "Terima Kasih Sudah Memberi Rating", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                showDialog();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                        alertDialog.hide();
                    }
                });
    }




         private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Silahkan Isi Ulasan!");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Feedback")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent i = new Intent(Detail_BookedActivity.this, RatingActivity.class);
                        i.putExtra("id_barang",id_barang);

                        startActivity(i);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}