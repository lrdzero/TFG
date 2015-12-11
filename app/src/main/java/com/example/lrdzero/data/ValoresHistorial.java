package com.example.lrdzero.data;

/**
 * Clase contenedora de datos para control de Historial de usuario.
 */
public class ValoresHistorial {
    private int totales;
    private int completados;

    /**
     * Constructor por defecto de la clase.
     */
    public ValoresHistorial(){
        totales=0;
        completados=0;
    }

    /**
     * Constructor por parámetro.
     * @param t
     * @param c
     */
    public ValoresHistorial(int t, int c){
        totales=t;
        completados=c;
    }

    /**
     * Función para devolver número de retos totales.
     * @return totales
     */
    public int getTotales(){return totales;}

    /**
     * Función para devolver número de retos completados
     * @return completados
     */
    public int getCompletados(){return completados;}

    /**
     * Función para devolver el porcentaje obtenido.
     * @return (completados*100)/totales
     */
    public int getPorcentaje(){return (completados*100)/totales;}
}
