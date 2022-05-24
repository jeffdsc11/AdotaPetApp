package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.firebaseapp.activities.CadastroNoticia;
import com.example.firebaseapp.activities.Login;
import com.example.firebaseapp.fragments.AcessDeniedFragment;
import com.example.firebaseapp.fragments.DicaFragment;
import com.example.firebaseapp.fragments.DoarFragment;
import com.example.firebaseapp.fragments.HomeFragment;
import com.example.firebaseapp.fragments.PerfilFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //private Button btnSair;
    boolean login;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        navigationView = findViewById(R.id.bottom_navigator);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_dica:
                        fragment = new DicaFragment();
                        break;
                    case R.id.nav_doar:
                        fragment = new DoarFragment();
                        break;
                    case R.id.nav_perfil:
                        VerificaLogin();
                        if(login){
                            fragment = new PerfilFragment();
                        }else {

                            fragment = new AcessDeniedFragment();

                            break;
                        }


                       // fragment = new PerfilFragment();

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                return true;
            }
        });




/*     BOTAO DESLOGAR IMPLEMENTAÇÃO
        btnSair = findViewById(R.id.BtnLogout);
        mAuth = FirebaseAuth.getInstance();
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

 */
    }

    private void VerificaLogin() {
        Fragment fragment = new HomeFragment();
            FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
            if(currentuser == null){
                login = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //define o titulo
                builder.setTitle("Erro!");
                //define a mensagem
                builder.setMessage("Voce precisa estar logado para acessar esta área! Deseja ir para a tela de login?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();



            }else {
                login = true;

            }

    }


    // Function to tell the app to stop getting
    // data from database on stoping of the activity

    @Override
    protected void onStart(){
        super.onStart();



    }


    }

