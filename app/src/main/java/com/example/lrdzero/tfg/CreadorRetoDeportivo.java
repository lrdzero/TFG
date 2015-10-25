package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


public class CreadorRetoDeportivo extends Activity {
    private boolean lyout,modifi;
    private ArrayList<Integer> dificultades = new ArrayList<>();
    private String nombreRecorrido, descripRecorrido, nombreRuta;
    private Conexion con;
    private ArrayList<String> envio = new ArrayList<String>();
    private String name;
    private DatosRyR miDato = new DatosRyR();

    @Override
    public void onResume(){
        super.onResume();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lyout=getIntent().getExtras().getBoolean("tipo");
        modifi=getIntent().getExtras().getBoolean("modifi");
        nombreRecorrido=getIntent().getExtras().getString("RecNombre");
        descripRecorrido=getIntent().getExtras().getString("descrip");
        nombreRuta=getIntent().getExtras().getString("RutaName");
        con=new Conexion();
        if(lyout) {
            setContentView(R.layout.activity_creador_reto_deportivo);
            metodosDeportivos(modifi,getBaseContext());
        }
        else{
            setContentView(R.layout.activity_creador_reto_cultural);
            metodosCulturales(modifi,getBaseContext());
        }
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());


    }


    public void metodosDeportivos(boolean modifi, final Context c){

            Button crear=(Button) findViewById(R.id.botonCrearPDeportiva);
            Button dificultad=(Button) findViewById(R.id.buttonDificultadPrueva);
            final EditText nombreDeporti=(EditText) findViewById(R.id.editNombrePruebaDeportiva);
            final EditText descripcion =(EditText) findViewById(R.id.editPruevaDescripcion);
            final EditText tiempo =(EditText) findViewById(R.id.editText3);
            final EditText recomp =(EditText)findViewById(R.id.editTextPruevaDeportivaRecompensa);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dificultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Integer> aux = new ArrayList<>();

                // Set the dialog title
                //Log.i("title", "prog");
                builder.setTitle("Dificultad:")
                        .setMultiChoiceItems(R.array.dificultades, null,
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
                                dificultades = aux;
                                Toast.makeText(CreadorRetoDeportivo.this,Integer.toString(dificultades.get(0)),Toast.LENGTH_LONG).show();
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

                builder.show();
            }
        });

            if(modifi) {
                crear.setText("Modificar");
                final String nam = getIntent().getExtras().getString("nombreReto");

                miDato=con.buscarDatosRetoDeportivo(nam);

                nombreDeporti.setText(miDato.getName());
                descripcion.setText(miDato.getDescription());
                tiempo.setText(miDato.getNumber());
                recomp.setText(miDato.getLargeDescription());
                dificultad.setEnabled(false);


                crear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (nombreDeporti.getText().toString().matches("") || descripcion.getText().toString().matches("") || tiempo.getText().toString().matches("") || recomp.getText().toString().matches("")) {
                            Toast.makeText(c, "Hay campos sin rellenar", Toast.LENGTH_LONG).show();
                        } else {

                            envio.add(nam);
                            envio.add(nombreDeporti.getText().toString());
                            envio.add(descripcion.getText().toString());
                            envio.add(tiempo.getText().toString());
                            envio.add(recomp.getText().toString());
                            if(!dificultades.isEmpty()){
                                envio.add(Integer.toString(dificultades.get(0)));
                            }

                            int resultado = con.hacerconexionGenerica("updateReto", envio);
                            envio.clear();
                            if (resultado == -1) {
                                finish();
                            } else {
                                Toast.makeText(c, "Error en insercion reto", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });



            }
            else {
                crear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nombreDeporti.getText().toString().matches("") || descripcion.getText().toString().matches("") || tiempo.getText().toString().matches("") || recomp.getText().toString().matches("")) {
                            Toast.makeText(c, "Hay campos sin rellenar", Toast.LENGTH_LONG).show();
                        } else {
                            envio.add(nombreRecorrido);
                            envio.add(descripRecorrido);
                            envio.add(nombreRuta);
                            envio.add(nombreDeporti.getText().toString());
                            envio.add(descripcion.getText().toString());
                            envio.add(tiempo.getText().toString());
                            envio.add(recomp.getText().toString());
                            envio.add(Integer.toString(dificultades.get(0)));
                            int resultado = con.hacerconexionGenerica("crearRetoNuevo", envio);
                            envio.clear();
                            if (resultado == -1) {
                                finish();
                            } else {
                                Toast.makeText(c, "Error en insercion reto", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
    }
    public void metodosCulturales(boolean modifi,final Context c){
        Button crear=(Button) findViewById(R.id.botonCrear);
        Button dificultad=(Button) findViewById(R.id.botonDifucultad);
        final EditText nombre =(EditText)findViewById(R.id.editText);
        final EditText Pregunta=(EditText)findViewById(R.id.editPregunta);
        final EditText respA = (EditText) findViewById(R.id.editTextA);
        final EditText respB = (EditText) findViewById(R.id.editTextB);
        final EditText respC = (EditText) findViewById(R.id.editTextC);
        final EditText respD = (EditText) findViewById(R.id.editTextD);
        final EditText descRecom = (EditText) findViewById(R.id.editText2);
        final RadioButton btnA =(RadioButton)findViewById(R.id.radioButtonA);
        final RadioButton btnB =(RadioButton)findViewById(R.id.radioButtonB);
        final RadioButton btnC =(RadioButton)findViewById(R.id.radioButtonC);
        final RadioButton btnD =(RadioButton)findViewById(R.id.radioButtonD);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dificultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Integer> aux = new ArrayList<>();

                // Set the dialog title
                //Log.i("title", "prog");
                builder.setTitle("Dificultad:")
                        .setMultiChoiceItems(R.array.dificultades, null,
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
                                dificultades = aux;
                                Toast.makeText(CreadorRetoDeportivo.this,Integer.toString(dificultades.get(0)),Toast.LENGTH_LONG).show();
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

                builder.show();
            }
        });

        if(modifi) {
            crear.setText("Modificar");
            final String nam = getIntent().getExtras().getString("nombreReto");


            DatosRyR nuevo = new DatosRyR();
            nuevo=con.buscarDatosRetoCultural(nam);

            nombre.setText(nuevo.getName());
            Pregunta.setText(nuevo.getDescription());
            respA.setText(nuevo.getOther());
            respB.setText(nuevo.getNumber());
            respC.setText(nuevo.getAdic());
            respD.setText(nuevo.getAux());
            descRecom.setText(nuevo.getLargeDescription());
            dificultad.setEnabled(false);

            crear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    envio.add(nam);
                    envio.add(nombre.getText().toString());
                    envio.add(Pregunta.getText().toString());
                    envio.add(respA.getText().toString());
                    envio.add(respB.getText().toString());
                    envio.add(respC.getText().toString());
                    envio.add(respD.getText().toString());
                    envio.add(descRecom.getText().toString());
                    envio.add(Integer.toString(dificultades.get(0)));
                    if(btnA.isChecked()==true){
                        envio.add("A");
                    }
                    else if(btnB.isChecked()==true){
                        envio.add("B");
                    }
                    else if(btnC.isChecked()==true){
                        envio.add("C");
                    }
                    else if(btnD.isChecked()==true){
                        envio.add("D");
                    }

                    int resultado = con.hacerconexionGenerica("updateRetoCultural",envio);
                    envio.clear();
                    if(resultado==-1){
                        finish();
                    }
                    else{
                        Toast.makeText(c, "Error en insercion reto", Toast.LENGTH_LONG).show();
                    }


                }
            });
        }
        else{
            crear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(nombre.getText().toString().matches("")||Pregunta.getText().toString().matches("")||descRecom.getText().toString().matches("")||respA.getText().toString().matches("")||respB.getText().toString().matches("")||respC.getText().toString().matches("")||respD.getText().toString().matches("")){
                                                Toast.makeText(c,"Hay campos sin rellenar",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if((btnA.isChecked()==false)&&(btnB.isChecked()==false)&&(btnC.isChecked()==false)&&(btnD.isChecked()==false)){
                            Toast.makeText(c,"No ha seleccionado una respuesta",Toast.LENGTH_LONG).show();
                        }
                        else{
                            envio.add(nombreRecorrido);
                            envio.add(descripRecorrido);
                            envio.add(nombreRuta);
                            envio.add(nombre.getText().toString());
                            envio.add(Pregunta.getText().toString());
                            envio.add(respA.getText().toString());
                            envio.add(respB.getText().toString());
                            envio.add(respC.getText().toString());
                            envio.add(respD.getText().toString());
                            envio.add(descRecom.getText().toString());
                            if(btnA.isChecked()==true){
                                envio.add("A");
                            }
                            else if(btnB.isChecked()==true){
                                envio.add("B");
                            }
                            else if(btnC.isChecked()==true){
                                envio.add("C");
                            }
                            else if(btnD.isChecked()==true){
                                envio.add("D");
                            }

                            int resultado = con.hacerconexionGenerica("crearRetoNuevoCultural",envio);
                            envio.clear();
                            if(resultado==-1){
                                finish();
                            }
                            else{
                                Toast.makeText(c, "Error en insercion reto", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
        }

    }


}
