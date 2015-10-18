package com.example.lrdzero.tfg;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class Tramo {
    LatLng puntoOrigen;
    LatLng puntoFinal;

    Tramo(LatLng pO,LatLng pF){
        puntoOrigen=pO;
        puntoFinal=pF;
    }

    LatLng getOrigen(){
        return puntoOrigen;
    }

    LatLng getFinal(){
        return puntoFinal;
    }


}
