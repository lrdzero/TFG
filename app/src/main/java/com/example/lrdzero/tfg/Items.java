package com.example.lrdzero.tfg;

/**
 * Created by lrdzero on 07/08/2015.
 */
public class Items {

    private String name;
    private int image;
    private String nombreReto;


    public Items(String n, int i,String n2){
        name=n;
        image=i;
        nombreReto=n2;
    }

    public String getName(){return name;}
    public int getImage(){return image;}
    public void setImage(int i){image=i;}
    public String getNombreReto(){return nombreReto;}
}
