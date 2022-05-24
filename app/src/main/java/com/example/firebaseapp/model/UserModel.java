package com.example.firebaseapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private String profileUrl;
    private String pets;
    private String urlComprovante;

    public UserModel() {
    }

    public UserModel(String id, String nome, String cpf, String email, String senha, String telefone, String profileUrl, String pets, String urlComprovante) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.profileUrl = profileUrl;
        this.pets = pets;
        this.urlComprovante = urlComprovante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProfileUrl() {return profileUrl;    }
    public void setProfileUrl(String profileUrl) {this.profileUrl = profileUrl;    }

    public String getPets() {return pets; }
    public void setPets(String pets) { this.pets = pets; }

    public String getUrlComprovante() {return urlComprovante;
    }

    public void setUrlComprovante(String urlComprovante) {this.urlComprovante = urlComprovante;
    }

    public void salvar(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(this);
    }
    public void InserirComprovante(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).child("comprovantes").push().setValue(urlComprovante);
    }
}
