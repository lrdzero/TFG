package com.example.lrdzero.tfg;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class Ruta {
    private String nombreRuta;
    ArrayList<Tramo> tramos= new ArrayList<>();
    ArrayList<Reto> retos = new ArrayList<>();
    ArrayList<LatLng> points = new ArrayList<>();


    public Ruta(String nombre){
        nombreRuta=nombre;

    }

    public void addTramo(Tramo t){
        Log.e("tag",t.getOrigen().toString());
        tramos.add(t);
    }

    public void removeTramo(){
        if(tramos.size()>0)
            tramos.remove(tramos.size()-1);
    }

    public void setTramos(ArrayList<Tramo> t){
        tramos=t;
        CalculaPoints();
    }

    public ArrayList<Tramo> getTramos(){
        return tramos;
    }
    public LatLng getFirstPoint(){
        return tramos.get(0).getOrigen();
    }

    public void CalculaPoints(){
        points.clear();
        Directions dir = new Directions();

        for(Tramo t : tramos){
            Document doc = dir.getDocument(t.getOrigen(), t.getFinal(), Directions.MODE_WALKING);
            points.addAll(dir.getDirection(doc));
        }

    }

    public ArrayList<LatLng> getPoints(){
        return points;
    }

    public LatLng getLastPoint(){
        if(tramos.size()>0)
            return tramos.get(tramos.size()-1).getFinal();
        else
            return null;
    }

    public void setRetos(ArrayList<Reto> list){
        retos=list;
    }

    public void addReto(Reto r){
        retos.add(r);
    }

    public ArrayList<Reto> getRetos(){
        return retos;
    }




}
