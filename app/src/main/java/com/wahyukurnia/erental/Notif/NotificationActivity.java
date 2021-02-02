package com.wahyukurnia.erental.Notif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.Profil.Adapter_Booked;
import com.wahyukurnia.erental.Profil.Model_Booked;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    API api;
    List<Model_Notif> dataNotif;
    private RecyclerView recycler_notif;
    Adapter_Notif adapter;
    TextView title;
    ImageView back;
    TinyDB tinyDB;
    String id_user;
    Button btn_terima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        api = new API();
        AndroidNetworking.initialize(this);

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

        recycler_notif = findViewById(R.id.recycler_notif);
        recycler_notif.setHasFixedSize(true);

        btn_terima = findViewById(R.id.btnTerima);

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title = findViewById(R.id.tv_toolbar);
        title.setText("Notifikasi");


        dataNotif = new ArrayList<>();
        getNotif();
    }
    public void getNotif(){
        Log.e("salah",api.URL_NOTIF+id_user);
        AndroidNetworking.get(api.URL_NOTIF+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampilmenu","response:"+response);
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataNotif.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Notif notif = gson.fromJson(data + "", Model_Notif.class);
                                    dataNotif.add(notif);
                                }
                                Adapter_Notif adapter = new Adapter_Notif(dataNotif);
                                recycler_notif.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(NotificationActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
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
}