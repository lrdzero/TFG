package com.example.lrdzero.tfg;

/**
 * Created by lrdzero on 08/12/2015.
 */
public class ValoresHistorial {
    private int totales;
    private int completados;

    public ValoresHistorial(){
        totales=0;
        completados=0;
    }

    public ValoresHistorial(int t, int c){
        totales=t;
        completados=c;
    }
    public int getTotales(){return totales;}
    public int getCompletados(){return completados;}
    public int getPorcentaje(){return (completados*100)/totales;}
}
