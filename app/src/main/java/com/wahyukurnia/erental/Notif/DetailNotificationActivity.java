package com.wahyukurnia.erental.Notif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.databinding.ActivityDetailNotificationBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailNotificationActivity extends AppCompatActivity {
    private ActivityDetailNotificationBinding binding;
    TextView title;
    ImageView back;
    String idsewa,gambarsewa,namapenyewa,tglawal,tglakhir,alamat,status,jumlahsewa,harga,jaminan,telp,namabarang;
    Button btnTerima, btnTolak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = findViewById(R.id.tv_toolbar);
        title.setText("Detail Notifikasi");

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getdataintent();

        binding.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terima(idsewa);
            }
        });

        binding.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tolak(idsewa);
            }
        });

    }

    private void getdataintent(){
        API api = new API();
        Intent intent = getIntent();

        idsewa = intent.getStringExtra("idsewa");
        gambarsewa = intent.getStringExtra("gambarsewa");
        namapenyewa = intent.getStringExtra("namapenyewa");
        tglawal = intent.getStringExtra("tglawal");
        tglakhir = intent.getStringExtra("tglakhir");
        alamat = intent.getStringExtra("alamat");
        status = intent.getStringExtra("status");
        jumlahsewa = intent.getStringExtra("jumlahsewa");
        harga = intent.getStringExtra("harga");
        jaminan = intent.getStringExtra("jaminan");
        telp = intent.getStringExtra("telp");
        namabarang = intent.getStringExtra("namabarang");

        Picasso.get().load(api.URL_GAMBAR_U+gambarsewa).placeholder(R.drawable.website).into(binding.imgDetailNotif);
        binding.namaDetailNotif.setText(namapenyewa);
        binding.tgl1DetailNotif.setText(tglawal);
        binding.tgl2DetailNotif.setText(tglakhir);
        binding.alamatDetailNotif.setText(alamat);
        binding.statusDetailNotif.setText(status);
        binding.jumlahDetailNotif.setText(jumlahsewa);
        binding.totalDetailNotif.setText(harga);
        Picasso.get().load(api.URL_GAMBAR_U+jaminan).placeholder(R.drawable.website).into(binding.jaminanDetailNotif);

    }

    private void terima(String idsewa) {
        API api = new API();
        AndroidNetworking.post(api.URL_KONFIRMASI)
                .addBodyParameter("id_sewa_barang",idsewa)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")){

                                Toast.makeText(getApplicationContext(), "Sewa Diterima!", Toast.LENGTH_SHORT).show();

                                String phoneNumberWithCountryCode = "+62"+telp.substring(1);
                                String message = "Hallo Saya Menyetujui Permintaan Penyewaan "+namabarang+" Pada Tanggal "+tglawal;

                                startActivity(
                                        new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(
                                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                                )
                                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                );
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
    private void tolak(String idsewa) {
        API api = new API();
        AndroidNetworking.post(api.URL_TOLAK)
                .addBodyParameter("id_sewa_barang",idsewa)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), "Sewa Ditolak!", Toast.LENGTH_SHORT).show();

                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}