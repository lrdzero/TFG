package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RetoDeportivo extends Activity implements View.OnClickListener{
    private HorizontalListView mochila;
    private ArrayList<Items> dt = new ArrayList<Items>();
    private PlaceList adapter;
    private Button go;
    private Conexion con;
    private DatosRyR datosReto;
    private String nameUser,nameRecorrido,nameRuta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_deportivo);
        TextView desafio =(TextView) findViewById(R.id.desafio);
        con=new Conexion();
        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido = getIntent().getExtras().getString("nombreRecorrido");
        nameRuta =getIntent().getExtras().getString("nombreRuta");
        datosReto = con.buscarDatosRetoDeportivo("unreco");
        if(datosReto==null){
            Toast.makeText(RetoDeportivo.this,"ERROR EN OBTENCION",Toast.LENGTH_LONG).show();
        }
        else{
            desafio.setText(datosReto.getDescription());
        }
        go=(Button) findViewById(R.id.buttonStart);


       // loadItems();
        //ListView();

        go.setOnClickListener(this);

    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.buttonStart:
                    Intent nuevo = new Intent(RetoDeportivo.this,CronometroDeporte.class);
                    nuevo.putExtra("nombreReto",datosReto.getName());
                    nuevo.putExtra("tiempo",datosReto.getNumber());
                    nuevo.putExtra("nombreUser",nameUser);
                    nuevo.putExtra("nombreRecorrido",nameRecorrido);
                    nuevo.putExtra("nombreRuta",nameRuta);
                    startActivity(nuevo);

                break;
        }

    }

    public void loadItems(){
        //dt.add(new Items("nombre 1",R.drawable.busto ));
        //dt.add(new Items("nombre 2",R.drawable.busto ));
        //dt.add(new Items("nombre 3",R.drawable.busto ));
        //dt.add(new Items("nombre 4",R.drawable.busto ));

    }
    public void ListView(){


        mochila = (HorizontalListView) findViewById(R.id.listaMochila);
        /*mochila.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Items currentData = (Items) mochila.getItemAtPosition(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(RetoDeportivo.this);
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
        */
        mochila.setAdapter(adapter);
    }
    public class PlaceList extends ArrayAdapter<Items> {
        Items currentData;
        public PlaceList(){
            super(RetoDeportivo.this,R.layout.activity_lista_horizontal_mochila,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            final ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            currentData = dt.get(position);

            img.setImageResource(R.drawable.busto);
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void aceptar(Items aBorrar) {
        final Items aB =aBorrar;
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RetoDeportivo.this);
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
}
