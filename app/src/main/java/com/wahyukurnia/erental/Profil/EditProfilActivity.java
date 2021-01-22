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
import com.wahyukurnia.erental.Kategori.Adapter_Kategori;
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfilActivity extends AppCompatActivity {
EditText edt_namauser_register,edt_alamatuser_register,edt_emailuser_register,
        edt_telpuser_register,edt_username_register,edt_pass_register;
Button btn_edit;
API api;
TinyDB tinyDB;
String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        api = new API();
        AndroidNetworking.initialize(this);

        tinyDB = new TinyDB(this);
        id = tinyDB.getString("keyIdUser");
        

        edt_namauser_register = findViewById(R.id.edt_namauser_register);
        edt_alamatuser_register = findViewById(R.id.edt_alamatuser_register);
        edt_emailuser_register = findViewById(R.id.edt_emailuser_register);
        edt_telpuser_register = findViewById(R.id.edt_telpuser_register);
        edt_username_register = findViewById(R.id.edt_username_register);
        edt_pass_register = findViewById(R.id.edt_pass_register);

        getProfil();
        btn_edit = findViewById(R.id.btn_update);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });



    }


    public void getProfil(){
        Log.e("salah",api.URL_USER+id);
        AndroidNetworking.get(api.URL_USER+id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("tampilmenu","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                               JSONObject data = res.getJSONObject(0);
                                       String nama_user = data.getString("nama_user");
                                       String alamat_user = data.getString("alamat_user");
                                       String email_user = data.getString("email_user");
                                       String telp_user = data.getString("telp_user");
                                       String username = data.getString("username");
                                       String password = data.getString("password");

                                       edt_namauser_register.setText(nama_user);
                                       edt_alamatuser_register.setText(alamat_user);
                                       edt_emailuser_register.setText(email_user);
                                       edt_telpuser_register.setText(telp_user);
                                       edt_username_register.setText(username);
                                       edt_pass_register.setText(password);

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



    public  void update(){
        AndroidNetworking.post(api.URL_UPDATE)
                .addBodyParameter("id_user", id)
                .addBodyParameter("nama_user", edt_namauser_register.getText().toString())
                .addBodyParameter("alamat_user", edt_alamatuser_register.getText().toString())
                .addBodyParameter("email_user", edt_emailuser_register.getText().toString())
                .addBodyParameter("telp_user", edt_telpuser_register.getText().toString())
                .addBodyParameter("username", edt_username_register.getText().toString())
                .addBodyParameter("password", edt_pass_register.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                                Intent i = new Intent(EditProfilActivity.this,ProfilActivity.class);
                                startActivity(i);
                                tinyDB.putString("id_user",id);
                                tinyDB.putString("keyNamaUser",edt_namauser_register.getText().toString());
                                tinyDB.putString("keyAlamatUser",edt_alamatuser_register.getText().toString());
                                tinyDB.putString("keyEmailUser",edt_emailuser_register.getText().toString());
                                tinyDB.putString("keyTelpUser",edt_telpuser_register.getText().toString());

                                Toast.makeText(getApplicationContext(), "Data berhasil di update..."
                                        ,Toast.LENGTH_LONG).show();


                            }

                    @Override
                    public void onError(ANError error) {
                        Log.e("salah",""+error);
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }


}