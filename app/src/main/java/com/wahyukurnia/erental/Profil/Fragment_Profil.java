package com.wahyukurnia.erental.Profil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;


public class Fragment_Profil extends Fragment {
    TinyDB tinyDB;
    Button logout,btn_profil, btn_edit_profil, btn_store;
    TextView txt_username_profil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__profil, container, false);
        tinyDB = new TinyDB(getContext());
        txt_username_profil = view.findViewById(R.id.username_profl);
        txt_username_profil.setText(tinyDB.getString("keyNamaUser"));

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
                Intent i = new Intent(getContext(),StoreActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}