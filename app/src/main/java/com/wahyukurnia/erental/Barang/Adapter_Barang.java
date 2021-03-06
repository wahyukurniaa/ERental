package com.wahyukurnia.erental.Barang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.DetailStore.Adapter_DetailStore;
import com.wahyukurnia.erental.DetailStore.Model_DetailStore;
import com.wahyukurnia.erental.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Adapter_Barang extends RecyclerView.Adapter<Adapter_Barang.ViewHolder> {
    Context context;
    List<Model_Barang> dataBarang;
    UpdateBarang updateBarang;

    public Adapter_Barang(List<Model_Barang> dataBarang, UpdateBarang updateBarang) {
        this.dataBarang = dataBarang;
        this.updateBarang = updateBarang;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_barang_saya,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Barang.ViewHolder holder, int position) {
        Model_Barang data =  dataBarang.get(position);
        API api = new API();
        Locale localeId = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        holder.txt_NamaBarang.setText(data.getNama_barang());
        holder.txt_TarifBarang.setText(formatRupiah.format((double)Integer.valueOf(data.getTarif_barang())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,Detail_BarangSewaActivity.class);
                i.putExtra("id_barang", data.getId_barang());
                i.putExtra("id_user", data.getId_user());
                i.putExtra("namaBarang", data.getNama_barang());
                i.putExtra("hargaBarang", data.getTarif_barang());
                i.putExtra("stok", data.getStok());
                i.putExtra("nama_user", data.getNama_user());
                i.putExtra("alamat_user", data.getAlamat_user());
                i.putExtra("gambar_barang", data.getGambar_barang());
                context.startActivity(i);
            }
        });
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusItem(data);
            }
        });
        Picasso.get().load(api.URL_GAMBAR_U + data.getGambar_barang()).into(holder.img_barang);

    }

    private void hapusItem(Model_Barang data) {
        API api = new API();
        AndroidNetworking.post(api.URL_HAPUS_BARANG)
                .addBodyParameter("id", data.getId_barang())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("coode").equalsIgnoreCase("1")){
                                Toast.makeText(context, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                                updateBarang.OnUpdateBarang();
                            }else {
                                Toast.makeText(context, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_NamaBarang, txt_TarifBarang;
        ImageView img_barang, hapus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            hapus = itemView.findViewById(R.id.btnDelete);
            txt_NamaBarang = itemView.findViewById(R.id.namaBarang);
            img_barang = itemView.findViewById(R.id.img_barang);
            txt_TarifBarang = itemView.findViewById(R.id.TarifBarangg);
        }
    }
}
