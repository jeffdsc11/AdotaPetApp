package com.example.firebaseapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseapp.R;
import com.example.firebaseapp.model.adotadosModel;
import com.example.firebaseapp.model.person;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class adotadosAdapter extends FirebaseRecyclerAdapter<adotadosModel, adotadosAdapter.adotadosViewHolder> {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public adotadosAdapter(
            @NonNull FirebaseRecyclerOptions<adotadosModel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull adotadosViewHolder holder, int position, @NonNull adotadosModel model) {


        holder.firstname.setText(model.getFirstname());

        Picasso
                .get()
                .load(model.getImage())
                .resize(152, 195)
                .noFade()
                .into(holder.imageView);

    }

    @NonNull
    @Override
    public adotadosAdapter.adotadosViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adotados, parent, false);
        return new adotadosAdapter.adotadosViewHolder(view);
    }


    public class adotadosViewHolder extends RecyclerView.ViewHolder {

        TextView firstname;
        ImageView imageView;

        public adotadosViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void adotadosViewholder(@NonNull View itemView)
        {


            firstname = itemView.findViewById(R.id.textView10);
            imageView = itemView.findViewById(R.id.perfiladotados);



        }
    }
}
