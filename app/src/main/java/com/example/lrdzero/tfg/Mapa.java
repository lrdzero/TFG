package com.example.lrdzero.tfg;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Document;

import java.io.StringWriter;
import java.util.ArrayList;


public class Mapa extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    GoogleMap googleMap;
    MapView mapView;
    private double latitude;
    private double longitude;
    LocationClient mLocationClient;
    LatLng LastPoint;
    int nTramos=0;
    Ruta ruta= new Ruta("r1");
    Conexion con;
    //ArrayList<Array> ArrayTramos = new ArrayList<>();
    static private final int GET_TEXT_REQUEST_CODE = 2;
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
        mLocationClient.connect();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        mapView =(MapView)findViewById(R.id.mi_mapa);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);

        con = new Conexion();
        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext(), this, this);
        mLocationClient.connect();

        ruta.setTramos(con.cargarVisionRuta("ruta a"));

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {



            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng loc;
               // new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());


                if(ruta.getLastPoint()==null) {
                    loc = new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                }
                else
                    loc=ruta.getLastPoint();



                new LongOperation().execute(loc,latLng);
                Toast.makeText(getApplication(), String.valueOf(latLng), Toast.LENGTH_SHORT).show();


            }
        });

        Button confirmar= (Button)findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocationClient != null && mLocationClient.isConnected()) {
                    String msg = "Location = " + mLocationClient.getLastLocation();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    // mLocationClient.on
                    LatLng loc = new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(loc));
                    if (googleMap != null) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17.0f));
                    }

                    //llevar a cabo la recogida de los datos.
                    ArrayList<Tramo> datos =ruta.getTramos();



                    for(int i=0;i< datos.size();i++){
                        JSONObject ori = new JSONObject();

                        Double latitudO=datos.get(i).getOrigen().latitude;
                        Double longitudO = datos.get(i).getOrigen().longitude;
                        Double latitudF=datos.get(i).getFinal().latitude;
                        Double longitudF = datos.get(i).getFinal().longitude;
                        try {
                            ori.put("Ruta","ruta a");
                            ori.put("latitudO",latitudO);
                            ori.put("longitudO",longitudO);
                            ori.put("latitudF", latitudF);
                            ori.put("longitudF", longitudF);
                            ori.put("posicion", i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Double imprime = ori.getDouble("longitudO");

                            String imprime2 = ori.toString();
                            Toast.makeText(Mapa.this,"Se han creado los JSONObject",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(Mapa.this,"Enviando",Toast.LENGTH_LONG).show();
                         con.hacerConexionJSON("Mapeado", ori);
                        Toast.makeText(Mapa.this,"Termino envio",Toast.LENGTH_LONG).show();





                    }




                }

            }
        });

        Button deshacer= (Button)findViewById(R.id.deshacer);
        deshacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new BorradoTramo().execute();



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class LongOperation extends AsyncTask<LatLng, Void, PolylineOptions> {
        LatLng li;
        LatLng lf;


        @Override
        protected PolylineOptions doInBackground(LatLng... params) {
            li=params[0];
            lf=params[1];
            LastPoint=lf;
            Directions dir = new Directions();
            Document doc = dir.getDocument(li, lf, Directions.MODE_WALKING);
            ArrayList<LatLng> directionPoint = dir.getDirection(doc);
            PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);


            ruta.addTramo(new Tramo(li,lf));

            for(int i = 0 ; i < directionPoint.size() ; i++) {
                rectLine.add(directionPoint.get(i));
            }




            return rectLine;
        }


        @Override
        protected void onPostExecute(PolylineOptions result) {

            googleMap.addPolyline(result);

            /*Polyline pl = googleMap.addPolyline(new PolylineOptions()
                            .add(li,lf)
                            .geodesic(true)
                            .width((float) 2.0)
                            .color(Color.BLUE).geodesic(true)
            );*/
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class BorradoTramo extends AsyncTask<Void, Void, PolylineOptions> {


        @Override
        protected PolylineOptions doInBackground(Void... params) {



            //Directions dir = new Directions();
            //Document doc = dir.getDocument(li, lf, Directions.MODE_WALKING);

            ruta.removeTramo();

            PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);


            for(LatLng p : ruta.getPoints()) {
                rectLine.add(p);

            }

            return rectLine;
        }


        @Override
        protected void onPostExecute(PolylineOptions result) {

            googleMap.addPolyline(result);

            /*Polyline pl = googleMap.addPolyline(new PolylineOptions()
                            .add(li,lf)
                            .geodesic(true)
                            .width((float) 2.0)
                            .color(Color.BLUE).geodesic(true)
            );*/
        }



        @Override
        protected void onPreExecute() {
            googleMap.clear();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
