package com.wahyukurnia.erental.Rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONObject;

public class RatingActivity extends AppCompatActivity {
    TextView getRating;
    Button Submit;
    AppCompatRatingBar RatingBar;
    EditText edtUlasan;
    private static final String TAG = "RatingActivity";
    API api;
    String id_barang,id_user;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        api = new API();
        AndroidNetworking.initialize(this);

        Intent i= new Intent(getIntent());
        id_barang = i.getStringExtra("id_barang");

        Log.e("id_barang",id_barang);
        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");
        Log.e("id_user",id_user);



        getRating = findViewById(R.id.rate);
        Submit = findViewById(R.id.submit);
        RatingBar = findViewById(R.id.penilaian);
        edtUlasan = findViewById(R.id.edtUlasan);

        RatingBar.setOnRatingBarChangeListener(new android.widget.RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float nilai, boolean b) {

                getRating.setText("" + nilai);
            }
        });
        aksiTambahUlasan();

    }

    public void aksiTambahUlasan() {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ulasan = edtUlasan.getText().toString(); //mengambil Value etNim menjadi string
                String rating = getRating.getText().toString(); //mengambil Value etNim menjadi string
                Toast.makeText(getApplicationContext(), "Terima Kasih", Toast.LENGTH_SHORT).show();
                if (ulasan.equals("") || rating.equals("")) {
                    Toast.makeText(getApplicationContext(), "Semua data harus diisi", Toast.LENGTH_SHORT).show();

                } else {
                    tambahData(ulasan,  rating); //memanggil fungsi tambahData()

                    edtUlasan.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    getRating.setText(""); //mengosongkan form setelah data berhasil ditambahkan


                }
            }
        });
    }

    public void tambahData(String ulasan, String rating){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post(api.URL_RATING)
                .addBodyParameter("id_ulasan","")
                .addBodyParameter("id_barang",id_barang) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("id_user",id_user) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("review", ulasan) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("bintang",rating) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                        startActivity(intent);

                        //Handle Response
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
