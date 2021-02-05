package com.wahyukurnia.erental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wahyukurnia.erental.Kategori.Adapter_Kategori;
import com.wahyukurnia.erental.Kategori.Fragment_Home;
import com.wahyukurnia.erental.Notif.NotificationActivity;
import com.wahyukurnia.erental.Pesanan.Fragment_Pesanan;
import com.wahyukurnia.erental.PesananMasuk.Fragment_PesananMasuk;
import com.wahyukurnia.erental.Profil.Fragment_Profil;

public class MainActivity extends AppCompatActivity{
    TinyDB tinyDB;
    EditText btn_cari;
    TextView title;
    ImageView notif, msg;
    Adapter_Kategori adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btn_cari = findViewById(R.id.btncari);

        title = findViewById(R.id.tv_toolbar);
        title.setText("E Rental");

        notif = findViewById(R.id.ib_notif);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
                getFragmentPage(new Fragment_Home());
        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new Fragment_Home();
                        break;

                    case R.id.profil:
                        fragment = new Fragment_Profil();
                        break;

                    case R.id.pesanan:
                        fragment = new Fragment_Pesanan();
                        break;

                    case R.id.pesanan_masuk:
                        fragment = new Fragment_PesananMasuk();
                        break;



                    /*case R.id.notidications:
                        fragment = new Notification();
                        break;*/
                }
                return getFragmentPage(fragment);
            }
        });
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

