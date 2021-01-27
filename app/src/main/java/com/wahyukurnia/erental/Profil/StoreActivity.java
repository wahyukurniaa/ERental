package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

public class StoreActivity extends AppCompatActivity {

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        tinyDB = new TinyDB(this);

        Button daftar_store = findViewById(R.id.btn_daftarStore);
        daftar_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tinyDB.getBoolean("id_store")) {
                    Intent intent = new Intent(StoreActivity.this, PasangSewaActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                    else{
                        Intent i = new Intent(StoreActivity.this, FormStoreActivity.class);
                        startActivity(i);
                    }

                }

        });


        }
    }
