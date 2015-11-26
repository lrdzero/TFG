package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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
    private ArrayList<DatosRyR> retos = new ArrayList<>();
    private ArrayList<String> envios = new ArrayList<>();
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
    private int tutorial=1;

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

        nombreRuta=(EditText)findViewById(R.id.editName);
        lista =(ListView) findViewById(R.id.listaRetosParaRuta);
        imageNew =(ImageView) findViewById(R.id.imagenNuevoReto);
        historia=(EditText)findViewById(R.id.editHistoria);
        mapa=(ImageView)findViewById(R.id.maprutas);
        imageNew.setOnClickListener(this);
        check.setOnClickListener(this);
        mapa.setEnabled(false);
        mapa.setVisibility(View.INVISIBLE);
        //mapa.setOnClickListener(this);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        con = new Conexion();
        Log.e("vAMOS", Boolean.toString(modif));
        if(modif){
            myName=getIntent().getExtras().getString("name");
            nombreRuta.setText(myName);
        }
        else{
            tutorial=getIntent().getExtras().getInt("tutorial");
        }
        Creador();
        Visualizar();
    }

    public void onClick(View v){
        switch (v.getId()){

            case R.id.imagenNuevoReto:
                if(nombreRuta.getText().toString().matches("")){
                    Toast.makeText(CreadorRutas.this,"El nombre de la ruta no puede estar vacio",Toast.LENGTH_LONG).show();
                }
                else {
                    if(retos.isEmpty()) {
                        Log.e("ERRORRORORORO","VUELVO A ENTRAR");
                      con.nuevaRuta(nombreRuta.getText().toString(),nombreRecorrido,historia.getText().toString());
                    }

                    Intent nuevo = new Intent(CreadorRutas.this, CreadorRetoDeportivo.class);
                    nuevo.putExtra("tipo", tipo);
                    nuevo.putExtra("RecNombre",nombreRecorrido);
                    nuevo.putExtra("descrip", descRecorrido);
                    nuevo.putExtra("RutaName", nombreRuta.getText().toString());
                    nuevo.putExtra("modifi",false);
                    double numero = Math.random() * 5000;
                    int n2= (int) numero;
                    nuevo.putExtra("nombrefile", Integer.toString(n2));
                    startActivity(nuevo);
                }
                break;

            case R.id.imagenCheck:
                if(!nombreRuta.getText().toString().matches("")) {
                    ArrayList<String> relacion = new ArrayList<>();
                    if (!modif) {

                        relacion.add(nombreRuta.getText().toString());
                        relacion.add(Integer.toString(retos.size()));
                        if (retos.isEmpty()) {
                            con.nuevaRuta(nombreRuta.getText().toString(), nombreRecorrido, historia.getText().toString());
                        }
                        con.hacerconexionGenerica("actualizaReco", relacion);
                    } else {

                        relacion.add(nombreRecorrido);
                        relacion.add(Integer.toString(retos.size()));
                        con.hacerconexionGenerica("actualizarReco", relacion);
                        relacion.clear();
                        relacion.add(myName);
                        relacion.add(nombreRuta.getText().toString());
                        relacion.add(Integer.toString(retos.size()));
                        con.hacerconexionGenerica("actualizarRuta", relacion);
                    }
                    finish();
                }
                else{
                    Toast.makeText(CreadorRutas.this,"Campos sin rellenar",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.maprutas:
                if(nombreRuta.getText().toString().matches("")){
                    Toast.makeText(CreadorRutas.this,"El nombre de la ruta no puede estar vacio",Toast.LENGTH_LONG).show();
                }
                else{
                    con.nuevaRuta(nombreRuta.getText().toString(), historia.getText().toString(), "");
                    Intent nuevo = new Intent(CreadorRutas.this, MapaEditor.class);
                    nuevo.putExtra("nombre", nombreRuta.getText().toString());
                    nuevo.putExtra("tipo", true);
                    startActivity(nuevo);
                }
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
        if(tutorial==0){
            generarTutorial().show();
        }


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

            final TextView descp =(TextView) intenView.findViewById(R.id.descripcionDelReto);
            ImageView img =(ImageView) intenView.findViewById(R.id.imagenRetoPremio);
            ImageView edit =(ImageView) intenView.findViewById(R.id.retoEdit);
            ImageView erase =(ImageView) intenView.findViewById(R.id.retoErase);
            ImageView pickPos =(ImageView) intenView.findViewById(R.id.pickPosition);

            descp.setText(currentData.getName());
            img.setImageURI(currentData.getUri());

            pickPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent n = new Intent(CreadorRutas.this,MapaEditor.class);
                    n.putExtra("tipo",true);
                    n.putExtra("nombre",nombreRuta.getText().toString());
                    n.putExtra("retos",true);
                    n.putExtra("namereto",descp.getText().toString());
                    startActivity(n);

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent al = new Intent(CreadorRutas.this, CreadorRetoDeportivo.class);
                    al.putExtra("tipo", tipo);
                    al.putExtra("RecNombre", nombreRecorrido);
                    al.putExtra("descrip", descRecorrido);
                    al.putExtra("RutaName", nombreRuta.getText().toString());
                    al.putExtra("modifi", true);
                    al.putExtra("nombreReto", currentData.getName());
                    double numero = Math.random() * 5000;
                    int n2= (int) numero;
                    al.putExtra("nombrefile",Integer.toString(n2));

                    startActivity(al);


                }
            });
            erase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    envios.add(currentData.getName());
                    Log.e("TAAAGTAMA", Integer.toString(envios.size()));
                    int borrarReto = con.hacerconexionGenerica("borrarReto", envios);
                    envios.clear();
                    if (borrarReto == -1) {
                        retos.remove(currentData);
                        actualizar();
                    } else {
                        Toast.makeText(CreadorRutas.this, "Error al borrar el reto", Toast.LENGTH_LONG).show();
                    }

                }
            });




            return intenView;
        }


    }
    private AlertDialog.Builder generarTutorial(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreadorRutas.this);
        builder.setTitle("Tutorial")
                .setMessage("Bienvenido al tutorial:\n Para Crear una Ruta nueva Rellene los campos necesarios: 1) Una vez hecho esto pulse \"Listo\", le devolverá a la pantalla anterior y allí podrá asignar la ruta pulsando el icono \"mapa\".\n" +
                        "2) Si por el contrario desea crear los Retos para esta ruta pulse el botón \"+\" y continue.\n"
                        +"3) Para asignar un recorrido primero deberá haber creado la ruta en google Maps como se dice en el punto 1 y acceder al Reto mediante el editor, despues pulsar el icono \"mapa\"")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder;
    }

}
