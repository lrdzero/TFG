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
    private int image;


    public DatosRyR(){

    }

    public DatosRyR(String aName, String aNumber, String aDescription, String anOther,int ima){
        this.name=aName;
        this.number=aNumber;
        this.description=aDescription;
        this.other=anOther;
        this.image = ima;

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
}
