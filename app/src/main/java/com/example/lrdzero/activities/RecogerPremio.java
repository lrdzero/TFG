package com.example.lrdzero.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrdzero.data.Conexion;
import com.example.lrdzero.data.Recompensa;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class RecogerPremio extends Activity implements View.OnClickListener{
    private ArrayList<Recompensa> dt = new ArrayList<Recompensa>();
    private PlaceList adapter;
    private ListView lista2;
    private ImageView image,foto;
    private String nombreReto;
    private Conexion con;
    private ArrayList<String> datosMochila;
    private ArrayList<String> datosPremio;
    private ArrayList<String> envio = new ArrayList<String>();
    private String nameRuta,nameUser,nameRecorrido;
    private RelativeLayout lt;
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
    private ImageView miMochila;
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
        lt =(RelativeLayout)findViewById(R.id.myLinear);
        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        miMochila =(ImageView) findViewById(R.id.mochilaDePremios);

        tipoReto=getIntent().getExtras().getInt("tipoReto");

        MediaPlayer mp = MediaPlayer.create(this,R.raw.tada);
        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nameRuta = getIntent().getExtras().getString("nombreRuta");
        edad=getIntent().getExtras().getString("edad");
        sexo=getIntent().getExtras().getString("sexo");
        nombreReto=getIntent().getExtras().getString("nombreReto");

        con = new Conexion();
        datosMochila = con.cargarMochila(nameUser, nameRecorrido, nameRuta);

        datosPremio = con.cargarPremio(nombreReto);

        fileUri = Uri.parse(datosPremio.get(2));
        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            yourDrawable = Drawable.createFromStream(inputStream, fileUri.toString());
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.plaza);
        }

        lt.setBackgroundDrawable(yourDrawable);



        if(datosPremio.get(4).equals("1")) {
            image.setImageResource(Integer.valueOf(datosPremio.get(1)));
            float dato = Float.valueOf(datosPremio.get(5));
            float dato2= Float.valueOf(datosPremio.get(6));
            int x=(int)dato;
            int y=(int)dato2;
            image.setX(dato);
            image.setY(dato2);

        }
        else{
            Uri nuevo = Uri.parse(datosPremio.get(1));
            image.setImageDrawable(reduceImagen(nuevo, 60, 60));
            float dato = Float.valueOf(datosPremio.get(5));
            float dato2= Float.valueOf(datosPremio.get(6));
            int x=(int)dato;
            int y=(int)dato2;
            image.setX(dato);
            image.setY(dato2);

            //image.setX(Float.valueOf(datosPremio.get(5)));
            //image.setY(Float.valueOf(datosPremio.get(6)));
        }
        /*
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(60, 60);
        layoutParams.setMargins(20, 20,0,0);

        lt.addView(image, layoutParams);
        */
        image.setOnClickListener(this);
        miMochila.setOnClickListener(this);
        volver.setOnClickListener(this);

        loadItems();
        adapter = new PlaceList();

        //ListaView();
        mp.start();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView14:
                if(datosPremio.get(4).equals("1")) {
                    dt.add(new Recompensa(datosPremio.get(0), Integer.valueOf(datosPremio.get(1)), nombreReto, datosPremio.get(3)));
                }
                else if(datosPremio.get(4).equals("2")){
                    Recompensa n = new Recompensa();
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
            case R.id.mochilaDePremios:

                    mostrarMochila().show();

                break;
        }
    }
    public class PlaceList extends ArrayAdapter<Recompensa> {
        Recompensa currentData;
        Uri unUri;
        String miDato;
        //BitmapDrawable dr;
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
                //Toast.makeText(RecogerPremio.this,"solo esto",Toast.LENGTH_LONG).show();
                img.setImageResource(currentData.getImage());
            }
            else{
                unUri = Uri.parse(currentData.getSeconFoto());

                img.setImageDrawable(reduceImagen(unUri,80,80));



            }
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void loadItems(){


        if(!datosMochila.isEmpty()){
            for(int i=0;i<datosMochila.size();i=i+4){
                if(datosMochila.get(i+3).equals("1")) {
                    dt.add(new Recompensa(datosMochila.get(i), Integer.valueOf(datosMochila.get(i+1)), nombreReto, datosMochila.get(i+2)));
                }
                else if(datosMochila.get(i+3).equals("2")){
                    Recompensa n = new Recompensa();
                    n.setName(datosMochila.get(i));
                    n.setSeconFoto(datosMochila.get(i+1));
                    n.setNombreReto(nombreReto);
                    n.setDescripcionRecompensa(datosMochila.get(i+2));
                    dt.add(n);
                }
            }

        }




    }
    private void ListaView(){


    }
    public void aceptar(Recompensa aBorrar) {
        final Recompensa aB =aBorrar;
       // Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        //t.show();
        final AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RecogerPremio.this);
        dialogo2.setTitle("Descripción");
        dialogo2.setMessage(aB.getDescripcionRecompensa());
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                            //dialogo2.setCancelable(false);
            }
        });

        dialogo2.show();
    }

    public void cancelar() {

    }
    public void aceptar2(){
        //adapter.notifyDataSetChanged();

    }
    private AlertDialog.Builder mostrarMochila(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RecogerPremio.this);

        builder.setTitle("Mochila").setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                                    aceptar(adapter.currentData);
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(false);
            }
        });

       return builder;
    }

    private Drawable reduceImagen(Uri unUri,int tam1,int tam2){
        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(unUri);
            yourDrawable = Drawable.createFromStream(inputStream, unUri.toString());
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.plaza);
        }
        Bitmap bitmap = ((BitmapDrawable) yourDrawable).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, tam1, tam2, true));
        yourDrawable.invalidateSelf();
        return d;
    }
}
