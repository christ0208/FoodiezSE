package com.example.index;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.index.Adapter.RestaurantGridViewAdapter;
import com.example.index.Objects.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ArrayList<Restaurant> restaurants = new ArrayList<>();

    ImageView imageSearch;
    RecyclerView restaurantGrid;
    FirebaseFirestore firestore;
   public HomeFragment() {
   }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        firestore = FirebaseFirestore.getInstance();

        imageSearch = v.findViewById(R.id.image_search);
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchActivity.class));
            }
        });

        restaurantGrid = v.findViewById(R.id.restaurant_grid);
        fetchRestaurants(v);
        return v;
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
                                        map.get("name").toString(), map.get("url").toString(), map.get("address").toString());
                                restaurants.add(r);
                            }
                            setGridView(v);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setGridView(View v) {
        RecyclerView.LayoutManager manager = new GridLayoutManager(v.getContext(), 2, GridLayoutManager.VERTICAL, false);
        restaurantGrid.setLayoutManager(manager);
        restaurantGrid.setAdapter(new RestaurantGridViewAdapter(v.getContext(), restaurants));
    }
}
