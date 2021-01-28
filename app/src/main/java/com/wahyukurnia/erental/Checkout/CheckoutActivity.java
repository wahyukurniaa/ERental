package com.wahyukurnia.erental.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
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
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.Profil.PasangSewaActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutActivity extends AppCompatActivity {
    API api;
    String id_barang,id_user,namaUser,alamatUser;
    String nama_barang,tarif_barang,banyakSewa,gambar_barang;
    String id_sewa_barang,lama_sewa,alamat_penyewa,jaminan,jenis_transaksi,jenis_pengiriman;
    int total;
    TinyDB tinyDB;;
    ImageView img_checkout;
    TextView txt_judul_checkout,txt_tarif_checkout,txt_banyak_checkout,namaCheckout,alamatCheckout,totalCheckout;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

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
        jaminan = i.getStringExtra("jaminan");
        jenis_transaksi = i.getStringExtra("jenis_transaksi");
        jenis_pengiriman = i.getStringExtra("jenis_pengiriman");




        img_checkout = findViewById(R.id.img_checkout);
        Picasso.get().load(gambar_barang).into(img_checkout);

        txt_judul_checkout = findViewById(R.id.txt_judul_checkout);
        txt_judul_checkout.setText(""+nama_barang);

        txt_tarif_checkout = findViewById(R.id.txt_tarif_checkout);
        txt_tarif_checkout.setText(""+tarif_barang);

        txt_banyak_checkout = findViewById(R.id.txt_banyak_checkout);
        txt_banyak_checkout.setText(""+banyakSewa);

        namaCheckout = findViewById(R.id.namaCheckout);
        namaCheckout.setText(""+namaUser);

        alamatCheckout = findViewById(R.id.alamatCheckout);
        alamatCheckout.setText(""+alamatUser);

        totalCheckout = findViewById(R.id.totalCheckout);
        totalCheckout.setText(""+total);

        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahData();

            }
        });


    }




    public void tambahData(){
        //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu

        AndroidNetworking.post(api.URL_SEWA_BARANG)
                .addBodyParameter("id_sewa_barang", "")
                .addBodyParameter("id_user", id_user)
                .addBodyParameter("id_barang", id_barang)
                .addBodyParameter("banyak_sewa", banyakSewa)
                .addBodyParameter("lama_sewa", lama_sewa)
                .addBodyParameter("alamat_penyewa", alamat_penyewa)
                .addBodyParameter("jaminan", jaminan)
                .addBodyParameter("jenis_transaksi", jenis_transaksi)
                .addBodyParameter("jenis_pengiriman", jenis_pengiriman)
                .addBodyParameter("total_harga", ""+total)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("total",""+total);
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
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }



}