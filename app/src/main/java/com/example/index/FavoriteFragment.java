package com.example.index;


import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.example.index.Adapter.FavoriteRecyclerViewAdapter;
import com.example.index.Adapter.HistoryRecyclerViewAdapter;
import com.example.index.Objects.Favorite;
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
public class FavoriteFragment extends Fragment {

//    RelativeLayout layout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private ArrayList<Favorite> favorites = new ArrayList<>();

    private RecyclerView favoriteRvView;
    private ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        favoriteRvView = v.findViewById(R.id.favorite_rv_view);
        progressBar = v.findViewById(R.id.progress_bar);
//        layout = v.findViewById(R.id.layout_click);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(v.getContext(), RestaurantInfoActivity.class));
//            }
//        });

        fetchRestaurants(v);

        return v;
    }

    private void setRecyclerView(View v) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        favoriteRvView.setLayoutManager(manager);
        favoriteRvView.setAdapter(new FavoriteRecyclerViewAdapter(favorites,v.getContext()));
        progressBar.setVisibility(View.GONE);
    }

    private void fetchFavorites(final View v){
        favorites.clear();
        firestore.collection("favorite")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:
                                    task.getResult()) {
                                Map map = document.getData();
                                Favorite f = new Favorite(Integer.parseInt(map.get("id").toString()),
                                        convertToRestaurantName(Integer.parseInt(map.get("restaurant_id").toString())),
                                        convertToRestaurantUrl(Integer.parseInt(map.get("restaurant_id").toString())),
                                        convertToRestaurantAddress(Integer.parseInt(map.get("restaurant_id").toString())),
                                        map.get("user_id").toString());
                                favorites.add(f);
                            }
                            setRecyclerView(v);
                        }
                    }
                });
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
                                        map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString());
                                restaurants.add(r);
                            }
                            fetchFavorites(v);
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

    private String convertToRestaurantAddress(Integer restaurant_id) {
        for (Restaurant r:
                restaurants) {
            if(r.getId() == restaurant_id) return r.getAddress();
        }
        return "";
    }

}
