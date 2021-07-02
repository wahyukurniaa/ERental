package com.wahyukurnia.erental.Kategori;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Detail.DetailBarangActivity;
import com.wahyukurnia.erental.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Adapter_IsiKategori extends RecyclerView.Adapter<Adapter_IsiKategori.ViewHolder> {
    Context context;
    List<Model_IsiKategori> dataIsiKategori;

    public Adapter_IsiKategori(List<Model_IsiKategori> dataIsiKategori) {
        this.dataIsiKategori = dataIsiKategori;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View a = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_isi_kategori, parent, false);
        ViewHolder holder = new ViewHolder(a);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_IsiKategori.ViewHolder holder, int position) {
        API api = new API();
        Model_IsiKategori data = dataIsiKategori.get(position);
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        String id_barang = data.getId_barang();

        holder.txt_store1.setText(data.getNama_store());
        holder.txt_namaKategori.setText(data.getNama_barang());
        holder.txt_tarif_kategori.setText(formatRupiah.format((double)Integer.valueOf(data.getTarif_barang())));
        Picasso.get().load(api.URL_GAMBAR_U + data.getGambar_barang()).into(holder.img_kategori);

        holder.txt_alamat.setText(data.getAlamat_store());
        holder.txt_store2.setText(data.getNama_store());


        holder.cardviewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(data.getStok()) > 0) {
                    Intent i = new Intent(context, DetailBarangActivity.class);
                    i.putExtra("id_barang", "" + id_barang);
                    i.putExtra("id_user", data.getId_user());
                    i.putExtra("id_store",data.getId_store());
                    i.putExtra("nama_barang", data.getNama_barang());
                    i.putExtra("tarif_barang", data.getTarif_barang());
                    i.putExtra("deskripsi", data.getDeskripsi());
                    i.putExtra("stok", data.getStok());
                    i.putExtra("nama_store", data.getNama_store());
                    i.putExtra("alamat_store", data.getAlamat_store());
                    i.putExtra("telp_store", data.getTelp_store());
                    i.putExtra("wa_store", data.getWa_store());
                    i.putExtra("gambar_barang", data.getGambar_barang());
                    i.putExtra("nama_user", data.getNama_user());
                    i.putExtra("gambar_toko", data.getGambar_store());
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Stok Produk Habis", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Integer.valueOf(data.getStok())>0){
//            holder.habis.setVisibility(View.GONE);
//            holder.content.setVisibility(View.VISIBLE);
            holder.habis.setVisibility(View.GONE);

        }else {
//            holder.habis.setVisibility(View.VISIBLE);
//            holder.content.setVisibility(View.GONE);

            holder.habis.setVisibility(View.VISIBLE);
            holder.cardviewDetail.setClickable(false);
        }


    }

    @Override
    public int getItemCount() {
        return dataIsiKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_namaKategori, txt_tarif_kategori, txt_store1, txt_alamat, txt_store2;
        ImageView img_kategori;
        RelativeLayout detailIsiKategori, habis;
        LinearLayout content ;
        CardView cardviewDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            habis = itemView.findViewById(R.id.habisLayout);
            content = itemView.findViewById(R.id.isiLayout);
            txt_namaKategori = itemView.findViewById(R.id.txt_isi_kategori);
            txt_tarif_kategori = itemView.findViewById(R.id.txt_tarif);
            img_kategori = itemView.findViewById(R.id.img_isi_kategori);
            detailIsiKategori = itemView.findViewById(R.id.cardview_isi_kategori);

            cardviewDetail = itemView.findViewById(R.id.cardview_detail_kategori);

            txt_store1 = itemView.findViewById(R.id.store_isi_kategori);
            txt_alamat = itemView.findViewById(R.id.alamat_isi_kategori);
            txt_store2 = itemView.findViewById(R.id.store2_isi_kategori);
        }
    }
}
