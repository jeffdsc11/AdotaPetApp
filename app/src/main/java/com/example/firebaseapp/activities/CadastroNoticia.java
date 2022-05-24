package com.example.firebaseapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.example.firebaseapp.model.NoticiaModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroNoticia extends AppCompatActivity {
    Button btnVolta, btnAdicionaNoticia;
    EditText edtTituloNoticia, edtTextoNoticia;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_noticia);
        btnVolta =  findViewById(R.id.btnVoltaNoticia);
        btnAdicionaNoticia = findViewById(R.id.btnAdicionaNoticia);
        edtTituloNoticia = findViewById(R.id.edtTituloNoticia);
        edtTextoNoticia = findViewById(R.id.edtTextoNoticia);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdicionaNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticiaModel noticiaModel = new NoticiaModel();
                noticiaModel.setTitulo(edtTituloNoticia.getText().toString());
                noticiaModel.setNoticia(edtTextoNoticia.getText().toString());
                noticiaModel.PublicarNoticia();
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroNoticia.this);
                //define o titulo
                builder.setTitle("Tudo certo!");
                //define a mensagem
                builder.setMessage("Notícia publicada!");
                //Exibe
                builder.show();
            }
        });

    }
}