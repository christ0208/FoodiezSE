package com.example.index;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.index.Adapter.FavoriteRecyclerViewAdapter;
import com.example.index.Adapter.RestaurantMenuRecyclerViewAdapter;
import com.example.index.Objects.RestaurantMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantMenuActivity extends AppCompatActivity {
    private RecyclerView menuRvView;
    private FirebaseFirestore firestore;
    private ArrayList<RestaurantMenu> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        firestore = FirebaseFirestore.getInstance();
        menuRvView = findViewById(R.id.menu_rv_view);

        Bundle extras = getIntent().getExtras();
        int restaurantId = extras.getInt("restaurantId");

        fetchMenus(restaurantId);
    }

    private void fetchMenus(int restaurantId) {
        firestore.collection("menu")
                .whereEqualTo("restaurant_id", restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Map map = document.getData();
                                RestaurantMenu rm = new RestaurantMenu(
                                        map.get("name").toString(),
                                        Integer.parseInt(map.get("price").toString()));

                                menus.add(rm);
                            }
                            setRecyclerView();
                        }
                    }
                });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        menuRvView.setLayoutManager(manager);
        menuRvView.setAdapter(new RestaurantMenuRecyclerViewAdapter(this, menus));
    }
}
