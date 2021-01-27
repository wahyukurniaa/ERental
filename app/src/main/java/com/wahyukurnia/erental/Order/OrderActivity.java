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
String gambar;
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
                                Picasso.get().load(api.URL_GAMBAR+data.getString("gambar_barang")).into(img_order);

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



                if (banyakSewa.equals("")||lamaSewa.equals("")||alamatPenyewa.equals("")||jaminan.equals("")||transaksi.equals("")||pengiriman.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(banyakSewa,lamaSewa,alamatPenyewa,jaminan,transaksi,pengiriman); //memanggil fungsi tambahData()

                    edt_banyakSewa.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_LamaSewa.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_alamatPenyewa.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_Jaminan.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_jenis_transaksi.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_jenis_pengiriman.setText(""); //mengosongkan form setelah data berhasil ditambahkan


                }

            }
        });

    }

    public void tambahData(String banyakSewa,String lamaSewa,String alamatPenyewa,String jaminan, String transaksi, String pengiriman){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu

        AndroidNetworking.post(api.URL_SEWA_BARANG)
                .addBodyParameter("id_sewa_barang", "")
                .addBodyParameter("id_user", id_user)
                .addBodyParameter("id_barang", id)
                .addBodyParameter("banyak_sewa", banyakSewa) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("lama_sewa",lamaSewa) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("alamat_penyewa", alamatPenyewa) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("jaminan", jaminan) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("jenis_transaksi", transaksi) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("jenis_pengiriman", pengiriman) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int total = Integer.valueOf(banyakSewa) * Integer.valueOf(tarif);
                        //Handle Response
                        Intent i = new Intent(OrderActivity.this, CheckoutActivity.class);
                        i.putExtra("nama_barang",namaBarang_Order.getText().toString());
                        i.putExtra("tarif_barang",hargaBarang.getText().toString());
                        i.putExtra("gambar_barang", api.URL_GAMBAR+gambar);
                        i.putExtra("banyak_sewa", banyakSewa+" item");
                        i.putExtra("total",total);
                        startActivity(i);

                        Log.e(TAG, "onResponse: " + response);
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();

                        //memunculkan Toast saat data berhasil ditambahkan

                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.e(TAG, "onError: Failed" + error);
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }




}