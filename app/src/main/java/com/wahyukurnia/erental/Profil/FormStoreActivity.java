package com.wahyukurnia.erental.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.RegisterActivity;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;

public class FormStoreActivity extends AppCompatActivity {

    Button next;
String id_user;
API api;
TinyDB tinyDB;
EditText edt_namaStore, edt_alamatStore, edt_telpStore, edt_WAStore, edt_IGStore,
    edt_gambar;
TextView title;
ImageView back;

LinearLayout addPhoto;
ImageView placePhoto;
    private static final String TAG = "FormStoreActivity";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    String tag_json_obj = "json_obj_req";
    Bitmap bitmap, decoded;

    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_store);

        title = findViewById(R.id.tv_toolbar);
        title.setText("Buat Store");

        placePhoto = findViewById(R.id.placePhoto);
        addPhoto = findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        api = new API();
        Log.d(TAG, "onCreate: inisialisasi");

        AndroidNetworking.initialize(this);

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");

        Log.e("salah", tinyDB.getString("keyIdUser"));

        edt_namaStore = findViewById(R.id.edt_namaStore);
        edt_alamatStore = findViewById(R.id.edt_alamatStore);
        edt_telpStore = findViewById(R.id.edt_telpStore);
        edt_WAStore = findViewById(R.id.edt_WAStore);
        edt_IGStore = findViewById(R.id.edt_IGStore);



        next = findViewById(R.id.btn_nextStore);

        if (tinyDB.getBoolean("keyStore")) {
            Intent intent = new Intent(FormStoreActivity.this, PasangSewaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
        aksiTambahStore();



    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        placePhoto.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void aksiTambahStore(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama_store = edt_namaStore.getText().toString(); //mengambil Value etNim menjadi string
                String alamat_store = edt_alamatStore.getText().toString(); //mengambil Value etNim menjadi string
                String telp_Store = edt_telpStore.getText().toString(); //mengambil Value etNim menjadi string
                String wa_store = edt_WAStore.getText().toString(); //mengambil Value etNim menjadi string
                String ig_store = edt_IGStore.getText().toString(); //mengambil Value etNim menjadi string
//                String gambar = edt_gambar.getText().toString(); //mengambil Value etNim menjadi string

                if (nama_store.equals("")||alamat_store.equals("")||telp_Store.equals("")||wa_store.equals("")||ig_store.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(nama_store,alamat_store,telp_Store,wa_store,ig_store); //memanggil fungsi tambahData()

                    edt_namaStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_alamatStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_telpStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_WAStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    edt_IGStore.setText(""); //mengosongkan form setelah data berhasil ditambahkan
//                    edt_gambar.setText(""); //mengosongkan form setelah data berhasil ditambahkan

                }

            }
        });





    }

    public void tambahData(String nama_store, String alamat_store, String telp_Store, String wa_store, String ig_store){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post(api.URL_Store)
                .addBodyParameter("id_store","")
                .addBodyParameter("id_user", id_user) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("nama_store", nama_store) //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("alamat_store",alamat_store) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("telp_store", telp_Store) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("wa_store", wa_store) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("ig_store", ig_store) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .addBodyParameter("gambar_store", getStringImage(decoded)) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                            Intent intent = new Intent(FormStoreActivity.this, PasangSewaActivity.class);

                            startActivity(intent);

                            tinyDB.putBoolean("keyStore",true);

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