package com.example.index;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.index.Adapter.HistoryRecyclerViewAdapter;
import com.example.index.Adapter.RestaurantReviewRecyclerViewAdapter;
import com.example.index.Objects.Restaurant;
import com.example.index.Objects.RestaurantReview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.index.Objects.User;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantInfoReviewListActivity extends AppCompatActivity {
    private ArrayList<RestaurantReview> reviews = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private FirebaseFirestore firestore;
    private RecyclerView reviewRvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info_review_list);

        firestore = FirebaseFirestore.getInstance();
        reviewRvView = findViewById(R.id.review_rv_view);

        Bundle extras = getIntent().getExtras();
        Integer restaurantId = extras.getInt("restaurantId");

        fetchUser(restaurantId);
    }

    private void fetchUser(final int restaurantId){
        firestore.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:
                                 task.getResult()) {
                                Map m = document.getData();
                                User u = new User(m.get("name").toString(), m.get("email").toString(),
                                        m.get("location").toString(), m.get("phone_number").toString());
                                u.setUid(document.getId());

                                users.add(u);
                            }
                            fetchRestaurants(restaurantId);
                        }
                    }
                });
    }

    private void fetchRestaurants(final int restaurantId) {
        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Restaurant r = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map map = document.getData();
                                r = new Restaurant(Integer.parseInt(map.get("id").toString()),
                                        map.get("name").toString(), map.get("url").toString(), map.get("address").toString(),
                                        map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString(),
                                        Float.parseFloat(map.get("lat").toString()), Float.parseFloat(map.get("long").toString()));
                            }
                            fetchReviews(r, restaurantId);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void fetchReviews(final Restaurant r, int restaurantId) {
        firestore.collection("review")
                .whereEqualTo("restaurant_id", restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Map m = document.getData();

                                RestaurantReview rr = new RestaurantReview(r.getName(), r.getUrl(), m.get("user_id").toString(),
                                        convertToUserName(m.get("user_id").toString()), Float.parseFloat(m.get("rating").toString()),
                                        m.get("description").toString());
                                reviews.add(rr);
                            }

                            setRecyclerView();
                        }
                    }
                });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        reviewRvView.setLayoutManager(manager);
        reviewRvView.setAdapter(new RestaurantReviewRecyclerViewAdapter(this, reviews));
    }

    private String convertToUserName(String user_id) {
        for (User u:
             users) {
            if(u.getUid().equals(user_id)) return u.getName();
        }
        return "";
    }


}
