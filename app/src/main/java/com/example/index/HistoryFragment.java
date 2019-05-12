package com.example.index;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.index.Adapter.HistoryRecyclerViewAdapter;
import com.example.index.Adapter.RestaurantGridViewAdapter;
import com.example.index.Objects.History;
import com.example.index.Objects.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private TextView lblNotLogin;
    private RecyclerView historyRvView;
    private ProgressBar progressBar;
    private ArrayList<History> histories = new ArrayList<>();
    private FirebaseAuth mAuth;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    private FirebaseFirestore firestore;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        lblNotLogin = v.findViewById(R.id.lbl_not_login);
        historyRvView = v.findViewById(R.id.history_rv_view);
        progressBar = v.findViewById(R.id.progress_bar);

        if(mAuth.getCurrentUser() == null) {
            historyRvView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        else {
            lblNotLogin.setVisibility(View.GONE);
            fetchRestaurants(v);
        }

        return v;
    }

    private void fetchHistory(final View v) {
        histories.clear();
        firestore.collection("history")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot snapshot:
                                 task.getResult()) {
                                Map m = snapshot.getData();
                                History h = new History(Integer.parseInt(m.get("id").toString()), 
                                        convertToRestaurantName(Integer.parseInt(m.get("restaurant_id").toString())),
                                        convertToRestaurantUrl(Integer.parseInt(m.get("restaurant_id").toString())),
                                        Float.parseFloat(m.get("rating").toString()),
                                        m.get("created_at").toString());

                                if(mAuth.getCurrentUser() != null &&
                                        m.get("user_id").toString().equals(mAuth.getCurrentUser().getUid()))
                                    histories.add(h);
                            }

                            setRecyclerView(v);
                        }
                    }
                });
    }

    private void setRecyclerView(View v) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        historyRvView.setLayoutManager(manager);
        historyRvView.setAdapter(new HistoryRecyclerViewAdapter(v.getContext(), histories));
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
                            fetchHistory(v);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
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

    private String convertToRestaurantUrl(Integer restaurant_id) {
        for (Restaurant r:
                restaurants) {
            if(r.getId() == restaurant_id) return r.getUrl();
        }
        return "";
    }

}
