package com.example.lrdzero.tfg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RecorridosParaUsuario extends Activity implements View.OnClickListener{

    private List<DatosRyR> dt = new ArrayList<>();
    private TextView tituloRecorrido;
    private TextView textoGuia;
    private ImageView sig;
    private boolean recorr;
    private final static String TITULO ="Recorridos para tí";
    private final static String TITULO2 ="Rutas Disponibles";
    private final static String TITULO3 ="HAGÁMOSLO. Pulsa la imagen de ruta en la que quieras participar";
    private final static String TITULO4 ="Selecciona el recorrido en el que quieras participar y pulsa la imagen";
    private ListView listView;
    private ArrayAdapter<DatosRyR>  adapter,adapter2;
    private String musica;

    private static Socket sk;
    private static int port=7;
    private static String ip="192.168.1.33";
    private String selected;
    private String nombreABuscar,creador;
    private int tipo;
    private String sexo,edad;

    private ImageView parpadoder;
    private ImageView parpadoiz;
    private ImageView brazoDer;
    private ImageView brazoIz;
    private ImageView cuerpo;
    private ImageView boca;
    private ImageView ojos;

    private Conexion con;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos_para_usuario);

        tituloRecorrido =(TextView) findViewById(R.id.TextoRecorrido);
        ImageView bi = (ImageView)findViewById(R.id.brazoizq);


        textoGuia =(TextView) findViewById(R.id.textodinamico);
        final RelativeLayout tdin =(RelativeLayout)findViewById(R.id.relativetexto);
        tdin.setVisibility(View.INVISIBLE);

        textoGuia.setText(TITULO3);
        sig =(ImageView) findViewById(R.id.siguiente);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        parpadoder =(ImageView) findViewById(R.id.parpder);
        parpadoiz=(ImageView) findViewById(R.id.parpizq);
        brazoDer =(ImageView) findViewById(R.id.brazoder);
        brazoIz=(ImageView) findViewById(R.id.brazoizq);
        cuerpo =(ImageView) findViewById(R.id.cabeza);
        boca = (ImageView)findViewById(R.id.bocaverde);
        ojos = (ImageView)findViewById(R.id.ojos);


       // sig.setEnabled(false);
        //sig.setVisibility(View.INVISIBLE);

        //sig.setAdjustViewBounds(true);
       // sig.setOnClickListener(this);
        textoGuia.setText(TITULO4);
        recorr =true;
        con = new  Conexion();
        tipo=getIntent().getExtras().getInt("tipo");
        sexo=getIntent().getExtras().getString("sexo");
        edad=getIntent().getExtras().getString("edad");
        creador=getIntent().getExtras().getString("creador");
        musica=getIntent().getExtras().getString("musica");

        //adaptacion(sexo,edad);

        Create();


        final AnimationSet habla = habla();

        ListaView();
        final Animation a = new AlphaAnimation(0,  1);
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
                //sig.setVisibility(View.VISIBLE);
                //sig.startAnimation(PasaTexto());
                boca.clearAnimation();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Animation s = saludo(getApplicationContext());
        s.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ojos.startAnimation(GiroOjos());
                tdin.setVisibility(View.VISIBLE);
                textoGuia.startAnimation(a);
                boca.startAnimation(habla);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        bi.startAnimation(s);


    }

    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.siguiente:
                /*
                Intent mp = new Intent(RecorridosParaUsuario.this,Seguimiento.class);
                mp.putExtra("tipo",true);
                mp.putExtra("nombre",nombreABuscar);
                startActivity(mp);
                break;
                */
            case R.id.button2:
                tituloRecorrido.setText(TITULO);

                sig.setEnabled(false);
                sig.setVisibility(View.INVISIBLE);
                textoGuia.setText(TITULO4);
                dt.clear();
                Create();
                ListaView();
                listView.setAdapter(adapter);
                recorr=true;
                break;


        }

    }

    public void Create(){
        String edad, pref1,pref2,dificultad;
        edad=getIntent().getExtras().getString("edad");

        pref1 = getIntent().getExtras().getString("pref1");
        pref2 =getIntent().getExtras().getString("pref2");
        dificultad=getIntent().getExtras().getString("dificultad");
        dt=con.cargaDeRecorridosPorAdaptacion(tipo,edad,pref1,pref2,dificultad);

        //dt.add(new DatosRyR("RECORRIDO 1", "2", "Ruta de muestra inicial 1", "JAVIEL RAMBIEL",R.drawable.f0907,"Una ruta POSICION 1 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        //dt.add(new DatosRyR("RECORRIDO 2", "3", "Ruta de muestra inicial 2", "ISMAEL",R.drawable.f0907,"Una ruta POSICION 2 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        //dt.add(new DatosRyR("RECORRIDO 3", "4", "Ruta de muestra inicial 3", "FEDERICO",R.drawable.f0907,"Una ruta POSICION 3 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        //dt.add(new DatosRyR("RECORRIDO 4", "7", "Ruta de muestra inicial 4", "PAQUITO",R.drawable.f0907,"Una ruta POSICION 4 que no tiene nada por el momento y que es utilizada a modo de prueba"));

    }

    private void ListaView(){
        adapter= new PlaceList();
        listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(recorr)
                    textoGuia.setText(dt.get(position).getDescription());



           }
       });
    }

    public class PlaceList extends ArrayAdapter<DatosRyR>{

            public PlaceList(){
                super(RecorridosParaUsuario.this,R.layout.activity_listas_con_imagen,dt);
            }

        public View getView(final int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_listas_con_imagen,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.imageView8);
            final TextView txt1 = (TextView) intenView.findViewById(R.id.NombreRecorrido);
            final TextView txt2 = (TextView) intenView.findViewById(R.id.nRutas);
            final TextView txt3 = (TextView) intenView.findViewById(R.id.textView7);

            final DatosRyR currentData = dt.get(position);

            img.setImageResource(currentData.getImage());

            txt1.setText(currentData.getName());
            txt2.setText("Nº de Rutas: "+currentData.getNumber());
            txt3.setText("Descripcion: "+currentData.getDescription());


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dt.clear();


                    dt = con.cargaDeRutas(txt1.getText().toString());

                    //dt.add(new DatosRyR("RUTA PRIMERA", "1", "Ruta de muestra inicial", "JAVIEL RAMBIEL",R.drawable.f0907,"Una ruta inicial que no tiene nada por el momento y que es utilizada a modo de prueba"));
                        adapter2 = new PlaceList2(currentData.getName());
                    nombreABuscar=currentData.getName();

                        tituloRecorrido.setText(TITULO2);
                        textoGuia.setText(TITULO3);
                        listView.setAdapter(adapter2);
                        recorr =false;


                }
            });


            return intenView;
        }

    }

    public class PlaceList2 extends ArrayAdapter<DatosRyR>{
        private String nameRoute;
        public PlaceList2(String name){

            super(RecorridosParaUsuario.this,R.layout.activity_listas_con_imagen,dt);
            nameRoute=name;
        }

        public View getView(int position,View convertView, ViewGroup parent){
            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_listas_con_imagen,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.imageView8);
            final TextView txt1 = (TextView) intenView.findViewById(R.id.NombreRecorrido);
            TextView txt2 = (TextView) intenView.findViewById(R.id.nRutas);
            TextView txt3 = (TextView) intenView.findViewById(R.id.textView7);

            final DatosRyR currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());
            txt2.setText("Nº retos: "+currentData.getNumber());
            txt3.setText("Historia: "+currentData.getLargeDescription());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mp = new Intent(RecorridosParaUsuario.this, Seguimiento.class);
                    mp.putExtra("tipo", true);
                    mp.putExtra("nombre", currentData.getName());
                    mp.putExtra("retos", false);
                    mp.putExtra("creador",creador);
                    mp.putExtra("sexo",sexo);
                    mp.putExtra("edad",edad);
                    mp.putExtra("nombreRecorrido",nombreABuscar);
                    mp.putExtra("nombreRuta",txt1.getText().toString());
                    mp.putExtra("tipoRecorrido",tipo);
                    mp.putExtra("musica",musica);
                    Ruta ruta = new Ruta("ruta a");
                    ruta.setTramos(con.cargarVisionRuta(currentData.getName()));
                    ArrayList<Tramo> tramos = ruta.getTramos();
                    mp.putExtra("tamanioRuta", tramos.size());


                    for (int i = 0; i < tramos.size(); i++) {
                        LatLng origen = tramos.get(i).getOrigen();
                        LatLng end = tramos.get(i).getFinal();
                        String n1 = "tramoLatOrigen" + Integer.toString(i);
                        String n2 = "tramoLongOrigen" + Integer.toString(i);
                        String n3 = "tramoLatFinal" + Integer.toString(i);
                        String n4 = "tramoLongFinal" + Integer.toString(i);
                        //Toast.makeText(CrearNuevoRecorrido.this,n1,Toast.LENGTH_LONG).show();

                        //Toast.makeText(CrearNuevoRecorrido.this,Double.toString(end.latitude),Toast.LENGTH_LONG).show();
                        mp.putExtra(n1, origen.latitude);
                        mp.putExtra(n2, origen.longitude);
                        mp.putExtra(n3, end.latitude);
                        mp.putExtra(n4, end.longitude);
                    }
                    ArrayList<DatosRyR> retosRuta = con.cargaDeRetos(currentData.getName());
                    mp.putExtra("tamanioRetos", retosRuta.size());

                    for (int i = 0; i < retosRuta.size(); i++) {
                        mp.putExtra("nombreReto" + i, retosRuta.get(i).getName());
                        mp.putExtra("position" + i, retosRuta.get(i).getPosition());
                    }
                    startActivity(mp);
                }
            });


            return intenView;
        }
    }

    //***********   ANIMACIONES

    public Animation saludo(Context c){


        return AnimationUtils.loadAnimation(c, R.anim.animacionbrazoizq);
    }

    public  AnimationSet GiroOjos(){

        Animation mirada = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.035,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        Animation mirada2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)-0.035,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);

        mirada.setDuration(1800);
        mirada2.setDuration(1800);
        mirada2.setStartOffset(3000);


        AnimationSet as = new AnimationSet(true);
        as.addAnimation(mirada);
        as.addAnimation(mirada2);


        return as;

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




    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();

        overridePendingTransition(R.anim.animstart, R.anim.animend);
    }
    private void adaptacion(String sexo,String edad){

        if(sexo.equals("H")){
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
}