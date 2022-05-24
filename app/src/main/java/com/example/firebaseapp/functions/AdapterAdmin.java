package com.example.firebaseapp.functions;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdapterAdmin extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String getId =mAuth.getUid().toString();
        Query buscaAdmin = FirebaseDatabase.getInstance().getReference("usuarios")
                .child(getId).child("admin");
        buscaAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String PetKey = getIntent().getStringExtra("PetKey");

    }


}
