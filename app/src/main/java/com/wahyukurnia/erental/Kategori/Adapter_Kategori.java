package com.wahyukurnia.erental.Kategori;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.R;


import java.util.ArrayList;
import java.util.List;

public class Adapter_Kategori extends RecyclerView.Adapter<Adapter_Kategori.ViewHolder> {
    Context context;
    List<Model_Kategori> dataKategori;

    public Adapter_Kategori(List<Model_Kategori> dataKategori) {
        this.dataKategori = dataKategori;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_kategori,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Kategori.ViewHolder holder, int position) {
        Model_Kategori data =  dataKategori.get(position);
        int id_kategori =  data.getId_kategori();
        holder.txt_kategori.setText(data.getNama_kategori());
        Picasso.get().load(data.getGambar_()).into(holder.gambar);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, IsiKategoriActivity.class);
                i.putExtra("id", ""+id_kategori);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_kategori;
        ImageView gambar;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_kategori =itemView.findViewById(R.id.txt_kategori);
            gambar = itemView.findViewById(R.id.img_kategori);
            cardView = itemView.findViewById(R.id.cardview_menu);
        }
    }
}
