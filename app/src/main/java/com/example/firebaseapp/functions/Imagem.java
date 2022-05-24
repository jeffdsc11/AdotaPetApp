package com.example.firebaseapp.functions;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Imagem {


    public Imagem() {
    }


 public void Show( String imagemUrl,  ImageView imagemView){
     Picasso
             .get()
             .load(imagemUrl)
             //.resize(140, 100)
             .noFade()
             .into(imagemView);

 }
}
