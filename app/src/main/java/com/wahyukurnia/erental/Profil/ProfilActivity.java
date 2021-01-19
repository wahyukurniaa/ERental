package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

public class ProfilActivity extends AppCompatActivity {
    TinyDB tinyDB;
    TextView nama,alamat,email,telp;
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


    }
}