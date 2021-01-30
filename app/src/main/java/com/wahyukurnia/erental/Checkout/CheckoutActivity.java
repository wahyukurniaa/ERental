package com.wahyukurnia.erental.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.Profil.PasangSewaActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    API api;
    String id_barang,id_user,namaUser,alamatUser;
    String nama_barang,tarif_barang,banyakSewa,gambar_barang;
    String id_sewa_barang,lama_sewa,alamat_penyewa,jaminan,jenis_transaksi,jenis_pengiriman;
    String tglAwal, tglAkhir;
    int total;
    TinyDB tinyDB;;
    ImageView img_checkout;
    TextView txt_judul_checkout,txt_tarif_checkout,txt_banyak_checkout,namaCheckout,alamatCheckout,totalCheckout;
    Button btnCheckout;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    String tag_json_obj = "json_obj_req";
    Bitmap bitmap, decoded;

    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    RelativeLayout pickImg;
    ImageView setImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        api = new API();

        tinyDB = new TinyDB(this);
        id_user = tinyDB.getString("keyIdUser");
        namaUser = tinyDB.getString("keyNamaUser");
        alamatUser = tinyDB.getString("keyAlamatUser");

        Intent i = getIntent();
        id_barang = i.getStringExtra("id_barang");
        nama_barang = i.getStringExtra("nama_barang");
        tarif_barang = i.getStringExtra("tarif_barang");
        gambar_barang = i.getStringExtra("gambar_barang");
        banyakSewa = i.getStringExtra("banyak_sewa");
        total = i.getIntExtra("total",0);
        id_sewa_barang = i.getStringExtra("id_sewa_barang");
        lama_sewa = i.getStringExtra("lama_sewa");
        alamat_penyewa = i.getStringExtra("alamat_penyewa");
//        jaminan = i.getStringExtra("jaminan");
        jenis_transaksi = i.getStringExtra("jenis_transaksi");
        jenis_pengiriman = i.getStringExtra("jenis_pengiriman");
        tglAwal = i.getStringExtra("tgl_awal");
        tglAkhir = i.getStringExtra("tgl_akhir");

        setImg = findViewById(R.id.setFoto);
        pickImg = findViewById(R.id.addPhoto);
        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        img_checkout = findViewById(R.id.img_checkout);
        Picasso.get().load(gambar_barang).into(img_checkout);

        txt_judul_checkout = findViewById(R.id.txt_judul_checkout);
        txt_judul_checkout.setText(""+nama_barang);

        txt_tarif_checkout = findViewById(R.id.txt_tarif_checkout);
        txt_tarif_checkout.setText(""+formatRupiah.format((double)Integer.valueOf(tarif_barang)));

        txt_banyak_checkout = findViewById(R.id.txt_banyak_checkout);
        txt_banyak_checkout.setText(""+banyakSewa+" item");

        namaCheckout = findViewById(R.id.namaCheckout);
        namaCheckout.setText(""+namaUser);

        alamatCheckout = findViewById(R.id.alamatCheckout);
        alamatCheckout.setText(""+alamatUser);

        totalCheckout = findViewById(R.id.totalCheckout);
        totalCheckout.setText(""+formatRupiah.format((double)total));

        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahData();

            }
        });


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
        setImg.setImageBitmap(decoded);
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



    public void tambahData(){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu

        AndroidNetworking.post(api.URL_SEWA_BARANG)
                .addBodyParameter("id_sewa_barang", "")
                .addBodyParameter("id_user", id_user)
                .addBodyParameter("id_barang", id_barang)
                .addBodyParameter("banyak_sewa", banyakSewa)
                .addBodyParameter("tglAwal", tglAwal)
                .addBodyParameter("tglAkhir", tglAkhir)
                .addBodyParameter("alamat_penyewa", alamat_penyewa)
                .addBodyParameter("jaminan", getStringImage(decoded))
                .addBodyParameter("jenis_transaksi", "COD")
                .addBodyParameter("jenis_pengiriman", "COD")
                .addBodyParameter("total_harga", ""+total)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("total",""+response);
                        //Handle Response
                        try {
                            if(response.getString("status").equalsIgnoreCase("sukses")){
                                Intent i = new Intent(CheckoutActivity.this, MainActivity.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CheckoutActivity.this, "Data Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan =" +error , Toast.LENGTH_SHORT).show();
                        Log.d("eror","ad"+error);
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }



}