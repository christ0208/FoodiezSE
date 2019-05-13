package com.example.index;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.index.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText txtName, txtEmail, txtPassword;
    CheckBox cbAgreement;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerVariables();
        registerListeners();
    }

    private void registerListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("name", txtName.getText().toString());
                                        user.put("email", txtEmail.getText().toString());
                                        user.put("location", "");
                                        user.put("phone_number", "");

                                        db.collection("user")
                                                .document(mAuth.getCurrentUser().getUid())
                                                .set(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mAuth.signOut();
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validateInputs() {
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if(name.length() == 0){
            Toast.makeText(this, "Name must not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!checkEmailFormat(email)){
            Toast.makeText(this, "Email must be in email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length() < 6){
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!checkPasswordValidity(password)){
            Toast.makeText(this, "Password must be contains at least 1 uppercase letter, 1 lowercase letter, and 1 digit", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!cbAgreement.isChecked()){
            Toast.makeText(this, "You must agree to terms and conditions to continue", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean checkPasswordValidity(String password) {
        boolean containsUppercase = checkUppercase(password);
        boolean containsLowercase = checkLowercase(password);
        boolean containsDigit = checkDigit(password);

        return containsUppercase && containsLowercase && containsDigit;
    }

    private boolean checkDigit(String password) {
        for(Character c = '0'; c <= '9'; c++){
            if(password.contains(c.toString())) return true;
        }
        return false;
    }

    private boolean checkLowercase(String password) {
        for(Character c = 'a'; c <= 'z'; c++){
            if(password.contains(c.toString())) return true;
        }
        return false;
    }

    private boolean checkUppercase(String password) {
        for (Character c = 'A'; c <= 'Z'; c++){
            if(password.contains(c.toString())) return true;
        }
        return false;
    }

    private boolean checkEmailFormat(String email) {
        if(!email.contains("@") || !email.contains(".")) return false;
        else if(email.startsWith("@") || email.endsWith("@")) return false;
        else if(email.startsWith(".") || email.endsWith(".")) return false;
        else if(email.indexOf("@") - email.lastIndexOf(".") > 0) return false;
        else if(email.indexOf("@") - email.indexOf(".") == 1 || email.indexOf("@") - email.lastIndexOf(".") == -1) return false;

        return true;
    }

    private void registerVariables() {
        btnRegister = findViewById(R.id.btn_register);

        cbAgreement = findViewById(R.id.cb_agreement);

        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
    }
}
