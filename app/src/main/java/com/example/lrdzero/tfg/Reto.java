package com.example.lrdzero.tfg;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Carlos on 27/11/2015.
 */
public class Reto {
    String nombre;
    Marker marcador;
    int puntoruta;
    private LatLng location;


    public Reto(String n, Marker m, int pto){
        nombre=n;
        marcador=m;
        puntoruta=pto;
    }
    public void setLocation(LatLng loc) {
       location=loc;
        marcador.setPosition(loc);
    }
    public void setPunto(int pto){
        puntoruta=pto;
    }
    public String getNombre() {
        return nombre;
    }
    public int getMarkerLocation(){
        return puntoruta;
    }


}