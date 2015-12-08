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
import android.widget.ScrollView;
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


public class marca_retos extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
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
    ListView listretos= null;
    private ArrayList<Tramo> tramosOF=new ArrayList<Tramo>();
    ArrayList<Reto> retosRuta = new ArrayList<Reto>();
    private int tamanio;
    private RetosAdapter ra;


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
        setContentView(R.layout.activity_marca_retos);


        mapView =(MapView)findViewById(R.id.mi_mapa);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);

        carga =getIntent().getExtras().getBoolean("tipo");
        name = getIntent().getExtras().getString("nombre");
        retos = getIntent().getExtras().getBoolean("retos");
        Button confirmar= (Button)findViewById(R.id.confirmar);
        listretos = (ListView)findViewById(R.id.listretosedit);
        ScrollView sb = (ScrollView) findViewById(R.id.scroll);


        con = new Conexion();
        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext(), this, this);
        mLocationClient.connect();
        tamanio = getIntent().getExtras().getInt("tamanioRuta");
        ruta = new Ruta(name);

        if(carga) {
            confirmar.setEnabled(false);
            confirmar.setVisibility(View.INVISIBLE);

        }






        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                                             @Override
                                             public void onMapLoaded() {

                                                 for(int i=0;i<tamanio;i++){
                                                     double oriLat=getIntent().getExtras().getDouble("tramoLatOrigen" + i);
                                                     double oriLong=getIntent().getExtras().getDouble("tramoLongOrigen" + i);
                                                     double endLat=getIntent().getExtras().getDouble("tramoLatFinal"+i);
                                                     double endLong=getIntent().getExtras().getDouble("tramoLongFinal"+i);
                                                     LatLng nuevoInicio = new LatLng(oriLat,oriLong);
                                                     LatLng nuevoFinal = new LatLng(endLat,endLong);
                                                     Tramo nuevo = new Tramo(nuevoInicio,nuevoFinal);
                                                     tramosOF.add(nuevo);
                                                 }
                                                 ruta.setTramos(tramosOF);
                                                 int tamanioRetos = getIntent().getExtras().getInt("tamanioRetos");


                                                 for(int i=0;i<tamanioRetos;i++){
                                                     String nombre =getIntent().getExtras().getString("nombreReto" + i);
                                                     int position = getIntent().getExtras().getInt("position"+i);
                                                     Log.i("Prueba", Integer.toString(position));
                                                     Reto nuevo = new Reto(nombre,googleMap.addMarker(new MarkerOptions().position(ruta.getMiniPoints().get(position)).title(nombre)),position);
                                                     //nuevo.setPunto(position);
                                                     //nuevo.setNombre(nombre);
                                                     retosRuta.add(nuevo);
                                                     ruta.addReto(nuevo);
                                                     //retosRuta.add(nuevo);
                                                 }

                                                 ra = new RetosAdapter(getApplicationContext(), ruta);
                                                 for(int i=0;i<retosRuta.size();i++){
                                                     ra.addReto(retosRuta.get(i));
                                                 }
                                                 Toast.makeText(marca_retos.this, "Tamanio" + Integer.toString(ra.getCount()), Toast.LENGTH_LONG).show();
                                                 listretos.setAdapter(ra);
                                                 listretos.setFooterDividersEnabled(true);

                                                 new  RefreshTramos().execute();

                                             }
                                         }
        );








        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //llevar a cabo la recogida de los datos.
                ArrayList<Tramo> datos = ruta.getTramos();
                ArrayList<String> posiciones = new ArrayList<String>();

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
                for(int i=0;i<retosRuta.size();i++){
                    Reto n = (Reto) ra.getItem(i);
                    posiciones.add(n.getNombre());
                    posiciones.add(Integer.toString(n.getPunto()));

                }
//Toast.makeText(marca_retos.this, "Tamanio" + Integer.toString(tamanio), Toast.LENGTH_LONG).show();
                con.hacerconexionGenerica("updateRetoPost",posiciones);
                Toast.makeText(marca_retos.this, "Tamanio" + Integer.toString(tamanio), Toast.LENGTH_LONG).show();
                con.hacerConexionJSON("Mapeado", ori, tamanio);
                Toast.makeText(marca_retos.this, "Termino envio", Toast.LENGTH_LONG).show();

                finish();


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

    private class CargaRuta extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //ruta.setTramos(con.cargarVisionRuta(name));



            //ruta.setRetos(retosRuta);

            //Toast.makeText(marca_retos.this,"TU PUTA MADRE",Toast.LENGTH_LONG).show();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("test",String.valueOf(ruta.getPoints().size()));
            new RefreshTramos().execute();
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



            LatLng loc;
            if (ruta.getTramos().size() == 0) {
                loc = new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());

            } else {
                loc = ruta.getFirstPoint();

            }
            Toast.makeText(getApplication(), String.valueOf(ruta.getMiniPoints().size()), Toast.LENGTH_LONG).show();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17.0f));






        }



        @Override
        protected void onPreExecute() {
            //googleMap.clear();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
