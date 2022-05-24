package com.example.firebaseapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.firebaseapp.R;
import com.example.firebaseapp.fragments.PetsAdminFragment;

public class PetAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pet_admin);

        Fragment fragment = new PetsAdminFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.body_containerPetsAdmin,fragment).commit();
    }
}