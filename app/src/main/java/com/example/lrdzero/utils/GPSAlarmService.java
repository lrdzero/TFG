package com.example.lrdzero.utils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import com.example.lrdzero.activities.MiServicioLocation;
import com.example.lrdzero.activities.Seguimiento;

import java.util.concurrent.TimeUnit;


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

            public void onTick(long millisUntilFinished) {

                String time =String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );




            }


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
    public boolean finalizado(){
        return finalizado;
    }
    public static void setUpdateListener(Seguimiento poiService) {
        notificador =poiService;
    }
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


