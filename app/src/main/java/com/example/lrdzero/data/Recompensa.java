package com.example.lrdzero.data;

/**
 * Clase contenedora de los datos recompensa.
 */

public class Recompensa {

    private String name;
    private int image;
    private String nombreReto;
    private String descripcionRecompensa;
    private String seconFoto;

    /**
     * Constructor por defecto
     */
    public Recompensa(){
        image=0;
    }

    /**
     * Constructor por parámetros
     * @param n
     * @param i
     * @param n2
     * @param descri
     */
    public Recompensa(String n, int i, String n2, String descri){
        name=n;
        image=i;
        nombreReto=n2;
        descripcionRecompensa=descri;
    }

    /**
     * Función para insertar imagen.
     * @param i
     */
    public void setImage(int i){image=i;}

    /**
     * Función para insertar nombre de reto.
     * @param n
     */
    public void setNombreReto(String n){ nombreReto=n;}

    /**
     * Función para insertar nombre premio.
     * @param n
     */
    public void setName(String n){name=n;}

    /**
     * Función para insertar descripción del premio.
     * @param n
     */
    public void setDescripcionRecompensa(String n){descripcionRecompensa=n;}

    /**
     * Función para guardar path de imagen.
     * @param n
     */
    public void setSeconFoto(String n){seconFoto=n;}

    /**
     * Función para devolver nombre
     * @return nombre
     */
    public String getName(){return name;}

    /**
     * Función para devolver imagen
     * @return image
     */
    public int getImage(){return image;}

    /**
     * Función para devolver path de imagen
     * @return
     */
    public String getSeconFoto(){return seconFoto;}

    /**
     * Función para devolver nombre del reto
     * @return nombreReto.
     */
    public String getNombreReto(){return nombreReto;}

    /**
     * Función para devolver descripción de la recompensa.
     * @return descripcionRecompensa.
     */
    public String getDescripcionRecompensa(){return descripcionRecompensa;}
}
