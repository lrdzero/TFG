package com.example.lrdzero.datos;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Clase contenedora de los elementos de Reto.
 */
public class Reto {
    String nombre;
    Marker marcador;
    int puntoruta;
    private LatLng location;

    /**
     * Constructor por defecto.
     */
    public Reto(){}

    /**
     *
     * Constructor por parámetros.
     * @param n
     * @param m
     * @param pto
     */
    public Reto(String n, Marker m, int pto){
        nombre=n;
        marcador=m;
        puntoruta=pto;
    }

    /**
     * Función para insertar una localización.
     * @param loc
     */
    public void setLocation(LatLng loc) {
       location=loc;
        marcador.setPosition(loc);
    }

    /**
     * Función para insertar nombre.
     * @param nom
     */
    public void setNombre(String nom){nombre=nom;}

    /**
     * Función para insertar un punto de la ruta.
     * @param pto
     */
    public void setPunto(int pto){
        puntoruta=pto;
    }

    /**
     * Función para obtener nombre de ruta.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Función para devolver un punto de ruta.
     * @return puntoruta
     */
    public int getPunto(){return puntoruta;}

    /**
     * Función para devolver localización
     * @return puntoruta
     */
    public int getMarkerLocation(){
        return puntoruta;
    }


}