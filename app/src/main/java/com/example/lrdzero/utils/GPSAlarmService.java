package com.example.lrdzero.utils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import com.example.lrdzero.activities.MiServicioLocation;
import com.example.lrdzero.activities.Seguimiento;

import java.util.concurrent.TimeUnit;

/**
 * Servicio para temporizar tiempo de pause de la clase Seguimiento.
 */

public class GPSAlarmService extends Service {


    MiServicioLocation local;
    CountDownTimer cuentaatras;
    private static Intent Contex;
    private boolean finalizado=false;
    private static Seguimiento notificador;


    public void onCreate()
    {
        super.onCreate();
        Toast.makeText(GPSAlarmService.this,"Entro en el servicion y esta activo",Toast.LENGTH_LONG).show();
        cuentaatras=new CountDownTimer(1200000, 1000) {
            /**
             * Función para lanzar la cuenta atrás
             * @param millisUntilFinished
             */
            public void onTick(long millisUntilFinished) {

                String time =String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );




            }

            /**
             * Función que reconoce la llegada a 0 de cuenta atrás y notifica a la clase Seguimiento.
             */
            public void onFinish() {
                notificador.finalizar();
                //onDestroy();
            }
        };
        cuentaatras.start();




    }


    public void onStart(Intent intent, int flags, int startId)
    {


       
    }

    /**
     * Función para asignar clase contenedora con la que enlazar comunicación de vuelta
     * @param poiService
     */
    public static void setUpdateListener(Seguimiento poiService) {
        notificador =poiService;
    }

    /**
     * Función onDestroy que ejecuta la cancelación del contador.
     */
    public void onDestroy(){
        super.onDestroy();
        cuentaatras.cancel();
        Toast.makeText(GPSAlarmService.this,"Final del servicio",Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}


