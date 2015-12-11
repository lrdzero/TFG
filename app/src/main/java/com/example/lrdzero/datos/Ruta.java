package com.example.lrdzero.datos;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class Ruta {
    private String nombreRuta;
    ArrayList<Tramo> tramos= new ArrayList<>();
    ArrayList<Reto> retos = new ArrayList<>();
    ArrayList<LatLng> points = new ArrayList<>();
    ArrayList<LatLng> minipoints = new ArrayList<>();


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

        ArrayList<LatLng> aux = new ArrayList<>();
        for(int i=0;i<points.size()-1;i++){
            if(measure(points.get(i),points.get(i+1))>3) {
                aux.addAll(Divide(measure(points.get(i), points.get(i + 1)), points.get(i), points.get(i + 1)));
                Log.i("puntos",String.valueOf(i)+"+dist:"+String.valueOf(measure(points.get(i),points.get(i+1))));
            }

        }
        if(points.size()>0)
          aux.add(points.get(points.size()-1));

        minipoints=aux;



    }
    private ArrayList<LatLng> Divide(double distancia,LatLng l1, LatLng l2) {
        ArrayList<LatLng> aux2 = new ArrayList<>();
        double lat1=l1.latitude;
        double lon1=l1.longitude;
        double lat2=l2.latitude;
        double lon2=l2.longitude;
        double x=(lat2-lat1);
        double y=(lon2-lon1);
        int divisiones = (int) (distancia/2)+1;
        for(int j=0;j<divisiones;j++)
            aux2.add(new LatLng(lat1+((x/divisiones)*j),lon1+((y/divisiones)*j)));



        return aux2;

    }



    public double measure(LatLng l1,LatLng l2){  // generally used geo measurement function
        double lat1=l1.latitude;
        double lon1=l1.longitude;
        double lat2=l2.latitude;
        double lon2=l2.longitude;
        double R = 6378.137; // Radius of earth in KM
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
       double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
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


    public int existsRetoIn(int puntoactual) {
        int index=-1;
        for(int i=0;i<retos.size();i++){
            if(retos.get(i).getPunto()==puntoactual)
                index=i;

        }
        return index;
    }

    public ArrayList<LatLng> getMiniPoints() {
        return minipoints;
    }
}
