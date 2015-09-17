package com.example.lrdzero.tfg;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;


public class GPSAlarmService extends Service {
    private Uri nm =null;
    private int vol;
    //Alarm alarm = new Alarm();
    MiServicioLocation local;
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //local=new MiServicioLocation(GPSAlarmService.this,0.0,0.0);
        //local.setLocal();
        //alarm.SetAlarm(GPSAlarmService.this,0,startId,nm);
        return START_STICKY;
    }



    public void onStart(Context context,double pos1, double pos2)
    {
        //local=new MiServicioLocation(GPSAlarmService.this,pos1,pos2);
        //alarm.setSound(selcet);
        //alarm.setVol(volumen);
        //alarm.SetAlarm(context,startId,volumen,selcet);
        //local.setLocal();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}


