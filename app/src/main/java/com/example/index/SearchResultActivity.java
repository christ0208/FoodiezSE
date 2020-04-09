package com.example.index;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.Adapter.RestaurantGridViewAdapter;
import com.example.index.Objects.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {
//    TextView lblResult;
    EditText txtSearch;
    ImageView btnBack;
    RecyclerView restaurantGrid;
    ProgressBar progressBar;

    ArrayList<Restaurant> restaurants = new ArrayList<>();

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        firestore = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();

        String result = extras.getString("search");

        registerVariables();
        registerListeners();
        registerAdapters();

        txtSearch.setText(result);
        fetchRestaurants(result);
    }

    private void registerAdapters() {
    }

    private void registerListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.finish();
            }
        });
    }

    private void registerVariables() {
        btnBack = findViewById(R.id.btn_back);
        txtSearch = findViewById(R.id.txt_search);
        restaurantGrid = findViewById(R.id.restaurant_grid);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void fetchRestaurants(final String searchKey) {
        restaurants.clear();
        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("name").toString().toLowerCase().contains(searchKey)) {
                                    Map map = document.getData();
                                    Restaurant r = new Restaurant(Integer.parseInt(map.get("id").toString()),
                                            map.get("name").toString(), map.get("url").toString(), map.get("address").toString(),
                                            map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString(),
                                            Float.parseFloat(map.get("lat").toString()), Float.parseFloat(map.get("long").toString()));
                                    restaurants.add(r);
                                }
                            }
                            sortRestaurantLocation();
                            setGridView();
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void sortRestaurantLocation() {
        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return phytagoras(o1) - phytagoras(o2);
            }

            private int phytagoras(Restaurant o1) {
                return (int) Math.round(Math.sqrt(o1.getLatitude() + o1.getLongitude()));
            }
        });
    }

    private void setGridView() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        restaurantGrid.setLayoutManager(manager);
        restaurantGrid.setAdapter(new RestaurantGridViewAdapter(this, restaurants));
        progressBar.setVisibility(View.GONE);
    }
}
