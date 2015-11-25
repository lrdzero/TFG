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

                generarBuilder(1);

                dificultad.clear();

                break;

            case R.id.imageView3:

                generarBuilder(0);

                break;
            case R.id.imageView:
                nueva = new Intent(SeleccionRecorridos.this, ProfileActivity.class);
                nueva.putExtra("NombreUser",name);
                startActivity(nueva);
                break;
        }

    }
    public void generarBuilder(final int tipo){
        final DatosRyR datosUser;


        datosUser=con.buscarUsuario(name);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> aux = new ArrayList<>();
        final ArrayList<Integer> aux2 = new ArrayList<>();
        final int variable=tipo;
        Toast.makeText(SeleccionRecorridos.this,"Pref1 "+datosUser.getPreferenciaUser1(),Toast.LENGTH_LONG).show();
        //Toast.makeText(SeleccionRecorridos.this,"Por favor rellene información adicional en su perfil (Preferencias)",Toast.LENGTH_LONG).show();
        if(datosUser.getPreferenciaUser1().equals("2")&&datosUser.getPreferenciaUser2().equals("2")){
            builder.setTitle("Es necesaria más información").setMessage("Es necesario que especifique sus preferencias.\n\tPor favor acceda al perfil")
            .setPositiveButton("Ir a mi perfil", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent perfil;
                    perfil = new Intent(SeleccionRecorridos.this, ProfileActivity.class);
                    perfil.putExtra("NombreUser",name);
                    startActivity(perfil);
                }
            })
            .setNegativeButton("En otro momento", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else {
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
                            final Intent nueva;
                            nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                            nueva.putExtra("tipo", variable);
                            nueva.putExtra("edad", datosUser.getNumber());
                            nueva.putExtra("pref1", datosUser.getPreferenciaUser1());
                            nueva.putExtra("pref2", datosUser.getPreferenciaUser2());
                            nueva.putExtra("dificultad", Integer.toString(dificultad.get(0)));
                            startActivity(nueva);
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
    }





}
