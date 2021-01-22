package com.wahyukurnia.erental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    API api;
    private static final String TAG = "MainActivity";
    EditText edt_namauser_register, edt_alamatuser_register,
             edt_emailuser_register, edt_telpuser_register,
             edt_username_register, edt_passuser_register;
    Button btn_daftar;
    TextView txtLogin;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengirim Data ....").setCancelable(false).build();
        Log.d(TAG, "onCreate: inisialisasi");
        api = new API();

        edt_namauser_register = findViewById(R.id.edt_namauser_register);
        edt_alamatuser_register = findViewById(R.id.edt_alamatuser_register);
        edt_emailuser_register = findViewById(R.id.edt_emailuser_register);
        edt_telpuser_register = findViewById(R.id.edt_telpuser_register);
        edt_username_register = findViewById(R.id.edt_username_register);
        edt_passuser_register = findViewById(R.id.edt_pass_register);
        txtLogin = findViewById(R.id.txt_Login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_daftar = findViewById(R.id.btn_daftar);
        AndroidNetworking.initialize(getApplicationContext());
        aksiTambah();
    }
    public void aksiTambah(){
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_user = edt_namauser_register.getText().toString(); //mengambil Value etNim menjadi string
                String alamat_user = edt_alamatuser_register.getText().toString(); //mengambil Value etNim menjadi string
                String email_user = edt_emailuser_register.getText().toString(); //mengambil Value etNim menjadi string
                String telp_user = edt_telpuser_register.getText().toString(); //mengambil Value etNim menjadi string
                String username = edt_username_register.getText().toString(); //mengambil Value etNim menjadi string
                String pass = edt_passuser_register.getText().toString(); //mengambil Value etNim menjadi string
                if (nama_user.equals("")||alamat_user.equals("")||email_user.equals("")||telp_user.equals("")||username.equals("")||pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(nama_user,alamat_user,email_user,telp_user,username,pass); //memanggil fungsi tambahData()

                    edt_namauser_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_alamatuser_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_emailuser_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_telpuser_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_username_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_passuser_register.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                }
            }
        });

    }

    public void tambahData(String nama_user, String alamat_user, String email_user, String telp_user, String username, String pass){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post(api.URL_REGISTER)
                .addBodyParameter("nama_user", nama_user) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("alamat_user",alamat_user) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("email_user", email_user) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("telp_user", telp_user) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("username", username) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("password", pass) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Handle Response
                        Log.d(TAG, "onResponse: " + response);
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(i);
                        //memunculkan Toast saat data berhasil ditambahkan

                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.d(TAG, "onError: Failed" + error);
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }


}