package com.example.index;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.example.index.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.index.Objects.User;


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

        if(mAuth.getCurrentUser() == null) {}
        else {
            layoutNotLogin.setVisibility(View.GONE);
            setAccount();
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
