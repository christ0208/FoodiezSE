package com.example.index;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private EditText txtName, txtPhoneNumber, txtLocation;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        txtName = findViewById(R.id.txt_name);
        txtPhoneNumber = findViewById(R.id.txt_phonenumber);
        txtLocation = findViewById(R.id.txt_location);

        btnSaveChanges = findViewById(R.id.btn_savechanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(EditProfileActivity.this, "Save Changes", Toast.LENGTH_SHORT).show();
                firestore.collection("user")
                        .document(auth.getCurrentUser().getUid())
                        .update(
                                "name", txtName.getText().toString(),
                                "phone_number", txtPhoneNumber.getText().toString(),
                                "location",txtLocation.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EditProfileActivity.this, "Success update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        fetchUser();
    }

    private void fetchUser() {
        firestore.collection("user")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Map m = task.getResult().getData();
                            txtName.setText(m.get("name").toString());
                            txtPhoneNumber.setText(m.get("phone_number").toString());
                            txtLocation.setText(m.get("location").toString());
                        }
                    }
                });
    }
}
