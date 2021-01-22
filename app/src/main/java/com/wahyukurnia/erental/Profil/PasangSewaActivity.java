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
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PasangSewaActivity extends AppCompatActivity {
    EditText edt_namaBarang,edt_tarif,edt_stokBarang,edtdeskripsi,edt_input_gambar;
    Button pasang_sewa;
    API api;
    Spinner List;
//    private String[] Item= new ArrayList;
    ArrayList<String> dataKategori = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang_sewa);
        api = new API();
        AndroidNetworking.initialize(this);

        List = findViewById(R.id.spinner_Kategori);
        getDataKategori();

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

                                        String gambar =api.URL_GAMBAR+data.getString("gambar_");

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
                int a  = (int) adapter.getItemId(i)+1;
                Toast.makeText(getApplicationContext(), "Saya Memesan " + a, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
    }


}