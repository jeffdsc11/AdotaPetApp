package com.example.firebaseapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.firebaseapp.R;

import java.util.List;

public class DogFragment extends Fragment {


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Need to define the child fragment layout
            View view =  inflater.inflate(R.layout.fragment_recyclerview, container, false);
            // Add the following lines to create RecyclerView


                // Aqui você instancia sua ListView

                return view;

        }

    }


