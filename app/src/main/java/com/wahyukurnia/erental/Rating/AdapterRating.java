package com.wahyukurnia.erental.Rating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wahyukurnia.erental.Notif.Adapter_Notif;
import com.wahyukurnia.erental.R;

import java.util.List;

public class AdapterRating extends RecyclerView.Adapter<AdapterRating.ListViewHolder> {
    Context context;
    List<ModelRating>ratings;

    public AdapterRating(List<ModelRating> ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ulasan, parent, false);
        AdapterRating.ListViewHolder holder = new AdapterRating.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelRating model = ratings.get(position);
        holder.nama.setText(model.getNama_user());
        holder.tgl.setText(model.getDate());
        holder.isi.setText(model.getReview());
        holder.ratingBar.setRating((float)Double.parseDouble(model.getBintang()));

    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nama, tgl, isi, rate;
        RatingBar ratingBar;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nama = itemView.findViewById(R.id.namaUser);
            tgl = itemView.findViewById(R.id.tglUlasan);
            isi = itemView.findViewById(R.id.txtUlasan);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
