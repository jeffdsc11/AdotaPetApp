package com.example.firebaseapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.firebaseapp.R;


public class
HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        return view ;
    }


    @Override
    public  void onViewCreated(View view, Bundle savedInstanceState){

        insertNestedFragment();
    }

    private void insertNestedFragment() {

        Fragment childFragment = new recyclerviewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }
}

