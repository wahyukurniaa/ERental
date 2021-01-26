package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

public class ProfilActivity extends AppCompatActivity {
    TinyDB tinyDB;
    TextView nama,alamat,email,telp, title;
    ImageView btnClose, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);



        tinyDB = new TinyDB(this);
        nama = findViewById(R.id.nama);
        nama.setText(tinyDB.getString("keyNamaUser"));
        alamat = findViewById(R.id.alamat);
        alamat.setText(tinyDB.getString("keyAlamatUser"));
        email = findViewById(R.id.email);
        email.setText(tinyDB.getString("keyEmailUser"));
        telp = findViewById(R.id.telp);
        telp.setText(tinyDB.getString("keyTelpUser"));

        title = findViewById(R.id.tv_toolbar);
        title.setText("Profil User");

        btnClose = findViewById(R.id.ib_back);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void onBackPressed(){
        Toast.makeText(this, "Tidak bisa kembali, silahkan tekan tombol Close", Toast.LENGTH_SHORT).show();
    }

}