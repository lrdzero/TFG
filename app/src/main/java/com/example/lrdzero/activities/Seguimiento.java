package com.example.lrdzero.activities;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrdzero.datos.Conexion;
import com.example.lrdzero.datos.Reto;
import com.example.lrdzero.datos.Ruta;
import com.example.lrdzero.datos.Tramo;
import com.example.lrdzero.utils.Functions;
import com.example.lrdzero.utils.GPSAlarmService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Seguimiento  extends Activity implements LocationListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private GoogleMap googleMap;
    Functions functions;
    MapView mapView;
    MediaPlayer media;
    MediaPlayer alerta;
    MediaPlayer salidaRuta;
    private ArrayList<Tramo> tramosOF=new ArrayList<Tramo>();
    ArrayList<Reto> retosRuta = new ArrayList<Reto>();
    private int tamanio;
    private static final long POLLING_FREQ = 2000;
    private static final float MIN_DISTANCE = 3.0f;
    Marker markerLastPoint;
    private String musica;
    TextView textoGuia;
    ArrayList<Circle> circulos = new ArrayList<>();
    private CountDownTimer cuentaatras;
    private boolean cronoON;
    private int totalRetos;
    // Reference to the LocationManager and LocationListener
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    LocationClient mLocationClient;

    Ruta ruta;
    Conexion con;
    private boolean carga;
    private boolean retos;
    private String name;
    private int tipoRecorrido;
    private String creador,nombreRecorrido,nombreRuta,sexo,edad;
    ArrayList<LatLng> PtosRecorridos;
    int puntoactual=0;
    boolean inicio=false;
    boolean cargado=false;
    Circle circulo;
    String locationProvider;
    Chronometer crono;
    android.support.v7.app.AlertDialog.Builder dialogo2;

    private ImageView parpadoder;
    private ImageView parpadoiz;
    private ImageView brazoDer;
    private ImageView brazoIz;
    private ImageView cuerpo;
    private ImageView boca;
    private ImageView ojos;
    private ImageView boca_roja;
    private ImageView pitorro;
    private ImageView dientes;

    private Intent miService;

    //ArrayList<Array> ArrayTramos = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        mLocationClient.connect();
        stopService(miService);
        this.mLocationManager.requestLocationUpdates(this.locationProvider, 400, 1, this);
         media.setLooping(true);
        media.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        stopService(miService);
        this.mLocationManager.removeUpdates(this);
        media.setLooping(false);
        media.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mLocationManager.removeUpdates(this);
        startService(miService);






        //   mapView.onPause();
        //   media.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seguimiento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cuentaatras=new CountDownTimer(1200000, 1000) {

            public void onTick(long millisUntilFinished) {

                String time =String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );
                dialogo2.setMessage("Tiempo restante: " + time);
                dialogo2.show();



            }


            public void onFinish() {
                finish();
            }
        };


        dialogo2 = new android.support.v7.app.AlertDialog.Builder(Seguimiento.this);
        dialogo2.setTitle("Pausa");

        dialogo2.setCancelable(false);
        dialogo2.setIcon(R.drawable.pausa);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cuentaatras.cancel();
            }
        });




        //Carga Avatar
        parpadoder =(ImageView) findViewById(R.id.parpder);
        parpadoiz=(ImageView) findViewById(R.id.parpizq);
        brazoDer =(ImageView) findViewById(R.id.brazoder);
        brazoIz=(ImageView) findViewById(R.id.brazoizq);
        cuerpo =(ImageView) findViewById(R.id.cabeza);
        boca = (ImageView)findViewById(R.id.bocaverde);
        ojos = (ImageView)findViewById(R.id.ojos);
        boca_roja=(ImageView) findViewById(R.id.bocaroja);
        pitorro=(ImageView) findViewById(R.id.pitorro);
        dientes=(ImageView) findViewById(R.id.dientes);



        mapView = (MapView) findViewById(R.id.gmap);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);

        carga = getIntent().getExtras().getBoolean("tipo");
        name = getIntent().getExtras().getString("nombre");
        retos = getIntent().getExtras().getBoolean("retos");
        nombreRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nombreRuta=getIntent().getExtras().getString("nombreRuta");
        sexo=getIntent().getExtras().getString("sexo");
        edad=getIntent().getExtras().getString("edad");
        creador=getIntent().getExtras().getString("creador");
        tipoRecorrido=getIntent().getExtras().getInt("tipoRecorrido");


        alerta = MediaPlayer.create(this,R.raw.alert);
        salidaRuta= MediaPlayer.create(this,R.raw.metronomo);
        GPSAlarmService.setUpdateListener(this);
        miService=new Intent(Seguimiento.this,GPSAlarmService.class);
        cronoON=false;
        crono=(Chronometer) findViewById(R.id.chronometer3);
        crono.setVisibility(View.INVISIBLE);
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedMillis = SystemClock.elapsedRealtime() - crono.getBase();
                if (myElapsedMillis >= 120000) {
                    salidaRuta.setLooping(false);
                    salidaRuta.stop();
                    con.borrarMochila(creador, nombreRecorrido, nombreRuta);
                    finish();
                } else if (myElapsedMillis >= 90000) {
                    crono.setTextColor(Color.RED);
                }

            }
        });
        con = new Conexion();
        //musica=getIntent().getExtras().getString("musica");
        musica=con.obtenerMusicaUsuario(creador);
        if(musica.matches("")){
            media= MediaPlayer.create(this,R.raw.frog);
        }
       else{
            //musica.
            File f = Environment.getExternalStoragePublicDirectory(musica);
            if(f.exists()){
                    Uri uri = Uri.parse(musica);
                    media=MediaPlayer.create(this,uri);
                }
            else{

               // Toast.makeText(Seguimiento.this,"Archivo de audio "+Environment.getExternalStoragePublicDirectory(musica).toString()+" no encontrado.",Toast.LENGTH_LONG).show();
                media=MediaPlayer.create(this,R.raw.frog);
            }
        }

        adaptacion(sexo, edad);

        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(false);
        mLocationClient = new LocationClient(getApplicationContext(), this, this);
        mLocationClient.connect();



        ruta = new Ruta(name);
        ImageView bi = (ImageView)findViewById(R.id.brazoizq);
        final ImageView boca = (ImageView)findViewById(R.id.bocaverde);
        final ImageView ojos = (ImageView)findViewById(R.id.ojos);
        textoGuia =(TextView) findViewById(R.id.textodinamico);
        textoGuia.setText("Bienvenido a la ruta "+name);

        final ImageView sig =(ImageView) findViewById(R.id.siguiente);
        RelativeLayout tdin = (RelativeLayout)findViewById(R.id.relativetexto);


        tamanio = getIntent().getExtras().getInt("tamanioRuta");
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
       totalRetos=tamanioRetos;
        for(int i=0;i<tamanioRetos;i++){
            String nombre =getIntent().getExtras().getString("nombreReto" + i);
            int position = getIntent().getExtras().getInt("position"+i);
            Log.i("Prueba", Integer.toString(position));
            Reto nuevo = new Reto(nombre,googleMap.addMarker(new MarkerOptions().position(ruta.getMiniPoints().get(position)).title("prueba")),position);

            ruta.addReto(nuevo);

        }

        sig.setEnabled(false);
        sig.setVisibility(View.INVISIBLE);
        sig.setAdjustViewBounds(true);

        media.setLooping(true);
        media.start();


        PtosRecorridos= (ArrayList<LatLng>) ruta.getMiniPoints().clone();


        this.mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);



   	    //define the location manager criteria
     	Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        this.locationProvider = mLocationManager.getBestProvider(criteria, false);

        Location location = mLocationManager.getLastKnownLocation(locationProvider);


       	//initialize the location
        	if(location != null) {
         		    onLocationChanged(location);
       		}




        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                //cuentaatras.start();
                avance();

                //markerLastPoint.setPosition(ruta.getMiniPoints().get(puntoactual));
                //Toast.makeText(getApplication(),String.valueOf(Haversine(latLng, ruta.getMiniPoints().get(puntoactual))) , Toast.LENGTH_SHORT).show();

            }
        });
        final Animation pasa = PasaTexto();
        final Animation a = new AlphaAnimation(0,1);
        Interpolator i = new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) (1 - Math.sin(input * Math.PI));
            }
        };
        a.setInterpolator(i);
        a.setDuration(3000);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sig.setVisibility(View.VISIBLE);
                sig.startAnimation(pasa);
                boca.clearAnimation();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }


        });

        tdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasa.reset();
                pasa.cancel();

                sig.setEnabled(false);
                sig.setVisibility(View.INVISIBLE);
                if (googleMap != null) {

                    LatLng loc;
                    loc = ruta.getFirstPoint();
                    new RefreshTramos().execute();

                   // Toast.makeText(getApplication(), Integer.toString(ruta.getMiniPoints().size()) , Toast.LENGTH_SHORT).show();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));

                }


                textoGuia.setText("Situate en el inicio para comenzar");


            }
        });
        boca.startAnimation(habla());
        textoGuia.startAnimation(a);


    }

    private void avance() {
        //textoGuia.setText("Ya has completado un "+puntoactual*100/ruta.getMiniPoints().size()+"%");

        if (PtosRecorridos.size() > 1) {
            PtosRecorridos.remove(0);
            puntoactual++;
            textoGuia.setText("quedan " + PtosRecorridos.size() + " puntos");



            circulos.get(0).setCenter(circulos.get(circulos.size() - 1).getCenter());
            circulos.remove(0);




            int index;

            if((index=ruta.existsRetoIn(puntoactual))!=-1) {
                Toast.makeText(getApplication(),"reto "+String.valueOf(index) , Toast.LENGTH_SHORT).show();

                String nombre =ruta.getRetos().get(index).getNombre();
               // Toast.makeText(Seguimiento.this,"Nombre del reto "+  nombre,Toast.LENGTH_LONG).show();


                if(tipoRecorrido==1) {
                    Intent n2 = new Intent(Seguimiento.this, RetoDeportivo.class);
                    n2.putExtra("nombreUser", creador);
                    n2.putExtra("nombreRecorrido", nombreRecorrido);
                    n2.putExtra("nombreRuta", nombreRuta);
                    n2.putExtra("edad", edad);
                    n2.putExtra("sexo", sexo);
                    n2.putExtra("nombreReto", nombre);
                    alerta.start();
                    startActivity(n2);
                }
                else if(tipoRecorrido==0){
                    Intent n2 = new Intent(Seguimiento.this, RetoCultural.class);
                    n2.putExtra("nombreUser", creador);
                    n2.putExtra("nombreRecorrido", nombreRecorrido);
                    n2.putExtra("nombreRuta", nombreRuta);
                    n2.putExtra("edad", edad);
                    n2.putExtra("sexo", sexo);
                    n2.putExtra("nombreReto", nombre);
                    alerta.start();
                    startActivity(n2);
                }


            }


        }
        else{
            textoGuia.setText("FIN");
            Intent nueva = new Intent(Seguimiento.this,Finalruta.class);
            nueva.putExtra("creador",creador);
            nueva.putExtra("nombreRecorrido",nombreRecorrido);
            nueva.putExtra("nombreRuta",nombreRuta);
            nueva.putExtra("totalRetos",totalRetos);
            nueva.putExtra("tipoRecorrido",tipoRecorrido);
            nueva.putExtra("sexo",sexo);
            nueva.putExtra("edad",edad);
            startActivity(nueva);
        }

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

    @Override
    public void onLocationChanged(Location location) {
        if(cargado){
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());


            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
            markerLastPoint.setPosition(loc);
            if(inicio){
                circulo.setCenter(loc);
                if(functions.Haversine(PtosRecorridos.get(0), loc)<20) {
                    if(cronoON) {
                        crono.setVisibility(View.INVISIBLE);
                        cronoON=false;
                        salidaRuta.setLooping(false);
                        salidaRuta.stop();
                        crono.stop();
                    }
                    textoGuia.setText("punto:" + puntoactual + "/" + ruta.getMiniPoints().size());



                    if (functions.Haversine(PtosRecorridos.get(1), loc) <= (functions.Haversine(PtosRecorridos.get(0), loc))||(functions.Haversine(PtosRecorridos.get(1), loc))<15) {
                        avance();
                    }


                }
                else {
                    textoGuia.setText("CUIDADO TE ESTAS SALIENDO DE LA RUTA, VUELVE ANTES DE 2 MINUTOS");
                    if(!cronoON) {
                        crono.setVisibility(View.VISIBLE);
                        crono.setBase(SystemClock.elapsedRealtime());
                        cronoON=true;
                        salidaRuta.setLooping(true);
                        salidaRuta.start();
                        crono.start();
                    }
                }
            }
            else {
                if (functions.Haversine(loc, ruta.getFirstPoint()) < 20) {
                    inicio = true;
                    textoGuia.setText("Listo, Comencemos");

                }
            }



        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /*@method: Hebra asíncrona que traza la ruta en el mapa
     */
    private class RefreshTramos extends AsyncTask<Void, Void, PolylineOptions> {


        @Override
        protected PolylineOptions doInBackground(Void... params) {

            PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

            for (LatLng p : ruta.getPoints()) {
                rectLine.add(p);
            }

            return rectLine;
        }


        @Override
        protected void onPostExecute(PolylineOptions result) {

            googleMap.addPolyline(result);
            if (ruta.getTramos().size() > 0) {
                circulo = googleMap.addCircle(new CircleOptions()
                        .center(ruta.getFirstPoint())
                        .radius(20)
                        .strokeColor(Color.RED));
               // markerLastPoint=googleMap.addMarker(new MarkerOptions().position(ruta.getPoints().get(puntoactual)).title(String.valueOf(puntoactual)));


            }
            cargado=true;
            for(int i=0;i<ruta.getMiniPoints().size();i++){
                circulos.add( googleMap.addCircle(new CircleOptions()
                        .center(ruta.getMiniPoints().get(i))
                        .fillColor(Color.BLUE)
                        .radius(0.5)
                        .strokeColor(Color.BLUE)));

            }
            markerLastPoint=googleMap.addMarker(new MarkerOptions().position(ruta.getPoints().get(puntoactual)).title("prueba"));




        }


        @Override
        protected void onPreExecute() {
            googleMap.clear();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }


    public AnimationSet habla(){

        Animation habla = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.03);
        habla.setDuration(500);
        habla.setRepeatCount(Animation.INFINITE);
        habla.setRepeatMode(Animation.REVERSE);
        habla.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = rand.nextInt((300 - 50) + 1) + 50;
                animation.setDuration(randomNum);
            }
        });

        AnimationSet as = new AnimationSet(true);
        as.addAnimation(habla);
        return as;
    }

    public Animation PasaTexto(){
        Animation agrandaSig = new ScaleAnimation((float)0.25,(float)1.25,(float)0.25,(float)1.25,Animation.RELATIVE_TO_SELF,(float)0.50,Animation.RELATIVE_TO_SELF,(float)0.50);

        agrandaSig.setDuration(500);
        agrandaSig.setRepeatCount(Animation.INFINITE);
        agrandaSig.setRepeatMode(Animation.REVERSE);


        return agrandaSig;
    }





    private void adaptacion(String sexo,String edad){
        if(tipoRecorrido==0) {

            boca.setImageResource(R.drawable.labio_superior);
            cuerpo.setImageResource(R.drawable.cuerpo_leon);
            ojos.setImageResource(R.drawable.ojos_leon);
            boca_roja.setImageResource(R.drawable.labio_inferior);
            pitorro.setImageResource(R.drawable.pitorro);
            dientes.setImageResource(R.drawable.dientes);

            parpadoder.setVisibility(View.INVISIBLE);
            parpadoiz.setVisibility(View.INVISIBLE);
            brazoIz.setVisibility(View.INVISIBLE);
            brazoDer.setVisibility(View.INVISIBLE);
        }
        else if(sexo.equals("H")){
            if(Integer.valueOf((edad))<18){
                boca.setImageResource(R.drawable.boca_n);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_n);
                parpadoiz.setImageResource(R.drawable.parpadoizq_n);
                brazoIz.setImageResource(R.drawable.manoizq);
                brazoDer.setImageResource(R.drawable.manoder);
                cuerpo.setImageResource(R.drawable.cuerpo_n);
            }
            else if(Integer.valueOf(edad)>=18&&Integer.valueOf(edad)<57) {
                boca.setImageResource(R.drawable.boca);
                ojos.setImageResource(R.drawable.ojos);
                //insertaMujer();
                parpadoder.setImageResource(R.drawable.parpadoder);
                parpadoiz.setImageResource(R.drawable.parpadoizq);
                brazoIz.setImageResource(R.drawable.manoizq);
                brazoDer.setImageResource(R.drawable.manoder);
                cuerpo.setImageResource(R.drawable.cuerpo);
            }
            else{
                boca.setImageResource(R.drawable.boca_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_a);
                brazoIz.setImageResource(R.drawable.manoizq_a);
                brazoDer.setImageResource(R.drawable.manoder_a);
                cuerpo.setImageResource(R.drawable.cuerpo_a);
            }
        }
        else{
            if(Integer.valueOf((edad))<18){
                boca.setImageResource(R.drawable.boca_h_n);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_h_n);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h_n);
                brazoIz.setImageResource(R.drawable.manoizq_h_n);
                brazoDer.setImageResource(R.drawable.manoder_h_n);
                cuerpo.setImageResource(R.drawable.cuerpo_h_n);
            }
            else if(Integer.valueOf(edad)>=18&&Integer.valueOf(edad)<57) {
                boca.setImageResource(R.drawable.boca_h);
                ojos.setImageResource(R.drawable.ojos);
                //insertaMujer();
                parpadoder.setImageResource(R.drawable.parpadoder_h);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h);
                brazoIz.setImageResource(R.drawable.manoizq_h);
                brazoDer.setImageResource(R.drawable.manoder_h);
                cuerpo.setImageResource(R.drawable.cuerpo_h);
            }
            else{
                boca.setImageResource(R.drawable.boca_h_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_h_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h_a);
                brazoIz.setImageResource(R.drawable.manoizq_h_a);
                brazoDer.setImageResource(R.drawable.manoder_h_a);
                cuerpo.setImageResource(R.drawable.cuerpo_h_a);
            }

        }
    }
    public void finalizar(){
       // Toast.makeText(Seguimiento.this,"Entro en finalizar",Toast.LENGTH_LONG).show();
        finish();
    }
}

