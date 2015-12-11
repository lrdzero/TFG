package com.example.lrdzero.datos;


public class Recompensa {
//lkjñlkj
    private String name;
    private int image;
    private String nombreReto;
    private String descripcionRecompensa;
    private String seconFoto;

    public Recompensa(){
        image=0;
    }
    public Recompensa(String n, int i, String n2, String descri){
        name=n;
        image=i;
        nombreReto=n2;
        descripcionRecompensa=descri;
    }

    public void setImage(int i){image=i;}
    public void setNombreReto(String n){ nombreReto=n;}
    public void setName(String n){name=n;}
    public void setDescripcionRecompensa(String n){descripcionRecompensa=n;}
    public void setSeconFoto(String n){seconFoto=n;}


    public String getName(){return name;}
    public int getImage(){return image;}
    public String getSeconFoto(){return seconFoto;}
    public String getNombreReto(){return nombreReto;}
    public String getDescripcionRecompensa(){return descripcionRecompensa;}
}
