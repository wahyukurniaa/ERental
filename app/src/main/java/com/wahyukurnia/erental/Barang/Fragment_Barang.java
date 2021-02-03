package com.wahyukurnia.erental.Barang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.DetailStore.Adapter_DetailStore;
import com.wahyukurnia.erental.DetailStore.Model_DetailStore;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Barang extends Fragment {
    API api;
    private List<Model_Barang> dataBarang;
    private RecyclerView recycler_barang;
    TinyDB tinyDB;
    String id_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__barang, container, false);

        api = new API();
        AndroidNetworking.initialize(getContext());

        tinyDB = new TinyDB(getContext());
        id_user = tinyDB.getString("keyIdUser");

        recycler_barang = view.findViewById(R.id.recycler_barang);
        recycler_barang.setHasFixedSize(true);
        recycler_barang.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dataBarang = new ArrayList<>();


        getBarang();
        return view;

    }
    public void getBarang(){
        AndroidNetworking.get(api.URL_BARANG_DISEWAKAN+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            Log.d("tampil Store","response:"+response+id_user);

                            dataBarang.clear();

                            JSONArray res = response.getJSONArray("res");
                            Gson gson = new Gson();
                            for (int i=0; i<res.length(); i++){
                                JSONObject data = res.getJSONObject(i);

                                Model_Barang Isi = gson.fromJson(data + "", Model_Barang.class);
                                dataBarang.add(Isi);

                            }
                            Adapter_Barang adapter = new Adapter_Barang(dataBarang);
                            recycler_barang.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }
}