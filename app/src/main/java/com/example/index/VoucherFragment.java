package com.example.index;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.index.Adapter.HistoryRecyclerViewAdapter;
import com.example.index.Adapter.VoucherRecyclerViewAdapter;
import com.example.index.Objects.Restaurant;
import com.example.index.Objects.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class VoucherFragment extends Fragment {
    private FirebaseFirestore firestore;

    private RecyclerView voucherRvView;
    private ProgressBar progressBar;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private ArrayList<Voucher> vouchers = new ArrayList<>();
    public VoucherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_voucher, container, false);
        firestore = FirebaseFirestore.getInstance();
        voucherRvView = v.findViewById(R.id.voucher_rv_view);
        progressBar = v.findViewById(R.id.progress_bar);

        fetchRestaurants(v);
        return v;
    }

    private void setRecyclerView(View v) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        voucherRvView.setLayoutManager(manager);
        voucherRvView.setAdapter(new VoucherRecyclerViewAdapter(v.getContext(), vouchers));
        progressBar.setVisibility(View.GONE);
    }

    private void fetchRestaurants(final View v) {
        restaurants.clear();
        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map map = document.getData();
                                Restaurant r = new Restaurant(Integer.parseInt(map.get("id").toString()),
                                        map.get("name").toString(), map.get("url").toString(), map.get("address").toString(),
                                        map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString(),
                                        Float.parseFloat(map.get("lat").toString()), Float.parseFloat(map.get("long").toString()));
                                restaurants.add(r);
                            }
                            fetchVoucher(v);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void fetchVoucher(final View v) {
        vouchers.clear();
        firestore.collection("voucher")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:
                                 task.getResult()) {
                                Map map = document.getData();

                                Voucher v = document.toObject(Voucher.class);


//                                Voucher v = new Voucher(map.get("end_date").toString(),
//                                        Integer.parseInt(map.get("min_transaction").toString()),
//                                        Integer.parseInt(map.get("discount").toString()) / 100,
//                                        map.get("terms_conditions").toString(),
//                                        map.get("url").toString(),
//                                        );
                                vouchers.add(v);
                            }
                            setRecyclerView(v);
                        }
                    }
                });
    }

    private String convertToRestaurantName(Integer restaurant_id) {
        for (Restaurant r:
                restaurants) {
            if(r.getId() == restaurant_id) return r.getName();
        }
        return "";
    }

}
