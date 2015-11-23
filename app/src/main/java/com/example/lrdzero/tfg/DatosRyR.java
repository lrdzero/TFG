package com.example.lrdzero.tfg;

import android.net.Uri;

/**
 * Created by lrdzero on 03/08/2015.
 */
public class DatosRyR {

    private String name;
    private String number;
    private String description;
    private String other;
    private String largeDescription;
    private String adic;
    private String aux;
    private int image;
    private Uri uri;


    public DatosRyR(){

    }

    public DatosRyR(String aName, String aNumber, String aDescription, String anOther,int ima,String large,Uri un){
        this.name=aName;
        this.number=aNumber;
        this.description=aDescription;
        this.other=anOther;
        this.image = ima;
        this.largeDescription=large;
        this.uri=un;

    }
    public void setUri(Uri n){
        this.uri =n;
    }
    public void setAdic(String n){this.adic=n;}
    public void setAux(String n){this.aux=n;}
    public void setName(String n){this.name=n;}
    public void setNumber(String n){this.number=n;}
    public void setDescription(String n){this.description=n;}
    public void setOther(String n){this.other=n;}
    public void setImage(int n){this.image=n;}
    public void setLargeDescription(String n){this.largeDescription=n;}

    public Uri getUri(){return uri;}
    public String getAdic(){return adic;}
    public String getAux(){return aux;}
    public String getName(){
        return name;
    }
    public String getNumber(){return number;}
    public String getDescription(){return description;}
    public String getOther(){return other;}
    public int getImage(){
        return image;
    }
    public String getLargeDescription(){return largeDescription;}
}
