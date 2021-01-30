package com.wahyukurnia.erental.Order;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;


public class OrderActivity extends AppCompatActivity {
    API api;
    String id, id_user;
    TinyDB tinyDB;
    ImageView img_order;
    Button btnConfirm;
    TextView namaBarang_Order, hargaBarang;
    EditText edt_banyakSewa, edt_LamaSewa, edt_alamatPenyewa, edt_Jaminan, edt_jenis_transaksi, edt_jenis_pengiriman;
    String gambar, banyakSewa;
    String tarif;

    TextView tglAwal, tglAkhir;
    final Calendar c = Calendar.getInstance();
    static final int DATE_DIALOG_ID= 999;
    String kondisiDate = "";

    private int year;
    private int month;
    private int day;

    private static final String TAG = "OrderActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        api = new API();
        Intent i = getIntent();
        id = i.getStringExtra("id_barang");
        Log.e("order", "" + id);


        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        tglAkhir = findViewById(R.id.tgl_akhir);
        tglAwal = findViewById(R.id.tgl_awal);

        String now= LocalDate.now().toString();
        tglAwal.setText(now);
        tglAkhir.setText(now);

        tglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                kondisiDate="awal";
            }
        });
        tglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                kondisiDate="akhir";
            }
        });


        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

        img_order = findViewById(R.id.img_order);
        namaBarang_Order = findViewById(R.id.txt_judul_order);
        hargaBarang = findViewById(R.id.txt_tarif_order);

        edt_banyakSewa = findViewById(R.id.edt_banyakSewa);
        edt_LamaSewa = findViewById(R.id.edt_LamaSewa);
        edt_alamatPenyewa = findViewById(R.id.edt_alamatPenyewa);
//        edt_Jaminan = findViewById(R.id.edt_Jaminan);
        edt_jenis_transaksi = findViewById(R.id.edt_jenis_transaksi);
        edt_jenis_pengiriman = findViewById(R.id.edt_pengiriman);


        AndroidNetworking.initialize(this);
        getDataOrder();

        btnConfirm = findViewById(R.id.btnConfirm);

        if (edt_banyakSewa.getText().toString().equals("") || edt_LamaSewa.getText().toString().equals("") || edt_alamatPenyewa.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Pastikan Semua Form Terisi", Toast.LENGTH_LONG).show();
        } else {
            aksiPasangSewa();
        }

    }

    public void getDataOrder() {
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        AndroidNetworking.get(api.URL_DESKRIPSI + id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("tampilmenu", "response:" + response);
                            JSONArray res = response.getJSONArray("res");
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject data = res.getJSONObject(i);
                                int id = data.getInt("id_barang");
                                String nama = data.getString("nama_barang");
                                tarif = data.getString("tarif_barang");


                                Log.d("tarif", "ha = " + tarif);
                                namaBarang_Order.setText(nama);
                                hargaBarang.setText(formatRupiah.format((double) Integer.valueOf(tarif)) + "/Hari");
                                gambar = data.getString("gambar_barang");
                                Picasso.get().load(api.URL_GAMBAR_U + data.getString("gambar_barang")).into(img_order);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu", "response:" + anError);
                    }
                });
    }

    public void aksiPasangSewa() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String banyakSewa = edt_banyakSewa.getText().toString(); //mengambil Value etNim menjadi string
                String lamaSewa = edt_LamaSewa.getText().toString(); //mengambil Value etNim menjadi string
                String alamatPenyewa = edt_alamatPenyewa.getText().toString(); //mengambil Value etNim menjadi string
//                String jaminan = edt_Jaminan.getText().toString(); //mengambil Value etNim menjadi string
                String transaksi = "COD"; //mengambil Value etNim menjadi string
                String pengiriman = "COD"; //mengambil Value etNim menjadi string

                int total = Integer.valueOf(banyakSewa) * Integer.valueOf(tarif);
                //Handle Response
                Intent i = new Intent(OrderActivity.this, CheckoutActivity.class);
                i.putExtra("id_sewa_barang", "");
                i.putExtra("id_user", id_user);
                i.putExtra("id_barang", id);
                i.putExtra("nama_barang", namaBarang_Order.getText().toString());
                i.putExtra("tarif_barang", tarif);
                i.putExtra("gambar_barang", api.URL_GAMBAR_U + gambar);
                i.putExtra("banyak_sewa", banyakSewa);
                i.putExtra("lama_sewa", lamaSewa);
                i.putExtra("alamat_penyewa", alamatPenyewa);
//                i.putExtra("jaminan", jaminan);
                i.putExtra("tgl_awal", tglAwal.getText().toString());
                i.putExtra("tgl_akhir", tglAkhir.getText().toString());
                i.putExtra("jenis_transaksi", transaksi);
                i.putExtra("jenis_pengiriman", pengiriman);
                i.putExtra("total", total);

                startActivity(i);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;

                    StringBuilder date;
                    if (new StringBuilder().append(day).length()==1) {
                        if (new StringBuilder().append(month).length()==1){
                            date = new StringBuilder().append(year).append("-0")
                                    .append(month + 1).append("-0").append(day).append("");
                        }else {
                            date = new StringBuilder().append(year).append("-")
                                    .append(month + 1).append("-0").append(day).append("");
                        }
                    }else {
                        if (new StringBuilder().append(month).length()==1){
                            date = new StringBuilder().append(year).append("-0")
                                    .append(month + 1).append("-").append(day).append("");
                        }else {
                            date = new StringBuilder().append(year).append("-")
                                    .append(month + 1).append("-").append(day).append("");
                        }
                    }


                    if (kondisiDate.equals("akhir")) {
                        tglAkhir.setText(date);
                    }else {
                        tglAwal.setText(date);
                    }

                }
            };

}