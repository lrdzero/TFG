package com.example.lrdzero.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.example.lrdzero.activities.Seguimiento;

/**
 * Servicio Temporizardo para controlar el tiempo que se tarda en completar una ruta.
 */
public class Temporizador extends Service {
    private Chronometer crono;
    private long timeTotal;
    private static Seguimiento notificador;
    public void onCreate(){
        super.onCreate();
        crono.setBase(SystemClock.elapsedRealtime());
        /**
         * Función que controla el tiempo y lo guarda en formato milisegundos.
         */
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                timeTotal=SystemClock.elapsedRealtime() - crono.getBase();
            }
        });
        crono.start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Función que finaliza el servicio y notifica a la clase interesada.
     */
    public void onDestroy(){
        super.onDestroy();
        //notificador.tiempoTranscurrido(timeTotal);
        crono.stop();
    }

    /**
     * Función que se encarga de establecer la comunicaión con el elemento al que se desea notificar
     * @param seguimineto
     */
    public static void setUpdateListener(Seguimiento seguimineto){ notificador=seguimineto;}
}
