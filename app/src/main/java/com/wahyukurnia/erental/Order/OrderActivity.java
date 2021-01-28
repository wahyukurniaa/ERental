package com.wahyukurnia.erental.Order;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Checkout.CheckoutActivity;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.Profil.PasangSewaActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class OrderActivity extends AppCompatActivity  {
API api;
String id,id_user;
TinyDB tinyDB;
ImageView img_order;
Button btnConfirm;
TextView namaBarang_Order, hargaBarang;
EditText edt_banyakSewa,edt_LamaSewa,edt_alamatPenyewa,edt_Jaminan, edt_jenis_transaksi,edt_jenis_pengiriman;
String gambar,banyakSewa;
    String tarif;

    private static final String TAG = "OrderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        api = new API();
        Intent i = getIntent();
        id = i.getStringExtra("id_barang");
        Log.e("order",""+id);


        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

        img_order = findViewById(R.id.img_order);
        namaBarang_Order = findViewById(R.id.txt_judul_order);
        hargaBarang = findViewById(R.id.txt_tarif_order);

        edt_banyakSewa = findViewById(R.id.edt_banyakSewa);
        edt_LamaSewa = findViewById(R.id.edt_LamaSewa);
        edt_alamatPenyewa = findViewById(R.id.edt_alamatPenyewa);
        edt_Jaminan = findViewById(R.id.edt_Jaminan);
        edt_jenis_transaksi = findViewById(R.id.edt_jenis_transaksi);
        edt_jenis_pengiriman = findViewById(R.id.edt_pengiriman);


        AndroidNetworking.initialize(this);
        getDataOrder();

        btnConfirm = findViewById(R.id.btnConfirm);
        aksiPasangSewa();


    }

    public void getDataOrder(){
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
                                tarif = data.getString("tarif_barang");


                                namaBarang_Order.setText(nama);
                                hargaBarang.setText("Rp. "+tarif+"/Hari");
                                gambar =data.getString("gambar_barang");
                                Picasso.get().load(api.URL_GAMBAR_U+data.getString("gambar_barang")).into(img_order);

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

    public void aksiPasangSewa(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String banyakSewa = edt_banyakSewa.getText().toString(); //mengambil Value etNim menjadi string
                String lamaSewa = edt_LamaSewa.getText().toString(); //mengambil Value etNim menjadi string
                String alamatPenyewa = edt_alamatPenyewa.getText().toString(); //mengambil Value etNim menjadi string
                String jaminan = edt_Jaminan.getText().toString(); //mengambil Value etNim menjadi string
                String transaksi = edt_jenis_transaksi.getText().toString(); //mengambil Value etNim menjadi string
                String pengiriman = edt_jenis_pengiriman.getText().toString(); //mengambil Value etNim menjadi string

                int total = Integer.valueOf(banyakSewa) * Integer.valueOf(tarif);
                //Handle Response
                Intent i = new Intent(OrderActivity.this, CheckoutActivity.class);
                i.putExtra("id_sewa_barang","");
                i.putExtra("id_user",id_user);
                i.putExtra("id_barang",id);
                i.putExtra("nama_barang",namaBarang_Order.getText().toString());
                i.putExtra("tarif_barang",hargaBarang.getText().toString());
                i.putExtra("gambar_barang", api.URL_GAMBAR_U+gambar);
                i.putExtra("banyak_sewa", banyakSewa+" item");
                i.putExtra("lama_sewa", lamaSewa);
                i.putExtra("alamat_penyewa", alamatPenyewa);
                i.putExtra("jaminan", jaminan);
                i.putExtra("jenis_transaksi", transaksi);
                i.putExtra("jenis_pengiriman", pengiriman);
                i.putExtra("total",total);

                startActivity(i);
            }
        });

    }





}