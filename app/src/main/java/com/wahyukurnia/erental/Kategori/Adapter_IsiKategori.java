package com.wahyukurnia.erental.Kategori;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.R;

import java.util.List;

public class Adapter_IsiKategori extends RecyclerView.Adapter<Adapter_IsiKategori.ViewHolder> {
    Context context;
    List<Model_IsiKategori> dataIsiKategori;

    public Adapter_IsiKategori(List<Model_IsiKategori> dataIsiKategori) {
        this.dataIsiKategori = dataIsiKategori;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View a = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_isi_kategori,parent,false);
        ViewHolder holder = new ViewHolder(a);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_IsiKategori.ViewHolder holder, int position) {
        Model_IsiKategori data = dataIsiKategori.get(position);
        holder.txt_namaKategori.setText(data.getNama_barang());
        holder.txt_tarif_kategori.setText(data.getTarif());
        Picasso.get().load(data.getGambar_barang()).into(holder.img_kategori);

    }

    @Override
    public int getItemCount() {
        return dataIsiKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_namaKategori,txt_tarif_kategori;
        ImageView img_kategori;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_namaKategori =itemView.findViewById(R.id.txt_isi_kategori);
            txt_tarif_kategori = itemView.findViewById(R.id.txt_tarif);
            img_kategori = itemView.findViewById(R.id.img_isi_kategori);
        }
    }
}
