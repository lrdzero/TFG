package com.example.lrdzero.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.lrdzero.activities.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Functions {
    public Functions (){}
    /**
     *
     * Funcion para medir la distancia en metros entre dos puntos
     *
     * @param l1 punto 1
     * @param l2 punto 2
     *
     */
    public double Haversine(LatLng l1, LatLng l2){
        double lat1=l1.latitude;
        double lon1=l1.longitude;
        double lat2=l2.latitude;
        double lon2=l2.longitude;
        double R = 6378.137;
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000;
    }

    private Drawable reduceImagen(Uri unUri,int tam1,int tam2, Activity ac){
        Drawable yourDrawable;
        try {
            InputStream inputStream = ac.getContentResolver().openInputStream(unUri);
            yourDrawable = Drawable.createFromStream(inputStream, unUri.toString());
        } catch (FileNotFoundException e) {
            yourDrawable = ac.getResources().getDrawable(R.drawable.pesas);
        }
        Bitmap bitmap = ((BitmapDrawable) yourDrawable).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(ac.getResources(), Bitmap.createScaledBitmap(bitmap, tam1, tam2, true));
        yourDrawable.invalidateSelf();
        return d;
    }
}
