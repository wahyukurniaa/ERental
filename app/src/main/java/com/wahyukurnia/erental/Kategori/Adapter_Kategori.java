package com.wahyukurnia.erental.Kategori;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wahyukurnia.erental.R;



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

    }

    @Override
    public int getItemCount() {
        return dataKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_kategori;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_kategori =itemView.findViewById(R.id.txt_kategori);
        }
    }
}
