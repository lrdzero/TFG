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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CrearNuevoRecorrido extends Activity implements View.OnClickListener {
    private Button recomen;
    private ArrayList<Integer> recomendaciones = new ArrayList<>();
    private ArrayList<DatosRyR> retos= new ArrayList<>();
    private PlaceList adapter;
    private ListView listaRetos;
    private ImageView nuevoReto;
    private RadioButton btn1,btn2;
    private EditText nombreRecorrido;
    private EditText brevDescripcionRecorrido;
    private ArrayList<String> envios = new ArrayList<String>();
    private Conexion con;
    private ImageView imgna;
    private boolean modif;
    private int tutorial=1;
    private String nombreCreador="defecto";
    private String nombreRc;
    private ArrayList<String> recomendaModifi;

    @Override
    public void onResume(){
        super.onResume();
        Create();
        Visualizar();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nuevo_recorrido);

        recomen = (Button) findViewById(R.id.botonRecomendado);
        listaRetos=(ListView) findViewById(R.id.listView5);
        nuevoReto=(ImageView) findViewById(R.id.nuevoReto);
        btn1 =(RadioButton) findViewById(R.id.rdbDeporte);
        btn2 =(RadioButton) findViewById(R.id.rdbCultural);

        imgna = (ImageView) findViewById(R.id.imageView15);
        nombreRecorrido=(EditText) findViewById(R.id.recorridoNombre);
        brevDescripcionRecorrido=(EditText)findViewById(R.id.textoDescripcion);

        recomen.setOnClickListener(this);
        nuevoReto.setOnClickListener(this);
        imgna.setOnClickListener(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        con=new Conexion();
        modif=getIntent().getExtras().getBoolean("Modif");


        if(modif){
            nombreRc=getIntent().getExtras().getString("nombre");
            DatosRyR mod = new DatosRyR();
            mod=con.buscarDatosRuta(nombreRc);
            nombreRecorrido.setText(mod.getName());
            brevDescripcionRecorrido.setText(mod.getDescription());
            if(mod.getNumber().equals("1")){
                btn1.setChecked(true);
            }
            else{
                btn2.setChecked(true);
            }
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            recomendaModifi= con.cargaRecomendaciones(nombreRc);
            recomendaciones.add(Integer.valueOf(recomendaModifi.get(0)));
            recomendaciones.add(Integer.valueOf(recomendaModifi.get(1)));
            recomendaciones.add(Integer.valueOf(recomendaModifi.get(2)));
            recomendaciones.add(Integer.valueOf(recomendaModifi.get(3)));
        }
        else{
            tutorial=getIntent().getExtras().getInt("tutorial");
            nombreCreador=getIntent().getExtras().getString("creador");

        }

        Create();
            Visualizar();






    }
    private AlertDialog.Builder generarTutorial(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(CrearNuevoRecorrido.this);
        builder.setTitle("Tutorial")
                .setMessage("Bienvenido al tutorial:\n Para crear un nuevo reto debes rellenar todos los datos y pulsar el boton \"+\" o pulsar listo si quireres hacer un Recorrido de prueba")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder;
    }
   private AlertDialog.Builder generarBuilder(){
       final AlertDialog.Builder builder = new AlertDialog.Builder(CrearNuevoRecorrido.this);
       final ArrayList<Integer> aux = new ArrayList<>();
       builder.setTitle("Recomendado para")
               .setMultiChoiceItems(R.array.recomendado, null,
                       new DialogInterface.OnMultiChoiceClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                               if (isChecked) {

                                   aux.add(which);
                               } else if (aux.contains(which)) {

                                   aux.remove(Integer.valueOf(which));
                               }
                           }
                       })
                       // Set the action buttons*/
               .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       recomendaciones = aux;
                       // User clicked OK, so save the mSelectedItems results somewhere
                       // or return them to the component that opened the dialog

                   }
               })
               .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {

                   }
               });
       Log.i("cred", "prog");

       return builder;
   }
    private String converToString(ArrayList<Integer> f){
        String sul = "";
        for(int i=0;i<f.size();i++){
            sul+=Integer.toString(f.get(i))+",";
        }

        return sul;
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.botonRecomendado:
                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(CrearNuevoRecorrido.this);
                // Set the dialog title
                //Log.i("title", "prog");
                if(modif){

                    String recomendacion1,recomendacion2,recomendacion3,recomendacion4;
                    recomendacion1="";
                    recomendacion2="";
                    recomendacion3="";
                    recomendacion4="";
                    if(recomendaModifi.get(0).equals("1")){recomendacion1="Niño";}
                    if(recomendaModifi.get(1).equals("1")){recomendacion2="Adulto";}
                    if(recomendaModifi.get(2).equals("1")){recomendacion3="Problemas Cardiovasculares";}
                    if(recomendaModifi.get(3).equals("1")){recomendacion4="Problemas de movilidad";}
                    builder.setTitle("Advertencia")
                            .setMessage("Sus datos anteriores  son:\n\t" + recomendacion1 + "\n\t" + recomendacion2+"\n\t"+recomendacion3+"\n\t"+recomendacion4+"\n¿Desea modificarlos?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    generarBuilder().show();
                                    // User clicked OK, so save the mSelectedItems results somewhere
                                    // or return them to the component that opened the dialog

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    builder.show();
                }
                else{
                    generarBuilder().show();
                }
                break;
            case R.id.nuevoReto:

                if(nombreRecorrido.getText().toString().matches("")||brevDescripcionRecorrido.getText().toString().matches("")||recomendaciones.isEmpty()){
                    Toast.makeText(CrearNuevoRecorrido.this,"Debe rellenar los campos Nombre y Descripcion.",Toast.LENGTH_LONG).show();
                }
                else{
                    llevarACaboInsercion();
                }
                break;
            case R.id.imageView15:
                if(modif){
                    if(!recomendaciones.isEmpty()) {
                        llevaACaboModificacion();
                    }
                    else{
                        Toast.makeText(CrearNuevoRecorrido.this,"Campos sin rellenar: Recomendaciones",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    if(nombreRecorrido.getText().toString().matches("")||brevDescripcionRecorrido.getText().toString().matches("")||recomendaciones.isEmpty()){
                        Toast.makeText(CrearNuevoRecorrido.this,"Debe rellenar los campos Nombre, Descripcion y Recomendado para.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        llevaACaboListo();
                    }
                }

                break;
        }
    }
    public void Create(){
        retos=con.cargaDeRutas(nombreRecorrido.getText().toString());

        //retos.add(new DatosRyR("Ruta 1", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));
        //retos.add(new DatosRyR("Ruta 2", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));
        //retos.add(new DatosRyR("Ruta 3", "2", "Breve descripcion de ruta", "alguien", R.drawable.recorridodefecto, "mas descripcion"));

    }
    public void llevaACaboListo(){
        ArrayList<Integer> datosTrueEnBD =new ArrayList<>();
        for(int i=0;i<4;i++){
            datosTrueEnBD.add(0);
        }
        for(int i=0;i<recomendaciones.size();i++){
            datosTrueEnBD.set(recomendaciones.get(i),1);
        }
        if(datosTrueEnBD.get(0)==0&&datosTrueEnBD.get(1)==0){
            Toast.makeText(CrearNuevoRecorrido.this,"Debe especificar si es para niños o adultos.",Toast.LENGTH_LONG).show();
        }
        else if(datosTrueEnBD.get(0)==1&&datosTrueEnBD.get(1)==1){
            Toast.makeText(CrearNuevoRecorrido.this,"Solo puede ser para niños o para adultos.",Toast.LENGTH_LONG).show();
        }
        else {
            if (btn1.isChecked()) {

                if (retos.isEmpty()) {
                    envios.add(nombreRecorrido.getText().toString());
                    envios.add(brevDescripcionRecorrido.getText().toString());
                    envios.add("1");
                    envios.add(converToString(recomendaciones));
                    envios.add(nombreCreador);
                    con.hacerconexionGenerica("nuevoRecorrido", envios);
                    envios.clear();
                    con.updateRecorridoPreferencias("updateRecorridoPreferencias", datosTrueEnBD, nombreRecorrido.getText().toString());
                }

            } else if (btn2.isChecked()) {

                if (retos.isEmpty()) {
                    envios.add(nombreRecorrido.getText().toString());
                    envios.add(brevDescripcionRecorrido.getText().toString());
                    envios.add("0");
                    envios.add(converToString(recomendaciones));
                    envios.add(nombreCreador);
                    con.hacerconexionGenerica("nuevoRecorrido", envios);
                    envios.clear();
                    con.updateRecorridoPreferencias("updateRecorridoPreferencias", datosTrueEnBD, nombreRecorrido.getText().toString());
                }

            } else {
                Toast.makeText(CrearNuevoRecorrido.this, "Debe elegir un tipo de Recorrido", Toast.LENGTH_LONG).show();
            }


            finish();
        }
    }
    public void llevaACaboModificacion(){
        ArrayList<Integer> datosTrueEnBD =new ArrayList<>();
        for(int i=0;i<4;i++){
            datosTrueEnBD.add(0);
        }
        for(int i=0;i<recomendaciones.size();i++){
            datosTrueEnBD.set(recomendaciones.get(i),1);
        }
        if(datosTrueEnBD.get(0)==0&&datosTrueEnBD.get(1)==0){
            Toast.makeText(CrearNuevoRecorrido.this,"Debe especificar si es para niños o adultos.",Toast.LENGTH_LONG).show();
        }
        else if(datosTrueEnBD.get(0)==1&&datosTrueEnBD.get(1)==1){
            Toast.makeText(CrearNuevoRecorrido.this,"Solo puede ser para niños o para adultos.",Toast.LENGTH_LONG).show();
        }
        else {
            envios.clear();
            envios.add(nombreRc);
            envios.add(nombreRecorrido.getText().toString());
            envios.add(brevDescripcionRecorrido.getText().toString());
            if (!recomendaciones.isEmpty()) {
                envios.add(converToString(recomendaciones));
            }
            con.hacerconexionGenerica("updateRecorrido", envios);
            envios.clear();
            con.updateRecorridoPreferencias("updateRecorridoPreferencias", datosTrueEnBD, nombreRecorrido.getText().toString());
            finish();
        }
    }
    public void llevarACaboInsercion(){
        ArrayList<Integer> datosTrueEnBD = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datosTrueEnBD.add(0);
        }
        for (int i = 0; i < recomendaciones.size(); i++) {
            datosTrueEnBD.set(recomendaciones.get(i), 1);
        }
        if(datosTrueEnBD.get(0)==0&&datosTrueEnBD.get(1)==0){
            Toast.makeText(CrearNuevoRecorrido.this,"Debe especificar si es para niños o adultos.",Toast.LENGTH_LONG).show();
        }
        else if(datosTrueEnBD.get(0)==1&&datosTrueEnBD.get(1)==1){
            Toast.makeText(CrearNuevoRecorrido.this,"Solo puede ser para niños o para adultos.",Toast.LENGTH_LONG).show();
        }
        else {

            Intent nuevo = new Intent(CrearNuevoRecorrido.this, CreadorRutas.class);
            nuevo.putExtra("RecNombre", nombreRecorrido.getText().toString());
            nuevo.putExtra("drescrip", brevDescripcionRecorrido.getText().toString());
            nuevo.putExtra("modif", false);
            nuevo.putExtra("tutorial", listaRetos.getCount());
            if (btn1.isChecked()) {
                nuevo.putExtra("tipo", true);
                if (retos.isEmpty()) {
                    envios.add(nombreRecorrido.getText().toString());
                    envios.add(brevDescripcionRecorrido.getText().toString());
                    envios.add("1");
                    envios.add(converToString(recomendaciones));
                    envios.add(nombreCreador);
                    con.hacerconexionGenerica("nuevoRecorrido", envios);
                    envios.clear();
                    con.updateRecorridoPreferencias("updateRecorridoPreferencias", datosTrueEnBD, nombreRecorrido.getText().toString());
                }
                startActivity(nuevo);

            } else if (btn2.isChecked()) {
                nuevo.putExtra("tipo", false);
                if (retos.isEmpty()) {
                    envios.add(nombreRecorrido.getText().toString());
                    envios.add(brevDescripcionRecorrido.getText().toString());
                    envios.add("0");
                    envios.add(converToString(recomendaciones));
                    envios.add(nombreCreador);
                    con.hacerconexionGenerica("nuevoRecorrido", envios);
                    envios.clear();
                    con.updateRecorridoPreferencias("updateRecorridoPreferencias", datosTrueEnBD, nombreRecorrido.getText().toString());
                }
                startActivity(nuevo);
            } else {
                Toast.makeText(CrearNuevoRecorrido.this, "Debe elegir un tipo de Recorrido", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void Visualizar(){

        adapter=new PlaceList();
        listaRetos.setAdapter(adapter);
        if(tutorial==0){
            generarTutorial().show();
        }

    }

    public void actualizar(){
        adapter.notifyDataSetChanged();
    }
    public class PlaceList extends ArrayAdapter<DatosRyR> {

        public PlaceList(){
            super(CrearNuevoRecorrido.this,R.layout.listaretosencreadorrecorridos,retos);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.listaretosencreadorrecorridos,parent,false);
            }
            final DatosRyR currentData = retos.get(position);

            final TextView nombre=(TextView)intenView.findViewById(R.id.RutaNombre);
            TextView descr=(TextView) intenView.findViewById(R.id.retoDescription);
            //TextView time=(TextView) intenView.findViewById(R.id.Time);
            ImageView imag =(ImageView)intenView.findViewById(R.id.imagenNuevoRuta);

            ImageView modifi =(ImageView) intenView.findViewById(R.id.btnEditar);
            ImageView eliminar =(ImageView) intenView.findViewById(R.id.imagenEliminar);
            ImageView enrutar =(ImageView) intenView.findViewById(R.id.enroutar);

            nombre.setText(currentData.getName());
            descr.setText(currentData.getLargeDescription());
            //time.setText(currentData.getNumber());
            imag.setImageResource(currentData.getImage());

            enrutar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent n = new Intent(CrearNuevoRecorrido.this,MapaEditor.class);
                    n.putExtra("tipo",false);
                    n.putExtra("nombre",nombre.getText().toString());
                    n.putExtra("retos",false);
                    startActivity(n);
                }
            });
            modifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent n = new Intent(CrearNuevoRecorrido.this,CreadorRutas.class);
                    n.putExtra("RecNombre",nombreRecorrido.getText().toString());
                    n.putExtra("drescrip",brevDescripcionRecorrido.getText().toString());
                    n.putExtra("modif",true);
                    if(btn1.isChecked()==true){
                        n.putExtra("tipo",true);
                    }
                    else{
                        n.putExtra("tipo",false);
                    }
                    n.putExtra("name",currentData.getName());

                    startActivity(n);
                    Log.e("Recargo rutas:","lanzamiento");

                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    envios.add(currentData.getName());
                    int borrarRuta =con.hacerconexionGenerica("borrarRuta",envios);
                    envios.clear();
                    if(borrarRuta==-1){
                        retos.remove(currentData);
                        actualizar();
                    }
                    else{
                        Toast.makeText(CrearNuevoRecorrido.this,"Error al borrar ruta",Toast.LENGTH_LONG).show();
                    }

                    }
            });





            return intenView;
        }

    }


}
