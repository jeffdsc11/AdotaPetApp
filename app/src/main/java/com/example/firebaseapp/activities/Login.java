package com.example.firebaseapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button  btn_entra;
    private Button  btn_cadastra;
    private FirebaseAuth mAuth;
    private CheckBox ckb_mostrar_senha;
    private ProgressBar progressBarLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        progressBarLogin = findViewById(R.id.progressBar);
        edt_email = findViewById(R.id.EdtEmail);
        edt_senha = findViewById(R.id.EdtSenha);
        btn_entra = findViewById(R.id.BtnEntra);
        btn_cadastra = findViewById(R.id.BtnCadastra);
        ckb_mostrar_senha = findViewById(R.id.CbMostaSenha);


        btn_entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginEmail = edt_email.getText().toString();
                String loginSenha = edt_senha.getText().toString();

                if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginSenha)){
                    progressBarLogin.setVisibility(view.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail,loginSenha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        abrirTelaPrincipal();
                                    }else{
                                        progressBarLogin.setVisibility(view.INVISIBLE);
                                        String error = task.getException().getMessage().toString();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        //define o titulo
                                        builder.setTitle("Erro!");
                                        //define a mensagem
                                        builder.setMessage(""+error);
                                        //Exibe
                                        builder.show();

                                    }
                                }
                            });
                }
            }
        });


        TextView txtEntraSemLogin = findViewById(R.id.txtEntraSemLogin);
        txtEntraSemLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
                finish();

            }
        });

        ckb_mostrar_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edt_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else edt_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    private void abrirTelaPrincipal() {
        Intent intent =  new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}