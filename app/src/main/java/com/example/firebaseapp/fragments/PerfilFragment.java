package com.example.firebaseapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.example.firebaseapp.activities.CadastroNoticia;
import com.example.firebaseapp.activities.EditPerfil;
import com.example.firebaseapp.activities.EnviaDog;
import com.example.firebaseapp.activities.Login;
import com.example.firebaseapp.activities.PetAdmin;
import com.example.firebaseapp.functions.Imagem;
import com.example.firebaseapp.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PerfilFragment extends Fragment {
     private FirebaseAuth mAuth;
     private FirebaseUser user;
     private DatabaseReference reference;
     private String userID;
     private String admin="nao";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_perfil, container, false);
        Button btnSair = (Button) view.findViewById(R.id.btnteste);
        Button btnAddNoticia = view.findViewById(R.id.btnAddNoticiaAdmin);
        Button btnAddPetAdmin = view.findViewById(R.id.btnAddpetAdmin);
        FloatingActionButton fab = view.findViewById(R.id.fab);



        btnAddPetAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PetAdmin.class);
            startActivity(intent);
            }
        });

        btnAddNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroNoticia.class);
                startActivity(intent);
            }
        });

        // Define a visibilidade conforme o usuario possuir ou não a
        // Tabela admin
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String getId =mAuth.getUid().toString();
        Query buscaAdmin = FirebaseDatabase.getInstance().getReference("usuarios")
                .child(getId).child("admin");
        buscaAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    btnAddNoticia.setVisibility(View.VISIBLE);
                    btnAddPetAdmin.setVisibility(View.VISIBLE);
                    admin = "sim";

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // System.out.println(admin);
                Intent intent = new Intent(getActivity(), EnviaDog.class);
                intent.putExtra("admin",admin);
                startActivity(intent);
                //finish();
               // Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                   //     .setAction("Action", null).show();
            }
        });

        //mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");
        userID = user.getUid();
        ImageView imagePerfil =  view.findViewById(R.id.perfil);
        final TextView txtname = (TextView) view.findViewById(R.id.perfilNome);
        //final TextView txtemail = (TextView) view.findViewById(R.id.doado);
        TextView txteditperfil = (TextView) view.findViewById(R.id.edtperfil);
        txteditperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditPerfil.class);
                startActivity(intent);
                //finish();
            }
        });


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                com.example.firebaseapp.model.User userProfile = snapshot.getValue(User.class);

                if(userProfile!= null){
                    String nome = userProfile.nome;
                    String email = userProfile.email;
                    String imageUrl = userProfile.profileUrl;
                     Imagem imagem = new Imagem();
                     imagem.Show(imageUrl,imagePerfil);

                    //txtemail.setText(email);
                    txtname.setText(nome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
               startActivity(intent);
            }
        });

        return  view;
    }

    @Override
    public  void onViewCreated(View view, Bundle savedInstanceState){
        //insertNestedFragment();
    }



    private void insertNestedFragment() {

        Fragment childFragment = new adotadosrecyclerviewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_adotados, childFragment).commit();
    }


}