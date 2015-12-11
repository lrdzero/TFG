package com.example.lrdzero.datos;

import android.net.Uri;

/**
 * Clase para guardar datos de transito en la aplicación
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
    private String preferenciaUser1;
    private String preferenciaUser2;
    private String respuesta;
    private int position;

    /**
     * Constructor por defecto
     */
    public DatosRyR(){

    }

    /**
     * Constructor por parámetros
     * @param aName
     * @param aNumber
     * @param aDescription
     * @param anOther
     * @param ima
     * @param large
     * @param un
     */
    public DatosRyR(String aName, String aNumber, String aDescription, String anOther,int ima,String large,Uri un){
        this.name=aName;
        this.number=aNumber;
        this.description=aDescription;
        this.other=anOther;
        this.image = ima;
        this.largeDescription=large;
        this.uri=un;



    }
    //Métodos set.
    public void setPosition(int i){this.position=i;}
    public void setRespuesta(String n){this.respuesta=n;}
    public void setPreferenciaUser1(String n){this.preferenciaUser1=n;}
    public void setPreferenciaUser2(String n){this.preferenciaUser2=n;}
    public void setUri(Uri n){this.uri =n;}
    public void setAdic(String n){this.adic=n;}
    public void setAux(String n){this.aux=n;}
    public void setName(String n){this.name=n;}
    public void setNumber(String n){this.number=n;}
    public void setDescription(String n){this.description=n;}
    public void setOther(String n){this.other=n;}
    public void setImage(int n){this.image=n;}
    public void setLargeDescription(String n){this.largeDescription=n;}

    //Métodos get.
    public int getPosition(){return position;}
    public String getRespuesta(){return respuesta;}
    public String getPreferenciaUser1(){return preferenciaUser1;}
    public String getPreferenciaUser2(){return preferenciaUser2;}
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
