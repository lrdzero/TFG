package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;




public class RetoCultural extends Activity implements View.OnClickListener {
    private ArrayList<Items> dt = new ArrayList<Items>();
    private ListView lista;
    private HorizontalListView lista2;
    private ArrayAdapter<Items> adapter;
    private TextView pregunta;
    private String respuestaCorrecta;
    private Button respuesta;
    private RadioButton resp1,resp2,resp3,resp4;
    private RadioGroup group1,group2;
    private AlertDialog alert;
    private String edad,sexo;
    private Conexion con;
    private String nombreAuxiliar;
    private MediaPlayer mp;
    private MediaPlayer error;
    private ArrayList<String> construyeRespuesta=new ArrayList<String>();
    private String nombreRecorrido,nombreRuta,creador,nombreReto;
    private ImageView parpadoder;
    private ImageView parpadoiz;
    private ImageView brazoDer;
    private ImageView brazoIz;
    private ImageView cuerpo;
    private ImageView boca;
    private ImageView ojos;
    Animation anim;

    private static int selected =0 ;
    public void onResume(){
        super.onResume();
        mp.setLooping(true);
        mp.start();
    }
    public void onPause(){
        super.onPause();
        mp.setLooping(false);
        mp.stop();
    }
    public void onDestroy(){
        super.onDestroy();
        mp.setLooping(false);
        mp.stop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_cultural);
        con=new Conexion();
        //group1 =(RadioGroup) findViewById(R.id.rGroup1);
        //group2 =(RadioGroup) findViewById(R.id.rGroup2);
        //Carga Avatar
        parpadoder =(ImageView) findViewById(R.id.parpder);
        parpadoiz=(ImageView) findViewById(R.id.parpizq);
        brazoDer =(ImageView) findViewById(R.id.brazoder);
        brazoIz=(ImageView) findViewById(R.id.brazoizq);
        cuerpo =(ImageView) findViewById(R.id.cabeza);
        boca = (ImageView)findViewById(R.id.bocaverde);
        ojos = (ImageView)findViewById(R.id.ojos);

        resp1 = (RadioButton) findViewById(R.id.responseA);
        resp2 = (RadioButton) findViewById(R.id.responseB);
        resp3 = (RadioButton) findViewById(R.id.responseC);
        resp4 = (RadioButton) findViewById(R.id.responseD);
        pregunta =(TextView) findViewById(R.id.pregunta);
        respuesta = (Button) findViewById(R.id.buttonResponse);
        nombreRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nombreRuta=getIntent().getExtras().getString("nombreRuta");
        creador=getIntent().getExtras().getString("nombreUser");
        edad=getIntent().getExtras().getString("edad");
        sexo=getIntent().getExtras().getString("sexo");
        nombreReto=getIntent().getExtras().getString("nombreReto");
        //Toast.makeText(RetoCultural.this,sexo,Toast.LENGTH_LONG).show();
        //adaptacion(sexo,edad);
        DatosRyR dt =con.buscarDatosRetoCultural(nombreReto);
        nombreAuxiliar=dt.getName();

        pregunta.setText(dt.getDescription());
        respuestaCorrecta =dt.getRespuesta();
        resp1.setText(dt.getOther());
        resp2.setText(dt.getNumber());
        resp3.setText(dt.getAdic());
        resp4.setText(dt.getAux());

        mp=MediaPlayer.create(this,R.raw.metronomo);
        resp1.setOnClickListener(this);
        resp2.setOnClickListener(this);
        resp3.setOnClickListener(this);
        resp4.setOnClickListener(this);
        respuesta.setOnClickListener(this);
        mp.setLooping(true);
        mp.start();


    }

    private void ListaView(){
        /*
        adapter= new PlaceList();
        //lista2 = (HorizontalListView) findViewById(R.id.listaMochila);
        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Items currentData = (Items) lista2.getItemAtPosition(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(RetoCultural.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Quieres utilizar este Item ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        aceptar(currentData);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();


            }
        });
        lista2.setAdapter(adapter);
        */
    }

    public class PlaceList extends ArrayAdapter<Items> {
        Items currentData;
        public PlaceList(){
            super(RetoCultural.this,R.layout.activity_lista_horizontal_mochila,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


                currentData = dt.get(position);

            img.setImageResource(R.drawable.busto);
            txt1.setText(currentData.getName());


             return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void loadItems(){
       // dt.add(new Items("nombre 1",R.drawable.busto ));
       // dt.add(new Items("nombre 2",R.drawable.busto ));
       // dt.add(new Items("nombre 3",R.drawable.busto ));
       // dt.add(new Items("nombre 4",R.drawable.busto ));


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.responseA:
               construyeRespuesta.add("A");
                resp1.setEnabled(false);
                break;
            case R.id.responseB:
               // group2.clearCheck();
                construyeRespuesta.add("B");
                break;
            case R.id.responseC:
                //group1.clearCheck();
                construyeRespuesta.add("C");
                break;
            case R.id.responseD:
                //group1.clearCheck();
                construyeRespuesta.add("D");
                break;
            case R.id.buttonResponse:
                //String.
                //construyeRespuesta.
                Collections.sort(construyeRespuesta);
                String comparador="";
                for(int i=0;i<construyeRespuesta.size();i++){
                    comparador+=construyeRespuesta.get(i);
                }

                        //Toast.makeText(RetoCultural.this, "La respuesta es " + respuestaCorrecta, Toast.LENGTH_LONG).show();
                       // Toast.makeText(RetoCultural.this, "Tu respuesta ha sido " + comparador, Toast.LENGTH_LONG).show();
                anim = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animacionreto);
                Animation izq = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animcelebra);
                Animation der = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.animcelebrader);
                Animation pizq = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animtristeizq);
                Animation pder = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animtristeder);
                Animation bocaanim = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animabreboca);
                bocaanim.setFillEnabled(true);
                bocaanim.setFillAfter(true);

                ImageView resultado = (ImageView) findViewById(R.id.resultado);


                resultado.bringToFront();

                ImageView brazoizq=(ImageView)findViewById(R.id.brazoizq);
                ImageView brazoder=(ImageView)findViewById(R.id.brazoder);
                ImageView parpadoizq=(ImageView)findViewById(R.id.parpizq);
                ImageView parpadoder=(ImageView)findViewById(R.id.parpder);
                ImageView boca=(ImageView)findViewById(R.id.bocaverde);




                if(comparador.equals(respuestaCorrecta)){
                    //Toast.makeText(RetoCultural.this,"Respuesta Correcta",Toast.LENGTH_LONG).show();

                    final Intent premio = new Intent(RetoCultural.this,RecogerPremio.class);
                    premio.putExtra("nombreUser",creador);
                    premio.putExtra("nombreRecorrido",nombreRecorrido);
                    premio.putExtra("nombreRuta",nombreRuta);
                    premio.putExtra("nombreReto",nombreAuxiliar);
                    premio.putExtra("edad",edad);
                    premio.putExtra("sexo", sexo);
                    premio.putExtra("tipoReto",1);
                    brazoizq.startAnimation(izq);
                    brazoder.startAnimation(der);
                    boca.startAnimation(bocaanim);
                    resultado.setImageResource(R.drawable.correcto);
                    resultado.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            startActivityForResult(premio, 1);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });


                }
                else{
                    error=MediaPlayer.create(this,R.raw.alert);
                    error.start();
                    parpadoizq.startAnimation(pizq);
                    parpadoder.startAnimation(pder);


                    //Toast.makeText(RetoCultural.this,"Respuesta Incorrecta",Toast.LENGTH_LONG).show();
                    resultado.setImageResource(R.drawable.fallo);
                    resultado.startAnimation(anim);

                }

                construyeRespuesta.clear();
                comparador="";
                resp1.setEnabled(true);
                resp1.setChecked(false);
                    resp2.setEnabled(true);
                    resp2.setChecked(false);
                    resp3.setEnabled(true);
                    resp3.setChecked(false);
                    resp4.setEnabled(true);
                    resp4.setChecked(false);
                    //construyeRespuesta="";
                break;
        }

    }

    public void aceptar(Items aBorrar) {
        final Items aB =aBorrar;
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RetoCultural.this);
        dialogo2.setTitle("Pista");
        dialogo2.setMessage("Pista util para responder a la pregunta");
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dt.remove(aB);
                aceptar2();
            }
        });

        dialogo2.show();
    }

    public void cancelar() {

    }
    public void aceptar2(){
        adapter.notifyDataSetChanged();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            String resultado = data.getExtras().getString("RESULTADO");
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case 1:
                    finish();
                    break;


            }
        }
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
