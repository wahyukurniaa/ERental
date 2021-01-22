package com.wahyukurnia.erental.Kategori;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wahyukurnia.erental.API;
import com.wahyukurnia.erental.LoginActivity;
import com.wahyukurnia.erental.R;
import com.wahyukurnia.erental.TinyDB;
import com.wahyukurnia.erental.slider.ModelSlider;
import com.wahyukurnia.erental.slider.SliderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Home extends Fragment {
    API api;

    private List<Model_Kategori> dataKategori;
    private RecyclerView recycler_kategori, recycler_slider;
    Adapter_Kategori adapter;
    TextView title;

    TinyDB tinyDB;
    String a;
    Button logout;

    LinearLayout main;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout koneksi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);

        tinyDB = new TinyDB(getContext());

        swipeRefreshLayout = view.findViewById(R.id.swHome);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_beranda);
        koneksi = view.findViewById(R.id.layout_koneksi);
        main = view.findViewById(R.id.mainLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataKategori();
            }
        });

        api = new API();
        AndroidNetworking.initialize(getContext());

        recycler_kategori = view.findViewById(R.id.recycler_kategori);
        recycler_kategori.setHasFixedSize(true);
        recycler_kategori.setLayoutManager(new GridLayoutManager(getContext(),4));

        List<ModelSlider> sliders;
        sliders = new ArrayList<>();

        sliders.add(new ModelSlider(1, api.URL_SLIDER+"sld1.jpg"));
        sliders.add(new ModelSlider(2, api.URL_SLIDER+"sld2.jpg"));
        sliders.add(new ModelSlider(3, api.URL_SLIDER+"sld3.jpg"));
        sliders.add(new ModelSlider(4, api.URL_SLIDER+"sld4.jpg"));


        SliderView sliderView = view.findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(sliders);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        title = view.findViewById(R.id.tv_toolbar);
        title.setText(R.string.app_name);

        dataKategori = new ArrayList<>();
        AndroidNetworking.initialize(getContext());
        getDataKategori();
        return view;


    }

    private void setLoading() {
        swipeRefreshLayout.setRefreshing(true);
        main.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        koneksi.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
    }

    private void setSukses() {
        swipeRefreshLayout.setRefreshing(false);
        shimmerFrameLayout.setVisibility(View.GONE);
        koneksi.setVisibility(View.GONE);
        main.setVisibility(View.VISIBLE);
        shimmerFrameLayout.stopShimmer();
    }

    private void setGagal() {
        swipeRefreshLayout.setRefreshing(false);
        shimmerFrameLayout.setVisibility(View.GONE);
        koneksi.setVisibility(View.VISIBLE);
        main.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
    }


    public void getDataKategori(){
        setLoading();
        AndroidNetworking.get(api.URL_Kategori)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            setSukses();
                            Log.d("tampilmenu","response:"+response);
                            JSONArray res = response.getJSONArray("res");
                            dataKategori.clear();
                            for(int i =0; i <res.length();i++){
                                JSONObject data = res.getJSONObject(i);
                                dataKategori.add(new Model_Kategori(
                                        data.getInt("id_kategori"),
                                        data.getString("nama_kategori"),
                                        api.URL_GAMBAR+data.getString("gambar_")
                                ));
                            }
                            Adapter_Kategori adapter = new Adapter_Kategori(dataKategori);
                            recycler_kategori.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        setGagal();
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }


}