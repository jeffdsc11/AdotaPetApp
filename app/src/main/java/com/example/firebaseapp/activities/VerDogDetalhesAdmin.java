package com.example.firebaseapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.example.firebaseapp.fragments.HomeFragment;
import com.example.firebaseapp.functions.Imagem;
import com.example.firebaseapp.model.PetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VerDogDetalhesAdmin extends AppCompatActivity {
    TextView firstname, lastname, age, descricaoPet;
    ImageView imageView;
    Button btnInserirPet;
    Button btnDeletaPet;
    DatabaseReference ref;
    DatabaseReference reference;
    String nome;
    String raca;
    String descricao;
    String idade;
    String ImageUrl;
    Boolean login;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_dog_detalhes_admin);

        imageView = findViewById(R.id.imageViewDetalhesAdmin);
        firstname = findViewById(R.id.txtFirstnameDetalhesAdmin);
        lastname = findViewById(R.id.txtLastnameDetalhesAdmin);
        descricaoPet = findViewById(R.id.txtDescricaoDetalhesAdmin);
        age = findViewById(R.id.txtAgeDetalhesAdmin);
        btnInserirPet = findViewById(R.id.btnInserirPet);
        btnDeletaPet = findViewById(R.id.btnDeletaPet);
        String PetKey = getIntent().getStringExtra("PetKey");
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        ref = FirebaseDatabase.getInstance().getReference().child("petsAnalise");
        ref.child(PetKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nome = snapshot.child("nome").getValue().toString();
                    raca = snapshot.child("raca").getValue().toString();
                    descricao = snapshot.child("descricao").getValue().toString();
                    idade = snapshot.child("idade").getValue().toString();
                    ImageUrl = snapshot.child("imageUrlDog").getValue().toString();
                    Imagem imagem = new Imagem();
                    imagem.Show(ImageUrl,imageView);
                    firstname.setText(nome);
                    lastname.setText(raca);
                    descricaoPet.setText(descricao);
                    age.setText(idade);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnInserirPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   // mAuth = FirebaseAuth.getInstance();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    userID = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("pets").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PetModel petModel = snapshot.getValue(PetModel.class);
                            if (petModel != null) {
                                petModel.setNome(nome);
                                petModel.setRaca(raca);
                                petModel.setIdade(idade);
                                petModel.setDescricao(descricao);
                                petModel.setImageUrlDog(ImageUrl);
                                //petModel.setIdDono(mAuth.getUid());
                                petModel.InserirPetAdmin();
                                Delepet(PetKey);
                                AlertDialog.Builder builder = new AlertDialog.Builder(VerDogDetalhesAdmin.this);
                                //define o titulo
                                builder.setTitle("Tudo certo!");
                                //define a mensagem
                                builder.setMessage("Cadastro do Pet Efetuado!");
                                //Exibe
                                builder.show();
                                abrirTelaPrincipal();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            }
        });

        btnDeletaPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delepet(PetKey);
                AlertDialog.Builder builder = new AlertDialog.Builder(VerDogDetalhesAdmin.this);
                //define o titulo
                builder.setTitle("Tudo certo!");
                //define a mensagem
                builder.setMessage("Pet deletado!");
                //Exibe
                builder.show();
                abrirTelaPrincipal();
            }
        });

    }
    private void Delepet(String idPet){
        reference = FirebaseDatabase.getInstance().getReference("petsAnalise");
        reference.child(idPet).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(VerDogDetalhesAdmin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}