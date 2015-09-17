package com.example.lrdzero.tfg;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class MiServicioLocation extends Service {

    private final Context ctx;

    private double objetivoLatitude;
    private double objetivoLongitude;
    private double latitude;
    private double longitude;
    private Location loclocation;
    private LocationListener locListener;
    boolean gpsActivo =false;
    protected LocationManager miManager;
    private double dist;
    private int sonido;
    private Uri soundposible;

    public MiServicioLocation(){

        super();
        ctx= this.getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public MiServicioLocation(Context c,double obj1, double obj2,double distancia,int sound,Uri urii){
        super();

        objetivoLatitude=obj1;
        objetivoLongitude=obj2;
        dist=distancia;
        sonido=sound;
        soundposible=urii;
        this.ctx=c;



    }

    public void comenzar() {
        miManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);
        gpsActivo = miManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        loclocation=miManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        getLocation();


        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                getLocation();
                comprobar();
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("", "Provider Status: " + status);

            }
        };
        miManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }
    public void getLocation(){
        if (gpsActivo) {
            Location location = miManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else{
            Log.e("TAG GPS","GPS NO ACTIVO");
        }
    }

    public void comprobar(){

        //if(haversine(latitude,longitude,objetivoLatitude,objetivoLongitude)-dist<=0) {
            //Intent nt = new Intent(this.ctx, ControlActivity.class);
           // nt.setData(soundposible);
           // nt.putExtra("Sonido",Integer.toString(sonido));

           // this.ctx.startActivity(nt);
        //}

    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2)*Math.sin(dLat / 2) + Math.sin(dLon / 2)*Math.sin(dLon / 2)*Math.cos(lat1)*Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6372.8 * c;
    }
    public void desactivar(){
        miManager.removeUpdates(locListener);

    }


}
