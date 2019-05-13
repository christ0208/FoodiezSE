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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.Adapter.FavoriteRecyclerViewAdapter;
import com.example.index.Adapter.ReviewRecyclerViewAdapter;
import com.example.index.Objects.Restaurant;
import com.example.index.Objects.Review;
import com.example.index.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.index.Objects.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FrameLayout layoutNotLogin;
    private LinearLayout layoutSetting;
    private Button btnLogin, btnSignout;
    private TextView lblFullName, lblLocation;
    private RecyclerView reviewRvView;

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        layoutNotLogin = v.findViewById(R.id.layout_not_login);
        btnLogin = v.findViewById(R.id.btn_login);
//        btnSignout = v.findViewById(R.id.btn_signout);

        lblFullName = v.findViewById(R.id.fullname);
        lblLocation = v.findViewById(R.id.location);
        layoutSetting = v.findViewById(R.id.layout_setting);

        reviewRvView = v.findViewById(R.id.review_rv_view);

        if(mAuth.getCurrentUser() == null) {}
        else {
            layoutNotLogin.setVisibility(View.GONE);
            setAccount();
            fetchRestaurants(v);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });
//
//        btnSignout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                startActivity(new Intent(v.getContext(), MainActivity.class));
//            }
//        });
        
        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(v.getContext(), "Setting Intent", Toast.LENGTH_SHORT).show();
            }
        });

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
                                        map.get("name").toString(), map.get("url").toString(), map.get("address").toString(),
                                        map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString(),
                                        Float.parseFloat(map.get("lat").toString()), Float.parseFloat(map.get("long").toString()));
                                restaurants.add(r);
                            }
                            fetchReview(v);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setRecyclerView(View v) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        reviewRvView.setLayoutManager(manager);
        reviewRvView.setAdapter(new ReviewRecyclerViewAdapter(v.getContext(), reviews));
    }

    private void fetchReview(final View v) {
        reviews.clear();
        firestore.collection("review")
                .whereEqualTo("user_id", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document :
                                    task.getResult()) {
                                Map map = document.getData();
                                Review r = new Review(convertToRestaurantName(Integer.parseInt(map.get("restaurant_id").toString())),
                                        convertToRestaurantUrl(Integer.parseInt(map.get("restaurant_id").toString())),
                                        map.get("user_id").toString(), Float.parseFloat(map.get("rating").toString()),
                                        map.get("description").toString());
                                reviews.add(r);
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

    private String convertToRestaurantUrl(Integer restaurant_id) {
        for (Restaurant r:
                restaurants) {
            if(r.getId() == restaurant_id) return r.getUrl();
        }
        return "";
    }

    private void setAccount() {
        firestore.collection("user").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            User u = task.getResult().toObject(User.class);
                            lblFullName.setText(u.getName());
                            lblLocation.setText(u.getLocation());
                        }
                    }
                });
    }
}
