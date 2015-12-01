package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoger_premio);
        final View v= new View(getApplicationContext());
        //foto = (ImageView) findViewById(R.id.laFoto);
        image = (ImageView) findViewById(R.id.imageView14);
        lt =(LinearLayout)findViewById(R.id.myLinear);
        //fileUri = Uri.parse();
        //image.setImageURI(fileUri);
        MediaPlayer mp = MediaPlayer.create(this,R.raw.tada);


        nombreReto=getIntent().getExtras().getString("nombreReto");

        con = new Conexion();
        datosMochila = con.cargarMochila(nombreReto);



        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nameRuta=getIntent().getExtras().getString("nombreRuta");
        datosPremio = con.cargarPremio(nombreReto);
        //lt.setBackgroundResource(Integer.valueOf(datosMochila.get(1)));
        fileUri = Uri.parse(datosPremio.get(2));
        //image.setImageResource(fileUri.describeContents());
        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            yourDrawable = Drawable.createFromStream(inputStream, fileUri.toString() );
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.imagendefecto);
        }
        lt.setBackgroundDrawable(yourDrawable);
        //image.setImageURI(fileUri);

        image.setImageResource(Integer.valueOf(datosPremio.get(1)));
        image.setOnClickListener(this);


        loadItems();

        ListaView();
        mp.start();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView14:

                dt.add(new Items(datosPremio.get(0), Integer.valueOf(datosPremio.get(1)), nombreReto));
                adapter.notifyDataSetChanged();
                envio.add(nameUser);
                envio.add(nameRuta);
                envio.add(nombreReto);
                envio.add(nameRecorrido);
                envio.add(datosPremio.get(0));
                envio.add(datosPremio.get(1));
                con.hacerconexionGenerica("insertMochila", envio);
                envio.clear();


                break;
        }
    }
    public class PlaceList extends ArrayAdapter<Items> {
        Items currentData;
        public PlaceList(){
            super(RecogerPremio.this,R.layout.activity_lista_horizontal_mochila,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void loadItems(){
        if(!datosMochila.isEmpty()){
            for(int i=0;i<datosMochila.size();i=i+2){
                dt.add(new Items(datosMochila.get(i),Integer.valueOf(datosMochila.get(i+1)),nombreReto));
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
                dialogo1.setMessage("¿ Quieres utilizar este Item ?");

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
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RecogerPremio.this);
        dialogo2.setTitle("Descripción");
        dialogo2.setMessage(aB.getName());
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
}
