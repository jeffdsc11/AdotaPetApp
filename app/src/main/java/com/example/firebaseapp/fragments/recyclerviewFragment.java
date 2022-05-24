package com.example.firebaseapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.firebaseapp.R;

import com.example.firebaseapp.adapter.petAdapter;
import com.example.firebaseapp.model.PetModel;
import com.example.firebaseapp.model.person;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firestore.v1.StructuredQuery;


public class recyclerviewFragment extends Fragment {
    RecyclerView recyclerView;
    petAdapter adapter; // Create Object of the Adapter class


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setUpRecyclerView();

        return view;
    }



    private void setUpRecyclerView() {


        Query query = FirebaseDatabase.getInstance().getReference("pets");
        FirebaseRecyclerOptions<PetModel> options
                = new FirebaseRecyclerOptions.Builder<PetModel>()
                .setQuery(query, PetModel.class)
                .build();
        adapter = new petAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onStart()
    {

        super.onStart();
        if(adapter!= null){
            adapter.startListening();
        }

    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        if(adapter!= null){
            adapter.stopListening();
        }

    }

}