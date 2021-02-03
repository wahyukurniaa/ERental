package com.wahyukurnia.erental.DetailStore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Kategori.Adapter_Kategori;
import com.wahyukurnia.erental.Kategori.IsiKategoriActivity;
import com.wahyukurnia.erental.Kategori.Model_Kategori;
import com.wahyukurnia.erental.R;

import java.util.List;

public class Adapter_DetailStore extends RecyclerView.Adapter<Adapter_DetailStore.ViewHolder> {
    Context context;
    List<Model_DetailStore> dataStore;

    public Adapter_DetailStore(List<Model_DetailStore> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_detail_store,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_DetailStore.ViewHolder holder, int position) {
        Model_DetailStore data =  dataStore.get(position);
        API api = new API();
        holder.txt_NamaBarang.setText("Nama Barang:"+data.getNama_barang());
        holder.txt_tarifBarang.setText(data.getTarif_barang());
        holder.txt_desBarang.setText(data.getDeskripsi());
        holder.txtStokBarang.setText(data.getStok());
        Picasso.get().load(api.URL_GAMBAR_U + data.getGambar_barang()).into(holder.img_barang);


    }

    @Override
    public int getItemCount() {
        return dataStore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_NamaBarang, txt_tarifBarang, txt_desBarang, txtStokBarang;
        ImageView img_barang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_NamaBarang = itemView.findViewById(R.id.txt_NamaBarang);
            txt_tarifBarang = itemView.findViewById(R.id.txt_TarifBarang);
            txt_desBarang= itemView.findViewById(R.id.txt_DesBarang);
            txtStokBarang = itemView.findViewById(R.id.txt_StokBarang);
            img_barang = itemView.findViewById(R.id.img_barang);
        }
    }
}
