package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class SeleccionRecorridos extends Activity implements View.OnClickListener {
    private ArrayList<Integer> dificultad=new ArrayList<Integer>();
    ImageView User;
    ImageView Rc1;
    ImageView Rc2;
    private Button cultura,ejercicio;
    private String name;
    private Conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_recorridos);

        User = (ImageView) findViewById(R.id.imageView);
        cultura=(Button) findViewById(R.id.buttonCulture);
        ejercicio=(Button) findViewById(R.id.buttonEjercicio);
        Rc1 = (ImageView) findViewById(R.id.imageView2);
        Rc2 = (ImageView) findViewById(R.id.imageView3);
        con =new Conexion();

        User.setOnClickListener(this);
        Rc1.setOnClickListener(this);
        Rc2.setOnClickListener(this);
        cultura.setOnClickListener(this);
        ejercicio.setOnClickListener(this);
        name= getIntent().getExtras().getString("NombreUser");
    }

    public void onClick(View v) {
        DatosRyR datosUser;

        Intent nueva;
        switch (v.getId()) {
            case R.id.buttonCulture:
                Intent n = new Intent(SeleccionRecorridos.this,RetoCultural.class);
                startActivity(n);
                break;
            case R.id.buttonEjercicio:
                Intent n2 = new Intent(SeleccionRecorridos.this, RetoDeportivo.class);
                startActivity(n2);
                break;
            case R.id.imageView2:
                datosUser=con.buscarUsuario(name);
                generarBuilder().show();
                nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                nueva.putExtra("tipo",1);
                startActivity(nueva);
                break;
            case R.id.imageView3:
                datosUser=con.buscarUsuario(name);
                nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                nueva.putExtra("tipo",0);
                startActivity(nueva);
                break;
            case R.id.imageView:
                nueva = new Intent(SeleccionRecorridos.this, ProfileActivity.class);
                nueva.putExtra("NombreUser",name);
                startActivity(nueva);
                break;
        }

    }
    public AlertDialog.Builder generarBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> aux = new ArrayList<>();
        final ArrayList<Integer> aux2 = new ArrayList<>();
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
                        dificultad = aux;
                        Toast.makeText(SeleccionRecorridos.this, Integer.toString(dificultad.get(0)), Toast.LENGTH_LONG).show();
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





}
