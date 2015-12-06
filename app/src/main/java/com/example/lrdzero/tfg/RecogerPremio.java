package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class RecogerPremio extends Activity implements View.OnClickListener{
    private ArrayList<Items> dt = new ArrayList<Items>();
    private PlaceList adapter;
    private HorizontalListView lista2;
    private ImageView image,foto;
    private String nombreReto;
    private Conexion con;
    private ArrayList<String> datosMochila;
    private ArrayList<String> datosPremio;
    private ArrayList<String> envio = new ArrayList<String>();
    private String nameRuta,nameUser,nameRecorrido;
    private LinearLayout lt;
    private Uri fileUri;
    private String edad,sexo;
    private int tipoReto;
    private boolean insertado=false;

    private ImageView parpadoder;
    private ImageView parpadoiz;
    private ImageView brazoDer;
    private ImageView brazoIz;
    private ImageView cuerpo;
    private ImageView boca;
    private ImageView ojos;
    private ImageView bocaRoja;
    private ImageView dientes;
    protected void onDestroy(){
        super.onDestroy();
    }
    protected  void onPause(){
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoger_premio);
        final View v= new View(getApplicationContext());
        Button volver = (Button) findViewById(R.id.volverMapa);
        image = (ImageView) findViewById(R.id.imageView14);
        lt =(LinearLayout)findViewById(R.id.myLinear);
        //Carga Avatar
        parpadoder =(ImageView) findViewById(R.id.parpder);
        parpadoiz=(ImageView) findViewById(R.id.parpizq);
        brazoDer =(ImageView) findViewById(R.id.brazoder);
        brazoIz=(ImageView) findViewById(R.id.brazoizq);
        cuerpo =(ImageView) findViewById(R.id.cabeza);
        boca = (ImageView)findViewById(R.id.bocaverde);
        ojos = (ImageView)findViewById(R.id.ojos);
        bocaRoja =(ImageView)findViewById(R.id.bocaroja);
        dientes =(ImageView)findViewById(R.id.dientesP);
        tipoReto=getIntent().getExtras().getInt("tipoReto");

        MediaPlayer mp = MediaPlayer.create(this,R.raw.tada);
        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nameRuta = getIntent().getExtras().getString("nombreRuta");
        edad=getIntent().getExtras().getString("edad");
        sexo=getIntent().getExtras().getString("sexo");

        nombreReto=getIntent().getExtras().getString("nombreReto");

        con = new Conexion();
        datosMochila = con.cargarMochila(nameUser,nameRecorrido,nameRuta,nombreReto);




        //Toast.makeText()
        adaptacion(sexo,edad);
        datosPremio = con.cargarPremio(nombreReto);

        fileUri = Uri.parse(datosPremio.get(2));

        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            yourDrawable = Drawable.createFromStream(inputStream, fileUri.toString() );
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.pesas);
        }

        lt.setBackgroundDrawable(yourDrawable);

        yourDrawable.invalidateSelf();
        if(datosPremio.get(4).equals("1")) {
            image.setImageResource(Integer.valueOf(datosPremio.get(1)));
        }
        else{
            Uri nuevo = Uri.parse(datosPremio.get(1));
            image.setImageURI(nuevo);
        }
        image.setOnClickListener(this);
        volver.setOnClickListener(this);

        loadItems();

        ListaView();
        mp.start();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView14:
                if(datosPremio.get(4).equals("1")) {
                    dt.add(new Items(datosPremio.get(0), Integer.valueOf(datosPremio.get(1)), nombreReto, datosPremio.get(3)));
                }
                else if(datosPremio.get(4).equals("2")){
                    Items n = new Items();
                    n.setName(datosPremio.get(0));
                    n.setSeconFoto(datosPremio.get(1));
                    n.setNombreReto(nombreReto);
                    n.setDescripcionRecompensa(datosPremio.get(3));
                    dt.add(n);
                }
                adapter.notifyDataSetChanged();
                envio.add(nameUser);
                envio.add(nameRuta);
                envio.add(nombreReto);
                envio.add(nameRecorrido);
                envio.add(datosPremio.get(0));
                envio.add(datosPremio.get(1));
                envio.add(datosPremio.get(3));
                if(datosPremio.get(4).equals("1")){
                    envio.add("1");
                }
                else if(datosPremio.get(4).equals("2")){
                    envio.add("2");
                }
                int cont =con.hacerconexionGenerica("insertMochila", envio);
                if(cont==0){
                    Toast.makeText(RecogerPremio.this,"Error al insertar a mochila",Toast.LENGTH_LONG).show();
                }
                else{
                    insertado=true;
                }
                envio.clear();
                image.setVisibility(View.INVISIBLE);
                image.setEnabled(false);


                break;

            case R.id.volverMapa:
                    if(insertado){
                        Intent i = getIntent();
                        // Le metemos el resultado que queremos mandar a la
                        // actividad principal.
                        i.putExtra("RESULTADO", 1);
                        // Establecemos el resultado, y volvemos a la actividad
                        // principal. La variable que introducimos en primer lugar
                        // "RESULT_OK" es de la propia actividad, no tenemos que
                        // declararla nosotros.
                        setResult(RESULT_OK, i);
                        finish();
                    }
                else{
                        Toast.makeText(RecogerPremio.this,"Debes recoger el premio.",Toast.LENGTH_LONG).show();
                    }
                break;
        }
    }
    public class PlaceList extends ArrayAdapter<Items> {
        Items currentData;
        public PlaceList(){
            super(RecogerPremio.this, R.layout.activity_lista_horizontal_mochila, dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            currentData = dt.get(position);
            if(currentData.getImage()!=0) {
                img.setImageResource(currentData.getImage());
            }
            else{
                img.setImageURI(Uri.parse(currentData.getSeconFoto()));
            }
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void loadItems(){
        //Toast.makeText(RecogerPremio.this,"Entro en la carga",Toast.LENGTH_LONG).show();

        if(!datosMochila.isEmpty()){
            for(int i=0;i<datosMochila.size();i=i+4){
                if(datosMochila.get(i+3).equals("1")) {
                    dt.add(new Items(datosMochila.get(i), Integer.valueOf(datosMochila.get(i+1)), nombreReto, datosMochila.get(i+2)));
                }
                else if(datosMochila.get(i+3).equals("2")){
                    Items n = new Items();
                    n.setName(datosMochila.get(i));
                    n.setSeconFoto(datosMochila.get(i+1));
                    n.setNombreReto(nombreReto);
                    n.setDescripcionRecompensa(datosMochila.get(i+2));
                    dt.add(n);
                }
            }

        }

        //dt.add(new Items("nombre 1",R.drawable.busto,"vacio" ));
        //dt.add(new Items("nombre 2",R.drawable.busto ,"vacio"));
        //dt.add(new Items("nombre 3",R.drawable.busto,"vacio" ));
        //dt.add(new Items("nombre 4",R.drawable.busto,"vacio" ));


    }
    private void ListaView(){

        adapter= new PlaceList();

        lista2 = (HorizontalListView) findViewById(R.id.listaMochila);

        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Items currentData = (Items) lista2.getItemAtPosition(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(RecogerPremio.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Ver este Item?");

                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

    }
    public void aceptar(Items aBorrar) {
        final Items aB =aBorrar;
       // Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        //t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RecogerPremio.this);
        dialogo2.setTitle("Descripción");
        dialogo2.setMessage(aB.getDescripcionRecompensa());
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                //aceptar2();
            }
        });

        dialogo2.show();
    }

    public void cancelar() {

    }
    public void aceptar2(){
        adapter.notifyDataSetChanged();

    }
    private void adaptacion(String sexo,String edad){
        if(tipoReto==0) {

            if (sexo.equals("H")) {
                if (Integer.valueOf((edad)) < 18) {
                    boca.setImageResource(R.drawable.boca_n);
                    ojos.setImageResource(R.drawable.ojos);
                    parpadoder.setImageResource(R.drawable.parpadoder_n);
                    parpadoiz.setImageResource(R.drawable.parpadoizq_n);
                    brazoIz.setImageResource(R.drawable.manoizq);
                    brazoDer.setImageResource(R.drawable.manoder);
                    cuerpo.setImageResource(R.drawable.cuerpo_n);
                } else if (Integer.valueOf(edad) >= 18 && Integer.valueOf(edad) < 57) {
                    boca.setImageResource(R.drawable.boca);
                    ojos.setImageResource(R.drawable.ojos);
                    parpadoder.setImageResource(R.drawable.parpadoder);
                    parpadoiz.setImageResource(R.drawable.parpadoizq);
                    brazoIz.setImageResource(R.drawable.manoizq);
                    brazoDer.setImageResource(R.drawable.manoder);
                    cuerpo.setImageResource(R.drawable.cuerpo);
                } else {
                    boca.setImageResource(R.drawable.boca_a);
                    ojos.setImageResource(R.drawable.ojos);
                    parpadoder.setImageResource(R.drawable.parpadoder_a);
                    parpadoiz.setImageResource(R.drawable.parpadoizq_a);
                    brazoIz.setImageResource(R.drawable.manoizq_a);
                    brazoDer.setImageResource(R.drawable.manoder_a);
                    cuerpo.setImageResource(R.drawable.cuerpo_a);
                }
            } else {
                if (Integer.valueOf((edad)) < 18) {
                    boca.setImageResource(R.drawable.boca_h_n);
                    ojos.setImageResource(R.drawable.ojos);
                    parpadoder.setImageResource(R.drawable.parpadoder_h_n);
                    parpadoiz.setImageResource(R.drawable.parpadoizq_h_n);
                    brazoIz.setImageResource(R.drawable.manoizq_h_n);
                    brazoDer.setImageResource(R.drawable.manoder_h_n);
                    cuerpo.setImageResource(R.drawable.cuerpo_h_n);
                } else if (Integer.valueOf(edad) >= 18 && Integer.valueOf(edad) < 57) {
                    boca.setImageResource(R.drawable.boca_h);
                    ojos.setImageResource(R.drawable.ojos);
                    //insertaMujer();
                    parpadoder.setImageResource(R.drawable.parpadoder_h);
                    parpadoiz.setImageResource(R.drawable.parpadoizq_h);
                    brazoIz.setImageResource(R.drawable.manoizq_h);
                    brazoDer.setImageResource(R.drawable.manoder_h);
                    cuerpo.setImageResource(R.drawable.cuerpo_h);
                } else {
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
        else{
            boca.setImageResource(R.drawable.labio_inferior);
            cuerpo.setImageResource(R.drawable.cuerpo_leon);
            bocaRoja.setImageResource(R.drawable.labio_superior);
            ojos.setImageResource(R.drawable.ojos_leon);
            dientes.setImageResource(R.drawable.dientes);
        }
    }
}
