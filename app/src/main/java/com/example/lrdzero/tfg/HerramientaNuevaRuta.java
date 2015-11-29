package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HerramientaNuevaRuta extends Activity implements View.OnClickListener{
    private ListView recorridos;
    private ImageView listo,nuevo;
    private ArrayList<DatosRyR> dt= new ArrayList<DatosRyR>();
    private PlaceList adapter;
    private EditText nombre,descrip;

    private ArrayList<String> envios=new ArrayList<String>();
    private Conexion con;
    private String usuarioCreador="defecto";

    @Override
    public void onResume(){
        super.onResume();
        CargarLista();
        Visualizar();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramienta_nueva_ruta);

        listo=(ImageView)findViewById(R.id.imagenListo);
        recorridos=(ListView) findViewById(R.id.listView4);
        nuevo=(ImageView) findViewById(R.id.imageNuevoRecorrido);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());


        con = new Conexion();
        //usuarioCreador=getIntent().getExtras().getString("creador");
        usuarioCreador="l";
        nuevo.setOnClickListener(this);

        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CargarLista();
        Visualizar();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageNuevoRecorrido:

                Intent nuevo = new Intent(HerramientaNuevaRuta.this,CrearNuevoRecorrido.class);
                nuevo.putExtra("Modif",false);
                nuevo.putExtra("creador",usuarioCreador);
                nuevo.putExtra("tutorial",recorridos.getCount());
                startActivity(nuevo);
                break;
        }

    }

    public void CargarLista(){
            dt=con.cargaDeRecorridos(3,usuarioCreador);


    }
    public void Visualizar(){
        adapter = new PlaceList();
        recorridos.setAdapter(adapter);

    }
    public void actualizar(){
       adapter.notifyDataSetChanged();
    }
    public class PlaceList extends ArrayAdapter<DatosRyR>{

        public PlaceList(){
            super(HerramientaNuevaRuta.this,R.layout.activity_nuevos_recorridos,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_nuevos_recorridos,parent,false);
            }
            final DatosRyR currentData = dt.get(position);

            TextView nombre=(TextView)intenView.findViewById(R.id.nombreRecorrido);
            TextView descr=(TextView) intenView.findViewById(R.id.descripcion);
            ImageView imag =(ImageView)intenView.findViewById(R.id.imagenRecrorrido);
            //ImageView camera =(ImageView) intenView.findViewById(R.id.camara);
            ImageView modifi =(ImageView) intenView.findViewById(R.id.modificar);
            ImageView eliminar =(ImageView) intenView.findViewById(R.id.eliminar);

            nombre.setText(currentData.getName());
            descr.setText(currentData.getDescription());
            imag.setImageResource(currentData.getImage());
/*
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
*/
            modifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent nuevo = new Intent(HerramientaNuevaRuta.this,CrearNuevoRecorrido.class);
                    nuevo.putExtra("Modif",true);
                    nuevo.putExtra("creador",usuarioCreador);
                    nuevo.putExtra("nombre",currentData.getName());

                    startActivity(nuevo);

                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    envios.add(currentData.getName());
                    int borrarRecorrido =con.hacerconexionGenerica("borrarRecorrido",envios);
                    envios.clear();
                    if(borrarRecorrido==-1){
                        dt.remove(currentData);
                        actualizar();
                    }
                    else{
                        Toast.makeText(HerramientaNuevaRuta.this, "Error al borrar ruta", Toast.LENGTH_LONG).show();
                    }


                }
            });



            return intenView;
        }

    }
}
