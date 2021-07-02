package com.wahyukurnia.erental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wahyukurnia.erental.databinding.ActivityRegister1Binding;

public class Register1Activity extends AppCompatActivity {
    ActivityRegister1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegister1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namalengkap = binding.namalengkapRegister.getText().toString();
                String alamat = binding.alamatRegister.getText().toString();
                String email = binding.emailRegister.getText().toString();
                String telepon = binding.teleponRegister.getText().toString();

                if (namalengkap.equalsIgnoreCase("")||alamat.equalsIgnoreCase("")||email.equalsIgnoreCase("")||telepon.equalsIgnoreCase("")){
                    Toast.makeText(Register1Activity.this, "Harap isi semua form untuk mendaftar", Toast.LENGTH_SHORT).show();
                }else {
                    toNextRegister(namalengkap,alamat,email,telepon);
                }

            }
        });

    }

    private void toNextRegister(String namalengkap, String alamat, String email, String telepon){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("namalengkap", namalengkap);
        intent.putExtra("alamat", alamat);
        intent.putExtra("email", email);
        intent.putExtra("telepon", telepon);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}