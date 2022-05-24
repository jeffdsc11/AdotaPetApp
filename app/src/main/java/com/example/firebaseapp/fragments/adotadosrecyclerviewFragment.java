package com.example.firebaseapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseapp.R;
import com.example.firebaseapp.adapter.adotadosAdapter;
import com.example.firebaseapp.model.adotadosModel;
import com.example.firebaseapp.model.person;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class adotadosrecyclerviewFragment extends Fragment {
    RecyclerView recyclerView;
    adotadosAdapter adapters;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adotados, container, false);
        recyclerView = view.findViewById(R.id.fragmentadotados);





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        System.out.println(userID);
        reference = FirebaseDatabase.getInstance().getReference("usuarios").child(userID);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {


        Query query = reference.child("pets");
        FirebaseRecyclerOptions<adotadosModel> options
                = new FirebaseRecyclerOptions.Builder<adotadosModel>()
                .setQuery(query, adotadosModel.class)
                .build();
        adapters = new adotadosAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapters);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(adapters!= null){
            adapters.startListening();
        }

    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        if(adapters!= null){
            adapters.stopListening();
        }

    }

}
