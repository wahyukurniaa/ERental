package com.wahyukurnia.erental.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutActivity extends AppCompatActivity {
    API api;
    String id,id_user,namaUser,alamatUser;
    String nama_barang,tarif_barang,banyakSewa,gambar_barang;
    int total;
    TinyDB tinyDB;;
    ImageView img_checkout;
    TextView txt_judul_checkout,txt_tarif_checkout,txt_banyak_checkout,namaCheckout,alamatCheckout,totalCheckout;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        api = new API();

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");
        namaUser = tinyDB.getString("keyNamaUser");
        alamatUser = tinyDB.getString("keyAlamatUser");

        Intent i = getIntent();
        nama_barang = i.getStringExtra("nama_barang");
        tarif_barang = i.getStringExtra("tarif_barang");
        gambar_barang = i.getStringExtra("gambar_barang");
        banyakSewa = i.getStringExtra("banyak_sewa");
        total = i.getIntExtra("total",0);


        img_checkout = findViewById(R.id.img_checkout);
        Picasso.get().load(gambar_barang).into(img_checkout);

        txt_judul_checkout = findViewById(R.id.txt_judul_checkout);
        txt_judul_checkout.setText(""+nama_barang);

        txt_tarif_checkout = findViewById(R.id.txt_tarif_checkout);
        txt_tarif_checkout.setText(""+tarif_barang);

        txt_banyak_checkout = findViewById(R.id.txt_banyak_checkout);
        txt_banyak_checkout.setText(""+banyakSewa);

        namaCheckout = findViewById(R.id.namaCheckout);
        namaCheckout.setText(""+namaUser);

        alamatCheckout = findViewById(R.id.alamatCheckout);
        alamatCheckout.setText(""+alamatUser);

        totalCheckout = findViewById(R.id.totalCheckout);
        totalCheckout.setText(""+total);

        btnCheckout = findViewById(R.id.btnCheckout);


    }



}