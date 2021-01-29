package com.wahyukurnia.erental.Profil;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    public Adapter_Booked(Context context) {
        this.context = context;
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
        int  id_sewa_barang =  data.getId_sewa_barang();
        holder.txt_namaBarang.setText(data.getNamaBarang());
        holder.txt_tanggalPinjam.setText(data.getTanggal_pinjam());
        holder.txt_tanggalKembali.setText(data.getTanggal_kembali());
//        holder.txt_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent(context, Detail_BookedActivity.class);
//////                i.putExtra("id_sewa_barang",""+id_sewa_barang);
////                context.startActivity(i);
//                Toast.makeText(context, "haha", Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.txt_tanggalKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "haha", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBooked.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_namaBarang, txt_tanggalPinjam, txt_tanggalKembali,txt_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_namaBarang = itemView.findViewById(R.id.namaBarang);
            txt_tanggalPinjam = itemView.findViewById(R.id.txt_tgl_pinjam);
            txt_tanggalKembali = itemView.findViewById(R.id.txt_tanggal_kembali);
            txt_detail = itemView.findViewById(R.id.txt_detail);
        }
    }
}
