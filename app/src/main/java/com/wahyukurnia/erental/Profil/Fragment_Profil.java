package com.wahyukurnia.erental.Profil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;


public class Fragment_Profil extends Fragment {
    TinyDB tinyDB;
    LinearLayout logout,btn_profil, btn_edit_profil, btn_store,btn_booked;
    TextView txt_username_profil;
    TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__profil, container, false);
        tinyDB = new TinyDB(getContext());
        txt_username_profil = view.findViewById(R.id.username_profl);
        txt_username_profil.setText(tinyDB.getString("keyNamaUser"));

        title = view.findViewById(R.id.tv_toolbar);
        title.setText("Profil User");


        logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.clear();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        btn_profil = view.findViewById(R.id.btn_profil);
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ProfilActivity.class);
                startActivity(i);
            }
        });
        btn_store = view.findViewById(R.id.btn_store);
        btn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FormStoreActivity.class);
                    startActivity(intent);
                }

        });

        btn_edit_profil = view.findViewById(R.id.btn_edit_profil);
        btn_edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditProfilActivity.class);
                startActivity(i);
            }
        });
        btn_booked = view.findViewById(R.id.btn_booked);
        btn_booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),BookedActivity.class);
                startActivity(i);
            }
        });
        return view;

    }
}