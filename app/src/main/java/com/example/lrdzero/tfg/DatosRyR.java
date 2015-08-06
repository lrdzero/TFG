package com.example.lrdzero.tfg;

import android.graphics.drawable.Drawable;

/**
 * Created by lrdzero on 03/08/2015.
 */
public class DatosRyR {

    private String name;
    private String number;
    private String description;
    private String other;
    private String largeDescription;
    private int image;


    public DatosRyR(){

    }

    public DatosRyR(String aName, String aNumber, String aDescription, String anOther,int ima,String large){
        this.name=aName;
        this.number=aNumber;
        this.description=aDescription;
        this.other=anOther;
        this.image = ima;
        this.largeDescription=large;

    }



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
