package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CreadorRutas extends Activity implements View.OnClickListener {
    private ArrayList<DatosRyR> retos = new ArrayList<DatosRyR>();
    private ArrayList<String> envios = new ArrayList<String>();
    private PlaceList adapter;
    private ListView lista;
    private ImageView imageNew;
    private Boolean tipo;
    private EditText nombreRuta,historia;
    private String nombreRecorrido,descRecorrido;
    private ImageView check,mapa;
    private Conexion con;
    private boolean modif;
    private String myName;

    @Override
    public void onResume(){
        super.onResume();
        Creador();
        Visualizar();

    }
    @Override
    public void onPause(){
        super.onPause();

    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_rutas);
        tipo=getIntent().getExtras().getBoolean("tipo");
        nombreRecorrido=getIntent().getExtras().getString("RecNombre");
        descRecorrido=getIntent().getExtras().getString("drescrip");
        modif=getIntent().getExtras().getBoolean("modif");

        check=(ImageView) findViewById(R.id.imagenCheck);
        mapa=(ImageView)findViewById(R.id.imageView17);
        nombreRuta=(EditText)findViewById(R.id.editName);
        lista =(ListView) findViewById(R.id.listaRetosParaRuta);
        imageNew =(ImageView) findViewById(R.id.imagenNuevoReto);
        historia=(EditText)findViewById(R.id.editHistoria);
        imageNew.setOnClickListener(this);
        check.setOnClickListener(this);
        mapa.setOnClickListener(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        con = new Conexion();
        Log.e("vAMOS", Boolean.toString(modif));
        if(modif){
            myName=getIntent().getExtras().getString("name");
            nombreRuta.setText(myName);
        }
        Creador();
        Visualizar();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView17:
                Intent n = new Intent(CreadorRutas.this,Mapa.class);
                startActivity(n);
                break;
            case R.id.imagenNuevoReto:
                if(nombreRuta.getText().toString().matches("")){
                    Toast.makeText(CreadorRutas.this,"El nombre de la ruta no puede estar vacio",Toast.LENGTH_LONG).show();
                }
                else {
                    if(retos.isEmpty()) {
                      con.nuevaRuta(nombreRuta.getText().toString(),nombreRecorrido,historia.getText().toString());
                    }

                    Intent nuevo = new Intent(CreadorRutas.this, CreadorRetoDeportivo.class);
                    nuevo.putExtra("tipo", tipo);
                    nuevo.putExtra("RecNombre",nombreRecorrido);
                    nuevo.putExtra("descrip", descRecorrido);
                    nuevo.putExtra("RutaName", nombreRuta.getText().toString());
                    nuevo.putExtra("modifi",false);
                    startActivity(nuevo);
                }
                break;

            case R.id.imagenCheck:
                ArrayList<String> relacion = new ArrayList<String>();
                if(!modif) {

                    relacion.add(nombreRuta.getText().toString());
                    relacion.add(Integer.toString(retos.size()));
                    //con.hacerconexionGenerica("actualizaReco", relacion);
                }
                else{
                    relacion.add(nombreRecorrido);
                    relacion.add(Integer.toString(retos.size()));
                    //con.hacerconexionGenerica("actualizarReco", relacion);
                    relacion.clear();
                    relacion.add(myName);
                    relacion.add(nombreRuta.getText().toString());
                    relacion.add(Integer.toString(retos.size()));
                    //con.hacerconexionGenerica("actualizarRuta",relacion);
                }
                    finish();
                break;
        }
    }

    public void Creador(){
       retos=con.cargaDeRetos(nombreRuta.getText().toString());
        //retos.add(new DatosRyR("Ruta 1", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));
        //retos.add(new DatosRyR("Ruta 2", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));
        //retos.add(new DatosRyR("Ruta 3", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));

    }
    public void Visualizar(){
        adapter=new PlaceList();
        lista.setAdapter(adapter);


    }

    public void actualizar(){
        adapter.notifyDataSetChanged();
    }
    public class PlaceList extends ArrayAdapter<DatosRyR> {

        public PlaceList(){
            super(CreadorRutas.this,R.layout.creadorlistaretos,retos);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.creadorlistaretos,parent,false);
            }
            final DatosRyR currentData = retos.get(position);

            TextView descp =(TextView) intenView.findViewById(R.id.descripcionDelReto);
            ImageView img =(ImageView) intenView.findViewById(R.id.imagenRetoPremio);
            ImageView edit =(ImageView) intenView.findViewById(R.id.retoEdit);
            ImageView erase =(ImageView) intenView.findViewById(R.id.retoErase);

            descp.setText(currentData.getName());
            img.setImageResource(currentData.getImage());


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent al = new Intent(CreadorRutas.this,CreadorRetoDeportivo.class);
                    al.putExtra("tipo", tipo);
                    al.putExtra("RecNombre",nombreRecorrido);
                    al.putExtra("descrip", descRecorrido);
                    al.putExtra("RutaName", nombreRuta.getText().toString());
                    al.putExtra("modifi",true);
                    al.putExtra("nombreReto",currentData.getName());
                    startActivity(al);


                }
            });
            erase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    envios.add(currentData.getName());
                    Log.e("TAAAGTAMA",Integer.toString(envios.size()));
                    int borrarReto = con.hacerconexionGenerica("borrarReto",envios);
                    envios.clear();
                    if(borrarReto==-1){
                        retos.remove(currentData);
                        actualizar();
                    }
                    else{
                        Toast.makeText(CreadorRutas.this,"Error al borrar el reto",Toast.LENGTH_LONG).show();
                    }

                }
            });




            return intenView;
        }


    }

}
