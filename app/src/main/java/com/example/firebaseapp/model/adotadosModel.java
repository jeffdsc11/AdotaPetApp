package com.example.firebaseapp.model;

public class adotadosModel {
    // Variable to store data corresponding
    // to firstname keyword in database
    private String firstname;

    // Variable to store data corresponding
    // to image keyword in database
    private String image;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public adotadosModel() {}

    // Getter and setter method
    public String getFirstname()
    {
        return firstname;
    }
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

}
