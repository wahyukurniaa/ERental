package com.wahyukurnia.erental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
//        btn_cari = findViewById(R.id.btncari);
        playstore();

        title = findViewById(R.id.tv_toolbar);
        title.setText("Rentalku");

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

    private void playstore(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                CFAlertDialog.Builder dialog = new CFAlertDialog.Builder(context)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                        .setTitle("New Update Available")
                        .setMessage("Update aplikasi untuk melanjutkan!!")
                        .setCancelable(false)
                        .addButton("Oke",
                                -1,
                                -1,
                                CFAlertDialog.CFAlertActionStyle.POSITIVE,
                                CFAlertDialog.CFAlertActionAlignment.END,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url = "market://details?id=com.wahyukurnia.erental";
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                        );

                dialog.show();

            }else {
//                Toast.makeText(context, " Update tidak ada", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

