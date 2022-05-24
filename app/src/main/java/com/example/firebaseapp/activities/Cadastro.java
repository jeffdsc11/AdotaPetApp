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

import com.example.firebaseapp.R;
import com.example.firebaseapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtConfirmaEmail;
    private EditText edtCpf;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private CheckBox ckbCadastro;
    private Button btnCadastra;
    private Button btnVolta;
    private ProgressBar progressBarCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        mAuth = FirebaseAuth.getInstance();
        edtNome = findViewById(R.id.edtNomePet);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail_Cadastro);
        edtConfirmaEmail = findViewById(R.id.edtEmail_Confirmar_Cadastro);
        edtCpf = findViewById(R.id.edtCpf);
        edtSenha = findViewById(R.id.edtSenha_cadastro);
        edtConfirmaSenha = findViewById(R.id.edtSenha_Confirmar_cadastro);
        btnCadastra = findViewById(R.id.btnConfirma_Cadastro);
        ckbCadastro = findViewById(R.id.ckb_cadastro);
        btnVolta = findViewById(R.id.btnVoltar);
        progressBarCadastro = findViewById(R.id.progressBarCadastro);

        ckbCadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtConfirmaSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtConfirmaSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel userModel = new UserModel();

                userModel.setNome( edtNome.getText().toString());
                userModel.setCpf(edtCpf.getText().toString());
                userModel.setTelefone(edtTelefone.getText().toString());
                userModel.setEmail(edtEmail.getText().toString());
                userModel.setProfileUrl("https://firebasestorage.googleapis.com/v0/b/fir-app-eb448.appspot.com/o/account_image.png?alt=media&token=82d287a0-30fd-4b6d-bcf4-17af439216ac");
                String registerConfirmaEmail = edtConfirmaEmail.getText().toString();
                String registerSenha = edtSenha.getText().toString();
                String registerConfirmaSenha = edtConfirmaSenha.getText().toString();
                if(!TextUtils.isEmpty(userModel.getNome())&& !TextUtils.isEmpty(userModel.getCpf())&&!TextUtils.isEmpty(userModel.getTelefone())&&
                        !TextUtils.isEmpty(registerConfirmaEmail)&&!TextUtils.isEmpty(userModel.getEmail())&&!TextUtils.isEmpty(registerSenha)&&!TextUtils.isEmpty(registerConfirmaSenha)){
                    if(userModel.getEmail().equals(registerConfirmaEmail)&&
                            registerSenha.equals(registerConfirmaSenha)){
                        progressBarCadastro.setVisibility(view.VISIBLE);
                       mAuth.createUserWithEmailAndPassword(userModel.getEmail(), registerSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){

                                   userModel.setId(mAuth.getUid());
                                   userModel.salvar();
                                   AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                                   //define o titulo

                                   //define a mensagem
                                   builder.setMessage("Cadastro Efetuado!");
                                   //Exibe
                                   builder.show();
                                   abrirTelaPrincipal();
                               }else{
                                   progressBarCadastro.setVisibility(view.INVISIBLE);
                                   String error;
                                   try{
                                        throw task.getException();
                                   }catch (FirebaseAuthWeakPasswordException e){
                                       error = "A senha deve conter no mínimo 6 caracteres";

                                   }catch (FirebaseAuthInvalidCredentialsException e){
                                       error = "E-mail inválido";
                                   }catch (FirebaseAuthUserCollisionException e){
                                       error = "E-mail  já existe";
                                   }catch (Exception e){
                                       error = "Erro ao efetuar o cadastro";
                                       e.printStackTrace();
                                   }
                                   AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                                   //define o titulo
                                   builder.setTitle("Erro!");
                                   //define a mensagem
                                   builder.setMessage(error);
                                   //Exibe
                                   builder.show();
                               }
                           }
                       });


                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                        //define o titulo
                        builder.setTitle("Erro!");
                        //define a mensagem
                        builder.setMessage("E-Mail ou senha não confere");
                        //Exibe
                        builder.show();
                    }

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                    //define o titulo
                    builder.setTitle("Erro!");
                    //define a mensagem
                    builder.setMessage("Preencher todos os campos");
                    //Exibe
                    builder.show();
                }



            }
        });

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void abrirTelaPrincipal() {
        Intent intent =  new Intent(Cadastro.this, Login.class);
        startActivity(intent);
    }
}