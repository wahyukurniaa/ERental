package com.wahyukurnia.erental.Notif;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.MainActivity;
import com.wahyukurnia.erental.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter_Notif extends RecyclerView.Adapter<Adapter_Notif.ViewHolder> {
    Context context;
    List<Model_Notif> dataNotif;
    API api;


    public Adapter_Notif(List<Model_Notif> dataNotif) {
        this.dataNotif = dataNotif;
    }

    @NonNull
    @Override
    public Adapter_Notif.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View b = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_notif,parent,false);
        ViewHolder holder = new ViewHolder(b);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Notif.ViewHolder holder, int position) {
        Model_Notif data = dataNotif.get(position);
        API api = new API();
        String id_sewa_barang =  data.getId_sewa_barang();

        holder.namaPenyewa.setText("Akan di Sewa Oleh "+data.getNama_user()+" pada");
        holder.tglNotif.setText(data.getTanggal_awal());
        holder.tglAwal.setText("Tanggal "+data.getTanggal_awal()+" sampai "+data.getTanggal_akhir());
        Picasso.get().load(api.URL_GAMBAR_U+data.getGambar_barang()).into(holder.imgBarang);
        holder.txtNama_Barang.setText(data.getNama_barang());
        holder.btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terima(data);
            }
        });
        holder.btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tolak(data);

            }
        });




    }

    private void terima(Model_Notif data) {
        API api = new API();
        AndroidNetworking.post(api.URL_KONFIRMASI)
                .addBodyParameter("id_sewa_barang",data.getId_sewa_barang())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")){
//                                Intent i = new Intent(context,MainActivity.class);
                                Toast.makeText(context, "Sewa Diterima!", Toast.LENGTH_SHORT).show();
                                String phoneNumberWithCountryCode = data.getTelp_user();
                                String message = "Hallo Saya Menyetujui Permintaan Penyewaan "+data.getNama_barang()+" Pada Tanggal "+data.getTanggal_awal();

                                context.startActivity(
                                        new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(
                                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                                )
                                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                );
                                ((NotificationActivity)context).finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
    private void tolak(Model_Notif data) {
        API api = new API();
        AndroidNetworking.post(api.URL_TOLAK)
                .addBodyParameter("id_sewa_barang",data.getId_sewa_barang())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                Intent i = new Intent(context,MainActivity.class);
                                Toast.makeText(context, "Sewa Ditolak!", Toast.LENGTH_SHORT).show();

                                context.startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    @Override
    public int getItemCount() {
        return dataNotif.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama_Barang, tglNotif, tglAwal, tglAkhir, namaPenyewa;
        ImageView imgBarang;
        Button btn_terima, btn_tolak;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tglAwal = itemView.findViewById(R.id.tgl_awal);
            tglNotif = itemView.findViewById(R.id.tglNotif);
            namaPenyewa = itemView.findViewById(R.id.namaPenyewa);
            txtNama_Barang = itemView.findViewById(R.id.judulNotif);
            btn_terima = itemView.findViewById(R.id.btnTerima);
            btn_tolak = itemView.findViewById(R.id.btnTolak);
            imgBarang = itemView.findViewById(R.id.img_barang);

        }
    }
}
