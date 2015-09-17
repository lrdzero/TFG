package com.example.lrdzero.tfg;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.w3c.dom.Document;

import java.util.ArrayList;


public class Mapa extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    GoogleMap googleMap;
    MapView mapView;
    private double latitude;
    private double longitude;
    LocationClient mLocationClient;
    LatLng LastPoint;
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

        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext(), this, this);
        mLocationClient.connect();

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


                if(LastPoint==null) {
                    loc = new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                }
                else
                    loc=LastPoint;

                Directions dir = new Directions();

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
                }

            }
        });

        /*LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        Location location = locationManager.getLastKnownLocation(provider);

        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        LatLng coordinate = new LatLng(lat, lng);*///

      //  CameraUpdate center=new CameraUpdateFactory.newLatLng(coordinate);//;e(); new CameraUpdateFactory.newLatLng(coordinate);
      //  CameraUpdate zoom=;

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //googleMap.getUiSettings();
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
}
