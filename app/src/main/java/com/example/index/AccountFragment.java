package com.example.index;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.index.ui.login.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LinearLayout layoutNotLogin, layoutLogin;
    private Button btnLogin, btnSignout;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        layoutLogin = v.findViewById(R.id.layout_login);
        layoutNotLogin = v.findViewById(R.id.layout_not_login);
        btnLogin = v.findViewById(R.id.btn_login);
        btnSignout = v.findViewById(R.id.btn_signout);

        if(mAuth.getCurrentUser() == null)layoutLogin.setVisibility(View.GONE);
        else layoutNotLogin.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });

        return v;
    }
}
