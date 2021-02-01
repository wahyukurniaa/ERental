package com.wahyukurnia.erental.Profil;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.Detail.DetailBarangActivity;
import com.wahyukurnia.erental.Kategori.Adapter_IsiKategori;
import com.wahyukurnia.erental.Kategori.Model_IsiKategori;
import com.wahyukurnia.erental.R;

import java.util.List;

public class Adapter_Booked extends RecyclerView.Adapter<Adapter_Booked.ViewHolder> {
    Context context;
    List<Model_Booked> dataBooked;

    public Adapter_Booked(List<Model_Booked> dataBooked) {
        this.dataBooked = dataBooked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View b = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_booked,parent,false);
        ViewHolder holder = new ViewHolder(b);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Booked.ViewHolder holder, int position) {
        Model_Booked data = dataBooked.get(position);
        API api = new API();
        holder.txt_namaBarang.setText(data.getNama_barang());
        holder.txt_tanggalPinjam.setText(data.getTanggal_awal());
        holder.txt_tanggalKembali.setText(data.getTanggal_akhir());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_BookedActivity.class);
                intent.putExtra("namaBarang", data.getNama_barang());
                intent.putExtra("hargaBarang", data.getTarif_barang());
                intent.putExtra("banyakSewa", data.getBanyak_sewa());
                intent.putExtra("namaPenyewa", data.getId_user());
                intent.putExtra("tglAwal", data.getTanggal_awal());
                intent.putExtra("tglAkhir", data.getTanggal_akhir());
                intent.putExtra("alamat", data.getAlamat_penyewa());
                intent.putExtra("status", "status"); ///isi kk
                intent.putExtra("gambarBarang", data.getGambar_barang());
                intent.putExtra("gambarJaminan", data.getJaminan());
                intent.putExtra("totaSewa", data.getTotal_harga());
                context.startActivity(intent);
            }
        });
        Picasso.get().load(api.URL_GAMBAR_U+data.getGambar_barang()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return dataBooked.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_namaBarang, txt_tanggalPinjam, txt_tanggalKembali;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_namaBarang = itemView.findViewById(R.id.namaBarang);
            txt_tanggalPinjam = itemView.findViewById(R.id.txt_tgl_pinjam);
            txt_tanggalKembali = itemView.findViewById(R.id.txt_tanggal_kembali);
            img = itemView.findViewById(R.id.img_barang);
        }
    }
}
