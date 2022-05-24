package com.example.firebaseapp.model;

public class person {
    // Variable to store data corresponding
    // to firstname keyword in database
    private String nome;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String raca;

    // Variable to store data corresponding
    // to age keyword in database
    private String idade;


    // Variable to store data corresponding
    // to image keyword in database
    private String imageUrlDog;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public person() {
    }

    // Getter and setter method


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

    public String getImageUrlDog() {
        return imageUrlDog;
    }

    public void setImageUrlDog(String imageUrlDog) {
        this.imageUrlDog = imageUrlDog;
    }
}