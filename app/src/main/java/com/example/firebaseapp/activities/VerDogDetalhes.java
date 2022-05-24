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
import com.example.firebaseapp.model.User;
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

public class VerDogDetalhes extends AppCompatActivity {
    TextView firstname, lastname, age, descricaoPet;
    ImageView imageView;
    Button btnAdotar;
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
        setContentView(R.layout.activity_ver_dog_detalhes);
        //VerificaLogin();


        imageView = findViewById(R.id.imageViewDetalhes);
        firstname = findViewById(R.id.txtFirstnameDetalhes);
        lastname = findViewById(R.id.txtLastnameDetalhes);
        descricaoPet = findViewById(R.id.txtDescricaoDetalhes);
        age = findViewById(R.id.txtAgeDetalhes);
        btnAdotar = findViewById(R.id.btnAdotar);
        String PetKey = getIntent().getStringExtra("PetKey");
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        //String getId =mAuth.getUid().toString();

        ref = FirebaseDatabase.getInstance().getReference().child("pets");
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



        btnAdotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(login) {
                    //mAuth = FirebaseAuth.getInstance();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    userID = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("usuarios");
                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PetModel petModel = snapshot.getValue(PetModel.class);
                            if (petModel != null) {
                                petModel.setNome(nome);
                                petModel.setRaca(raca);
                                petModel.setIdade(idade);
                                petModel.setDescricao(descricao);
                                petModel.setImageUrlDog(ImageUrl);
                                petModel.setIdDono(mAuth.getUid());
                                petModel.InserirPetUsuario();
                                Delepet(PetKey);
                                AlertDialog.Builder builder = new AlertDialog.Builder(VerDogDetalhes.this);
                                //define o titulo
                                builder.setTitle("Tudo certo!");
                                //define a mensagem
                                builder.setMessage("Cadastro do Pet Efetuado!");
                                //Exibe
                                builder.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerDogDetalhes.this);
                    //define o titulo
                    builder.setTitle("Erro!");
                    //define a mensagem
                    builder.setMessage("Voce precisa estar logado para adotar um pet! Deseja ir para a tela de login?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(VerDogDetalhes.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(VerDogDetalhes.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.show();
                }
            }
        });

    }
    private void Delepet(String idPet){
        reference = FirebaseDatabase.getInstance().getReference("pets");
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
        Intent intent = new Intent(VerDogDetalhes.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}