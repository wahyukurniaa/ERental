package com.wahyukurnia.erental.PesananMasuk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Profil.EditProfilActivity;
import com.wahyukurnia.erental.Profil.ProfilActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;
import com.wahyukurnia.erental.databinding.ActivityDetailBookedBinding;
import com.wahyukurnia.erental.databinding.ActivityDetailPesananMasukBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class Detail_PesananMasukActivity extends AppCompatActivity {

    private ActivityDetailPesananMasukBinding binding;
    String id_barang,id_sewa_barang;
    API api;
    AlertDialog alertDialog;
    TinyDB tinyDB;

    Spinner List;
    int a;

    ArrayList<String> dataStatus = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPesananMasukBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        tinyDB = new TinyDB(this);
        Intent intent = new Intent(getIntent());
        id_barang = intent.getStringExtra("id_barang");
        id_sewa_barang = intent.getStringExtra("id_sewa_barang");

        List = binding.spinnerStatus;
        List.setVisibility(View.GONE);
        dataStatus.add(intent.getStringExtra("status"));
        if (intent.getStringExtra("status").equalsIgnoreCase("Diterima" )||intent.getStringExtra("status").equalsIgnoreCase("Diserahkan" )){
            List.setVisibility(View.VISIBLE);
        }
                getStatus();



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

        Log.d("tes", intent.getStringExtra("namaBarang"));

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

    public void getStatus(){
        AndroidNetworking.get(api.URL_STATUS_PENJUALAN)
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
                                String status = data.getString("status");
                                dataStatus.add(status);

                                int id = data.getInt("id_status");


                            }
                            setDataSpinner();

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

    private void setDataSpinner (){
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, dataStatus);

        //Memasukan Adapter pada Spinner
        List.setAdapter(adapter);

        //Mengeset listener untuk mengetahui event/aksi saat item dipilih
        List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                a  = (int) adapter.getItemId(i)+1;
                update();

            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
    }
    public  void update(){
        AndroidNetworking.post(api.URL_UPDATE_STATUS)
                .addBodyParameter("id_sewa_barang",id_sewa_barang)
                .addBodyParameter("id_status",""+a)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        Toast.makeText(getApplicationContext(), "Data berhasil di update..."
                                ,Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("salah",""+error);
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }






}