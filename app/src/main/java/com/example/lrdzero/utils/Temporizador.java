package com.example.lrdzero.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.example.lrdzero.activities.Seguimiento;

/**
 * Created by lrdzero on 10/12/2015.
 */
public class Temporizador extends Service {
    private Chronometer crono;
    private long timeTotal;
    private static Seguimiento notificador;
    public void onCreate(){
        super.onCreate();
        crono.setBase(SystemClock.elapsedRealtime());
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
    public void onDestroy(){
        super.onDestroy();
        //notificador.tiempoTranscurrido(timeTotal);
        crono.stop();
    }
    public static void setUpdateListener(Seguimiento seguimineto){ notificador=seguimineto;}
}
