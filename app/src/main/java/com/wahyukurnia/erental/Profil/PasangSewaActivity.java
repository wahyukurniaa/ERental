package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Kategori.Adapter_Kategori;
import com.wahyukurnia.erental.Kategori.Fragment_Home;
import com.wahyukurnia.erental.Kategori.IsiKategoriActivity;
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PasangSewaActivity extends AppCompatActivity {
    EditText edt_namaBarang,edt_tarif,edt_stokBarang,edtdeskripsi,edt_input_gambar;
    Button pasang_sewa;
    API api;
    Spinner List;
    String id_user,id_store;
    int a;

    TinyDB tinyDB;
//    private String[] Item= new ArrayList;
    ArrayList<String> dataKategori = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final String TAG = "PasangSewaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang_sewa);
        api = new API();
        AndroidNetworking.initialize(this);


        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");


        List = findViewById(R.id.spinner_Kategori);
        getDataKategori();

        edt_namaBarang = findViewById(R.id.edt_namaBarang);
        edt_tarif = findViewById(R.id.edt_tarif);
        edt_stokBarang = findViewById(R.id.edt_stokBarang);
        edtdeskripsi = findViewById(R.id.edtdeskripsi);
        edt_input_gambar = findViewById(R.id.edt_input_gambar);

        pasang_sewa = findViewById(R.id.btn_pasangSewa);
        aksiPasangSewa();
    }

    public void getDataKategori(){
        AndroidNetworking.get(api.URL_Kategori)
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
                                String nama = data.getString("nama_kategori");
                                dataKategori.add(nama);

                                       int id = data.getInt("id_kategori");

//                                        String gambar =api.URL_GAMBAR+data.getString("gambar_");

//                                        Item[i] = nama;
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
                android.R.layout.simple_spinner_dropdown_item, dataKategori);

        //Memasukan Adapter pada Spinner
        List.setAdapter(adapter);

        //Mengeset listener untuk mengetahui event/aksi saat item dipilih
        List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                a  = (int) adapter.getItemId(i)+1;
                Toast.makeText(getApplicationContext(), "Saya Memesan " + a, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
    }

    public void aksiPasangSewa(){
        pasang_sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = edt_namaBarang.getText().toString(); //mengambil Value etNim menjadi string
                String tarifBarang = edt_tarif.getText().toString(); //mengambil Value etNim menjadi string
                String stokBarang = edt_stokBarang.getText().toString(); //mengambil Value etNim menjadi string
                String deskripsi = edtdeskripsi.getText().toString(); //mengambil Value etNim menjadi string
                String inputGambar = edt_input_gambar.getText().toString(); //mengambil Value etNim menjadi string


                if (namaBarang.equals("")||tarifBarang.equals("")||stokBarang.equals("")||deskripsi.equals("")||inputGambar.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(namaBarang,tarifBarang,stokBarang,deskripsi,inputGambar); //memanggil fungsi tambahData()

                    edt_namaBarang.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_tarif.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_stokBarang.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edtdeskripsi.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_input_gambar.setText(""); //mengosongkan form setelah data berhasil ditambahkan

                }

            }
        });

    }

    public void tambahData(String namaBarang,String tarifBarang,String stokBarang,String deskripsi,String inputGambar){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        Log.e("salah",""+api.URL_BARANG);
        AndroidNetworking.post(api.URL_BARANG)
                .addBodyParameter("id_barang", "")
                .addBodyParameter("id_user", id_user)
                .addBodyParameter("id_kategori", String.valueOf(a))
                .addBodyParameter("nama_barang", namaBarang) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("tarif_barang",tarifBarang) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("stok", stokBarang) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("deskripsi", deskripsi) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("gambar_barang", inputGambar) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Handle Response
                        Intent i = new Intent(PasangSewaActivity.this, MainActivity.class);
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