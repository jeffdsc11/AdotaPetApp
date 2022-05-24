package com.example.firebaseapp.model;

public class User {

    public String nome, email,profileUrl;

    public User() {
    }

    public User(String nome, String email, String profileUrl) {
        this.nome = nome;
        this.email = email;
        this.profileUrl = profileUrl;
    }
}
