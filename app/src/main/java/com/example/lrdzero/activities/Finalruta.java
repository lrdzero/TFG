package com.example.lrdzero.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrdzero.animations.Animations;
import com.example.lrdzero.data.Conexion;
import com.example.lrdzero.data.Recompensa;

import java.util.ArrayList;

/**
 * Clase para lanzar la animación final.
 */
public class Finalruta extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    private Conexion con;
    private ArrayList<String> datosMochila = new ArrayList<String>();
    private ArrayList<Recompensa> elementosMochila=new ArrayList<Recompensa>();
    private String creador,nombreRecorrido,nombreRuta;
    private int totalRetos,porcentaje;
    private ArrayList<Recompensa> items;
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
    private ImageView cola;
    private Animations animations;

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
        animations= new Animations();

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
        cola=(ImageView) findViewById(R.id.cola);


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


        porcentaje=(elementosMochila.size()*100)/totalRetos;




        tv.setText("¡Felicidades! Has recorrido la ruta con el "+porcentaje+"% de los retos completados");


        flecha.startAnimation(animations.PasaTexto());

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

                items.add(new Recompensa("pepe", R.drawable.pesas, "", "petada"));
                items.add(new Recompensa("pepe2", R.drawable.barba, "", "..."));
                items.add(new Recompensa("pepeee", R.drawable.brazoder_n, "", "aymama"));
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

        boca.startAnimation(animations.habla());
        tv.startAnimation(Mostrar(flecha));
        tv.setVisibility(View.VISIBLE);

    }

    /**
     * Función para cargar la mochila del usuario.
     */
    private void loadItems(){
        if(!datosMochila.isEmpty()){
            for(int i=0;i<datosMochila.size();i=i+4){
                if(datosMochila.get(i+3).equals("1")) {
                    elementosMochila.add(new Recompensa(datosMochila.get(i), Integer.valueOf(datosMochila.get(i+1)), "", datosMochila.get(i+2)));
                }
                else if(datosMochila.get(i+3).equals("2")){
                    Recompensa n = new Recompensa();
                    n.setName(datosMochila.get(i));
                    n.setSeconFoto(datosMochila.get(i+1));
                    n.setNombreReto("");
                    n.setDescripcionRecompensa(datosMochila.get(i+2));
                    elementosMochila.add(n);
                }
            }
            items= (ArrayList<Recompensa>) elementosMochila.clone();


            con.updateRecorren(creador,nombreRuta);
            con.borrarMochila(creador,nombreRecorrido,nombreRuta);
        }

    }

    /**
     * Función que lanza animación
     * @param v
     * @return mostrar
     */
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
                    v.startAnimation(animations.PasaTexto());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return mostrar;

    }

    /**
     * Función de adaptación de guía.
     * @param sexo
     * @param edad
     */
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
                cola.setImageResource(R.drawable.cola_n);
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
                cola.setImageResource(R.drawable.cola);
            }
            else{
                boca.setImageResource(R.drawable.boca_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_a);
                brazoIz.setImageResource(R.drawable.manoizq_a);
                brazoDer.setImageResource(R.drawable.manoder_a);
                cuerpo.setImageResource(R.drawable.cuerpo_a);
                cola.setImageResource(R.drawable.cola_a);
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
                cola.setImageResource(R.drawable.cola_h_n);
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
                cola.setImageResource(R.drawable.cola_h);
            }
            else{
                boca.setImageResource(R.drawable.boca_h_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_h_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h_a);
                brazoIz.setImageResource(R.drawable.manoizq_h_a);
                brazoDer.setImageResource(R.drawable.manoder_h_a);
                cuerpo.setImageResource(R.drawable.cuerpo_h_a);
                cola.setImageResource(R.drawable.cola_h_a);
            }

        }
    }
}
