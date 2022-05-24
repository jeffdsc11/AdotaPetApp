package com.example.firebaseapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PetModel {
    String nome;
    String raca;
    String idade;
    String descricao;
    String imageUrlDog;
    String idDono;
    String idDoador;

    public PetModel(String nome, String raca, String idade, String descricao, String imageUrlDog, String idDono, String idDoador) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.descricao = descricao;
        this.imageUrlDog = imageUrlDog;
        this.idDono = idDono;
        this.idDoador = idDoador;
    }

    public PetModel() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImageUrlDog() {
        return imageUrlDog;
    }

    public void setImageUrlDog(String imageUrlDog) {
        this.imageUrlDog = imageUrlDog;
    }

    public String getIdDono() {
        return idDono;
    }

    public void setIdDono(String idDono) {
        this.idDono = idDono;
    }

    public String getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(String idDoador) {
        this.idDoador = idDoador;
    }

    public void InserirPetAdmin(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("pets").push().setValue(this);
    } public void InserirPet(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("petsAnalise").push().setValue(this);
    }
    public void InserirPetUsuario(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("usuarios").child(idDono);
        reference.child("pets").push().setValue(this);
    }

}
