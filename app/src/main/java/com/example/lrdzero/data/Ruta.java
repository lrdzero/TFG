package com.example.lrdzero.data;

import android.util.Log;
import android.view.View;


import com.example.lrdzero.utils.Functions;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Clase contenedora de los elementos de ruta.
 */
public class Ruta {
    private String nombreRuta;
    ArrayList<Tramo> tramos= new ArrayList<>();
    ArrayList<Reto> retos = new ArrayList<>();
    ArrayList<LatLng> points = new ArrayList<>();
    ArrayList<LatLng> minipoints = new ArrayList<>();
    private Functions functions;

    /**
     * Constructor por parámetro.
     * @param nombre
     */
    public Ruta(String nombre){
        nombreRuta=nombre;

    }

    /**
     * Función para añadir un tramo a la ruta.
     * @param t
     */
    public void addTramo(Tramo t){
        Log.e("tag",t.getOrigen().toString());
        tramos.add(t);
    }

    /**
     * Función para eliminar el último tramo de la ruta.
     */
    public void removeTramo(){
        if(tramos.size()>0)
            tramos.remove(tramos.size()-1);
    }

    /**
     * Función para añadir un conjunto de tramos.
     * @param t
     */
    public void setTramos(ArrayList<Tramo> t){
        tramos=t;
        CalculaPoints();
    }

    /**
     * Función para devolver un conjunto de tramos.
     * @return tramos
     */
    public ArrayList<Tramo> getTramos(){
        return tramos;
    }

    /**
     * Función que devuelve el primer punto de ruta.
     *
     */
    public LatLng getFirstPoint(){
        return tramos.get(0).getOrigen();
    }

    /**
     *Función que genera primero los puntos de ruta devueltos desde GoogleMap y despues realiza una distribucion homogenea de los puntos
     *
     *
     */
    public void CalculaPoints(){
        
        points.clear();
        Directions dir = new Directions();

        for(Tramo t : tramos){
            Document doc = dir.getDocument(t.getOrigen(), t.getFinal(), Directions.MODE_WALKING);
            points.addAll(dir.getDirection(doc));
        }

        ArrayList<LatLng> aux = new ArrayList<>();
        for(int i=0;i<points.size()-1;i++){
            
            if(functions.Haversine(points.get(i), points.get(i + 1))>3) {
                aux.addAll(Divide(functions.Haversine(points.get(i), points.get(i + 1)), points.get(i), points.get(i + 1)));
                Log.i("puntos",String.valueOf(i)+"+dist:"+String.valueOf(functions.Haversine(points.get(i),points.get(i+1))));
            }

        }
        if(points.size()>0)
          aux.add(points.get(points.size()-1));

        minipoints=aux;



    }

    /**
     *Función que divide una recta en n puntos con una relativa distancia entre ellos
     *
     *
     */
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




    /**
     *Función que devuelve los puntos iniciales de la ruta
     *
     *
     */
    public ArrayList<LatLng> getPoints(){
        return points;
    }

    /**
     *Función que devuelve el ultimo punto de la ruta
     *
     *
     */
    public LatLng getLastPoint(){
        if(tramos.size()>0)
            return tramos.get(tramos.size()-1).getFinal();
        else
            return null;
    }

    /**
     *Función que establece los retos pasados por parametro
     * 
     *@param list Lista de retos a añadir
     *
     */
    public void setRetos(ArrayList<Reto> list){
        retos=list;
    }


    /**
     *Función que añade un reto 
     *
     *
     */
    public void addReto(Reto r){
        retos.add(r);
    }

    /**
     *Función que devuelve la lista de retos de la ruta
     *
     *
     */
    public ArrayList<Reto> getRetos(){
        return retos;
    }


    /**
     *Función que indica si hay un reto en el punto de la ruta indicado
     * 
     *@return index Indice del reto encontrado
     * @return -1 Si no encuentra ningun reto
     *
     */
    public int existsRetoIn(int puntoactual) {
        int index=-1;
        for(int i=0;i<retos.size();i++){
            if(retos.get(i).getPunto()==puntoactual)
                index=i;

        }
        return index;
    }

    /**
     *Función que devuelve la lista de puntos homogeneamente distribuidos
     *
     *
     */
    public ArrayList<LatLng> getMiniPoints() {
        return minipoints;
    }
}
