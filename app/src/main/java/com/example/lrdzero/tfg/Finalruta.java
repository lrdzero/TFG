package com.example.lrdzero.tfg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Finalruta extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    private Conexion con;
    private ArrayList<String> datosMochila = new ArrayList<String>();
    private ArrayList<Items> elementosMochila=new ArrayList<Items>();
    private String creador,nombreRecorrido,nombreRuta;
    private int totalRetos,porcentaje;
    private ArrayList<Items> items;
    private String historiaFinal="";
    private int tipoRecorrido;
    private String edad;
    private String sexo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalruta);
        con = new Conexion();
        final Animation af = AnimationUtils.loadAnimation(this,R.anim.animfinal);
        tv = (TextView)findViewById(R.id.textitem);
        tv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.item);
        final ImageView flecha = (ImageView) findViewById(R.id.siguiente);
        flecha.setVisibility(View.INVISIBLE);
        items=new ArrayList<>();

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


        creador=getIntent().getExtras().getString("creador");
        tipoRecorrido=getIntent().getExtras().getInt("tipoRecorrido");
        sexo=getIntent().getExtras().getString("sexo");
        edad=getIntent().getExtras().getString("edad");

        nombreRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nombreRuta=getIntent().getExtras().getString("nombreRuta");
        totalRetos=getIntent().getExtras().getInt("totalRetos");
        porcentaje=0;

        datosMochila=con.cargarMochila(creador,nombreRecorrido,nombreRuta);
        historiaFinal=con.finalHistoria(nombreRuta);
        Toast.makeText(Finalruta.this,"Historia final "+historiaFinal,Toast.LENGTH_LONG).show();
        adaptacion(sexo,edad);
        loadItems();


       // porcentaje=(elementosMochila.size()*100)/totalRetos;
        porcentaje=60;



        tv.setText("¡Felicidades! Has recorrido la ruta con el "+porcentaje+"% de los retos completados");
        //recomps.add(new Items());

        flecha.startAnimation(PasaTexto());

        final RelativeLayout avatar = (RelativeLayout)findViewById(R.id.Avatar);
        af.setFillEnabled(true);
        af.setFillAfter(true);


        final Animation ocultar = AnimationUtils.loadAnimation(this,R.anim.abc_fade_out);
        ocultar.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ocultar.setDuration(2000);




        af.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                flecha.getAnimation().cancel();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation saltito = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animloco);


                Animation crazy = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animloco);
                RelativeLayout todo =(RelativeLayout)findViewById(R.id.todo);
                todo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(Finalruta.this,HistorialUsuario.class);
                        i.putExtra("userName",creador);

                        startActivity(i);
                    }
                });
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(R.drawable.brujula);

                items.add(new Items("pepe", R.drawable.pesas, "", "petada"));
                items.add(new Items("pepe2", R.drawable.barba, "", "..."));
                items.add(new Items("pepeee", R.drawable.brazoder_n, "", "aymama"));
                ocultar.cancel();
                ocultar.reset();
                ocultar.setDuration(2000);
                ocultar.setRepeatCount(Animation.INFINITE);
                ocultar.setRepeatMode(Animation.REVERSE);
                ocultar.setAnimationListener(new Animation.AnimationListener() {

                    double i = 0;

                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.i("repeat", String.valueOf(i));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        int indice = (int) (i % items.size());
                        if (items.get(indice).getImage() != 0) {
                            iv.setImageResource(items.get(indice).getImage());
                            // tv.setText(items.get(indice).getDescripcionRecompensa());

                        } else {
                            Uri n = Uri.parse(items.get(indice).getSeconFoto());
                            iv.setImageURI(n);
                            //  tv.setText(items.get(indice).getDescripcionRecompensa());
                        }
                        i+=0.5;


                    }
                });
                iv.startAnimation(ocultar);
                saltito.setDuration(1000);
               // iv.startAnimation(saltito);


                //ojos.startAnimation(crazy);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                tv.clearAnimation();
                iv.clearAnimation();
                flecha.getAnimation().cancel();
                boca.clearAnimation();
                flecha.getAnimation().reset();


                if(porcentaje!=100){
                    Intent i = new Intent(Finalruta.this,SeleccionRecorridos.class);
                    i.putExtra("NombreUser",creador);
                    startActivity(i);

                }




                if(elementosMochila.size()>0){
                    if(elementosMochila.get(0).getImage()!=0) {
                        iv.setImageResource(elementosMochila.get(0).getImage());
                        tv.setText(elementosMochila.get(0).getDescripcionRecompensa());
                        elementosMochila.remove(0);
                    }
                    else{
                        Uri n= Uri.parse(elementosMochila.get(0).getSeconFoto());
                        iv.setImageURI(n);
                        tv.setText(elementosMochila.get(0).getDescripcionRecompensa());
                        elementosMochila.remove(0);
                    }
                        tv.startAnimation(Mostrar(flecha));
                        iv.startAnimation(Mostrar(null));

                }
                else {
                    tv.setVisibility(View.INVISIBLE);
                    iv.setVisibility(View.INVISIBLE);
                    avatar.startAnimation(af);
                }


            }
        });

        boca.startAnimation(habla());
        tv.startAnimation(Mostrar(flecha));
        tv.setVisibility(View.VISIBLE);

    }
    private void loadItems(){
        if(!datosMochila.isEmpty()){
            for(int i=0;i<datosMochila.size();i=i+4){
                if(datosMochila.get(i+3).equals("1")) {
                    elementosMochila.add(new Items(datosMochila.get(i), Integer.valueOf(datosMochila.get(i+1)), "", datosMochila.get(i+2)));
                }
                else if(datosMochila.get(i+3).equals("2")){
                    Items n = new Items();
                    n.setName(datosMochila.get(i));
                    n.setSeconFoto(datosMochila.get(i+1));
                    n.setNombreReto("");
                    n.setDescripcionRecompensa(datosMochila.get(i+2));
                    elementosMochila.add(n);
                }
            }
            items= (ArrayList<Items>) elementosMochila.clone();


            con.updateRecorren(creador,nombreRuta);
            con.borrarMochila(creador,nombreRecorrido,nombreRuta);
        }

    }
    public AnimationSet habla(){

        Animation habla = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.03);
        habla.setDuration(500);
        habla.setRepeatCount(20);
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

    public  AnimationSet GiroOjos(){

        Animation mirada = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.035);
        Animation mirada2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)-0.035);

        mirada.setDuration(1000);
        mirada2.setDuration(1000);
        mirada2.setStartOffset(3000);


        AnimationSet as = new AnimationSet(true);
        as.addAnimation(mirada);
        as.addAnimation(mirada2);


        return as;

    }

    public Animation Mostrar(final View v){

        final Animation mostrar= AnimationUtils.loadAnimation(this,R.anim.abc_fade_in);
        mostrar.setDuration(2000);
        mostrar.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (v != null)
                    v.startAnimation(PasaTexto());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return mostrar;
    }
    private Drawable reduceImagen(Uri unUri,int tam1,int tam2){
        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(unUri);
            yourDrawable = Drawable.createFromStream(inputStream, unUri.toString());
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.pesas);
        }
        Bitmap bitmap = ((BitmapDrawable) yourDrawable).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, tam1, tam2, true));
        yourDrawable.invalidateSelf();
        return d;
    }
    private void adaptacion(String sexo,String edad){
        if(tipoRecorrido==0) {
            ImageView cuerpo=(ImageView) findViewById(R.id.cabeza);
            ImageView boca_roja=(ImageView) findViewById(R.id.bocaroja);
            ImageView pitorro=(ImageView) findViewById(R.id.pitorro);
            ImageView dientes=(ImageView) findViewById(R.id.dientes);

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
}
