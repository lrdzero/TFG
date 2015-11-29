package com.example.lrdzero.tfg;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;


public class MapaEditor extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    GoogleMap googleMap;
    MapView mapView;
    private double latitude;
    private double longitude;
    LocationClient mLocationClient;
    LatLng LastPoint;
    LatLng inicio=null;
    int nTramos=0;
    Ruta ruta;
    Conexion con;
    private boolean carga;
    private boolean retos;
    private String name;
    private String namereto;

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

        carga =getIntent().getExtras().getBoolean("tipo");
        name = getIntent().getExtras().getString("nombre");
        retos = getIntent().getExtras().getBoolean("retos");
        Button confirmar= (Button)findViewById(R.id.confirmar);
        Button deshacer= (Button)findViewById(R.id.deshacer);



        con = new Conexion();
        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext(), this, this);
        mLocationClient.connect();
        ruta = new Ruta("ruta a");

        if(carga) {
            confirmar.setEnabled(false);
            confirmar.setVisibility(View.INVISIBLE);

        }
            ruta.setTramos(con.cargarVisionRuta(name));




            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                                                 @Override
                                                 public void onMapLoaded() {

                                                     LatLng loc;

                                                     if (googleMap != null) {
                                                         if (ruta.getTramos().size() == 0) {
                                                             loc = new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());

                                                         } else {
                                                             loc = ruta.getFirstPoint();
                                                             new RefreshTramos().execute();
                                                         }
                                                         googleMap.addMarker(new MarkerOptions().position(loc));
                                                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17.0f));

                                                         //ruta.addTramo(new Tramo(new LatLng(2.33, 2.33), new LatLng(2.33, 2.33)));


                                                     }
                                                 }
                                             }
            );




        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {


            }
        });
        if(!retos) {

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    LatLng loc;

                    if (inicio != null) {
                        Toast.makeText(getApplication(), inicio.toString(), Toast.LENGTH_SHORT).show();
                        loc = inicio;
                       // Log.i("inicio null", "ultimo");
                        new LongOperation().execute(loc, latLng);
                        inicio = null;

                    } else if (ruta.getLastPoint() == null) {
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Inicio"));
                        //Log.i("vacio", "ultimo");
                        inicio = latLng;
                    } else {
                        loc = ruta.getLastPoint();
                        new LongOperation().execute(loc, latLng);
                        //Log.i("normal" + ruta.getLastPoint().toString(), "ultimo");
                    }

                    Toast.makeText(getApplication(), String.valueOf(latLng), Toast.LENGTH_SHORT).show();


                }
            });
        }
        else{

            final String nombreRto=getIntent().getExtras().getString("namereto");
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    LatLng loc;
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    con.updateRetoPos(nombreRto, latLng.latitude, latLng.longitude);
                    Toast.makeText(MapaEditor.this, "Posicion de reto asignada", Toast.LENGTH_LONG).show();
                    finish();
                }
            });




        }



        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //llevar a cabo la recogida de los datos.
                ArrayList<Tramo> datos = ruta.getTramos();


                JSONObject ori = new JSONObject();
                int tamanio = datos.size();
                for (int i = 0; i < datos.size(); i++) {
                    Double latitudO = datos.get(i).getOrigen().latitude;
                    Double longitudO = datos.get(i).getOrigen().longitude;
                    Double latitudF = datos.get(i).getFinal().latitude;
                    Double longitudF = datos.get(i).getFinal().longitude;
                    try {
                        ori.put("Ruta" + i, name);
                        ori.put("latitudO" + i, latitudO);
                        ori.put("longitudO" + i, longitudO);
                        ori.put("latitudF" + i, latitudF);
                        ori.put("longitudF" + i, longitudF);
                        ori.put("posicion" + i, i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Double imprime = ori.getDouble("longitudO");

                        String imprime2 = ori.toString();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Toast.makeText(MapaEditor.this, "Tamanio" + Integer.toString(tamanio), Toast.LENGTH_LONG).show();
                con.hacerConexionJSON("Mapeado", ori, tamanio);
                Toast.makeText(MapaEditor.this, "Termino envio", Toast.LENGTH_LONG).show();

                finish();


            }
        });


        deshacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta.removeTramo();
                new RefreshTramos().execute();

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
            Log.i(ruta.getLastPoint().toString(), "ultimo");

            for(int i = 0 ; i < directionPoint.size() ; i++) {
                rectLine.add(directionPoint.get(i));
            }

            return rectLine;
        }


        @Override
        protected void onPostExecute(PolylineOptions result) {

            googleMap.addPolyline(result);

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class RefreshTramos extends AsyncTask<Void, Void, PolylineOptions> {


        @Override
        protected PolylineOptions doInBackground(Void... params) {


            PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);


            for(LatLng p : ruta.getPoints()) {
                rectLine.add(p);

            }


            return rectLine;
        }


        @Override
        protected void onPostExecute(PolylineOptions result) {

            googleMap.addPolyline(result);
            if(ruta.getTramos().size()>0) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(ruta.getFirstPoint())
                        .title("Inicio"));
            }
            Log.i("vacio", "ultimo");



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
