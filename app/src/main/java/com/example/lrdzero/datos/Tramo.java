package com.example.lrdzero.datos;

import com.google.android.gms.maps.model.LatLng;


public class Tramo {
    LatLng puntoOrigen;
    LatLng puntoFinal;

    public Tramo(LatLng pO, LatLng pF){
        puntoOrigen=pO;
        puntoFinal=pF;
    }

    /**
     *
     * Devuelve el origen del tramo
     *
     *
     */
    public LatLng getOrigen(){
        return puntoOrigen;
    }

    /**
     *
     * Devuelve el final del tramo
     *
     *
     */
    public LatLng getFinal(){
        return puntoFinal;
    }

}
