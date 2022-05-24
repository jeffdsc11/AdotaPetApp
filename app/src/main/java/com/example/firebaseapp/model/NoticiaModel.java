package com.example.firebaseapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoticiaModel {
    String id;
    String Titulo;
    String Noticia;

    public NoticiaModel() {
    }

    public NoticiaModel(String id, String titulo, String noticia) {
        this.id = id;
        Titulo = titulo;
        Noticia = noticia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getNoticia() {
        return Noticia;
    }

    public void setNoticia(String noticia) {
        Noticia = noticia;
    }

    public void PublicarNoticia(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Noticias").push().setValue(this);
    }

}
