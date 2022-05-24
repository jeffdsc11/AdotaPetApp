package com.example.firebaseapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseapp.R;
import com.example.firebaseapp.activities.VerDogDetalhes;
import com.example.firebaseapp.activities.VerDogDetalhesAdmin;
import com.example.firebaseapp.model.PetModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class petAdapterAdmin extends FirebaseRecyclerAdapter<
        PetModel, petAdapterAdmin.petViewholder> {
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public petAdapterAdmin(
            @NonNull FirebaseRecyclerOptions<PetModel> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull petViewholder holder,
                     int position, @NonNull PetModel model)
    {


        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.nome.setText(model.getNome());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.raca.setText(model.getRaca());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.idade.setText(model.getIdade());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), VerDogDetalhesAdmin.class);
                intent.putExtra("PetKey",getRef(position).getKey());
                view.getContext().startActivity(intent);
            }
        });
        // Where render image from database realtime
        Picasso
                .get()
                .load(model.getImageUrlDog())
                .resize(152, 195)
                .noFade()
                .into(holder.imageView);


    }



    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public petViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person, parent, false);
        return new petAdapterAdmin.petViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class petViewholder
            extends RecyclerView.ViewHolder {

        TextView nome, raca, idade;
        ImageView imageView;
        View v;


        public petViewholder(@NonNull View itemView)
        {
            super(itemView);

            nome  = itemView.findViewById(R.id.txtFirstnameDetalhes);
            raca = itemView.findViewById(R.id.txtLastnameDetalhes);
            idade = itemView.findViewById(R.id.txtAgeDetalhes);
            imageView = itemView.findViewById(R.id.imageViewDetalhes);
            v = itemView;


        }



    }
}
