package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.RegisterActivity;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONObject;

public class FormStoreActivity extends AppCompatActivity {
Button next;
String id_user;
API api;
TinyDB tinyDB;
EditText edt_namaStore, edt_alamatStore, edt_telpStore, edt_WAStore, edt_IGStore;
    private static final String TAG = "FormStoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_store);

        api = new API();
        Log.d(TAG, "onCreate: inisialisasi");

        tinyDB = new TinyDB(this);
       id_user = tinyDB.getString("keyIdUser");

        Log.e("salah", tinyDB.getString("keyIdUser"));

        edt_namaStore = findViewById(R.id.edt_namaStore);
        edt_alamatStore = findViewById(R.id.edt_alamatStore);
        edt_telpStore = findViewById(R.id.edt_telpStore);
        edt_WAStore = findViewById(R.id.edt_WAStore);
        edt_IGStore = findViewById(R.id.edt_IGStore);


        next = findViewById(R.id.btn_nextStore);
        AndroidNetworking.initialize(getApplicationContext());
        aksiTambahStore();
    }

    public void aksiTambahStore(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = tinyDB.getString("keyIdUser");
                String nama_store = edt_namaStore.getText().toString(); //mengambil Value etNim menjadi string
                String alamat_store = edt_alamatStore.getText().toString(); //mengambil Value etNim menjadi string
                String telp_Store = edt_telpStore.getText().toString(); //mengambil Value etNim menjadi string
                String wa_store = edt_WAStore.getText().toString(); //mengambil Value etNim menjadi string
                String ig_store = edt_IGStore.getText().toString(); //mengambil Value etNim menjadi string

                if (id_user.equals("")||nama_store.equals("")||alamat_store.equals("")||telp_Store.equals("")||wa_store.equals("")||ig_store.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(id_user,nama_store,alamat_store,telp_Store,wa_store,ig_store); //memanggil fungsi tambahData()

                    edt_namaStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_alamatStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_telpStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_WAStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_IGStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan

                }

            }
        });

    }

    public void tambahData(String id_user, String nama_store, String alamat_store, String telp_Store, String wa_store, String ig_store){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post(api.URL_Store)
                .addBodyParameter("id_user", id_user) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("nama_store", nama_store) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("alamat_store",alamat_store) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("telp_store", telp_Store) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("wa_store", wa_store) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("ig_store", ig_store) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Handle Response
                        Log.e(TAG, "onResponse: " + response);
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(FormStoreActivity.this, PasangSewaActivity.class);
                        startActivity(i);
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