package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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

    private String name;
    private Conexion con;
    private DatosRyR datosUser;
    private MediaPlayer error;
    private MediaPlayer miMusic;
    private String musica="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_recorridos);

        User = (ImageView) findViewById(R.id.imageView);

        Rc1 = (ImageView) findViewById(R.id.imageView2);
        Rc2 = (ImageView) findViewById(R.id.imageView3);
        con =new Conexion();

        error=MediaPlayer.create(this,R.raw.alert);
        User.setOnClickListener(this);
        Rc1.setOnClickListener(this);
        Rc2.setOnClickListener(this);
        name= getIntent().getExtras().getString("NombreUser");
       // Toast.makeText(SeleccionRecorridos.this,"Nombre de usuario "+ name,Toast.LENGTH_LONG).show();





    }

    public void onClick(View v) {
        DatosRyR datosUser;

        Intent nueva;
        switch (v.getId()) {

            case R.id.imageView2:

                generarBuilder(1);
//aasdfa
                dificultad.clear();

                break;

            case R.id.imageView3:

                generarBuilder(0);

                break;
            case R.id.imageView:
                nueva = new Intent(SeleccionRecorridos.this, ProfileActivity.class);
                nueva.putExtra("NombreUser",name);
                startActivityForResult(nueva,1);
                break;
        }

    }
    public void generarBuilder(final int tipo){

        datosUser=con.buscarUsuario(name);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> aux = new ArrayList<>();
        final ArrayList<Integer> aux2 = new ArrayList<>();
        final int variable=tipo;

        //Toast.makeText(SeleccionRecorridos.this,"Por favor rellene información adicional en su perfil (Preferencias)",Toast.LENGTH_LONG).show();
        if(datosUser.getPreferenciaUser1().equals("2")&&datosUser.getPreferenciaUser2().equals("2")){
            error.start();
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
                            if(dificultad.size()==1) {
                                final Intent nueva;
                                nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                                nueva.putExtra("tipo", variable);
                                nueva.putExtra("creador",name);
                                nueva.putExtra("edad", datosUser.getNumber());
                                nueva.putExtra("sexo",datosUser.getAdic());
                                nueva.putExtra("musica",musica);
                                nueva.putExtra("pref1", datosUser.getPreferenciaUser1());
                                nueva.putExtra("pref2", datosUser.getPreferenciaUser2());
                                nueva.putExtra("dificultad", Integer.toString(dificultad.get(0) + 1));
                                startActivity(nueva);
                            }
                            else{
                                Toast.makeText(SeleccionRecorridos.this,"Debe seleccionar una unica dificultad.",Toast.LENGTH_LONG).show();
                            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            String resultado = data.getExtras().getString("RESULTADO");
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case 1:
                    musica= data.getExtras().getString("musica");
                    if(musica.matches("")){
                        //musica="default";
                        Toast.makeText(SeleccionRecorridos.this,musica,Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SeleccionRecorridos.this, "La musica es " + musica, Toast.LENGTH_LONG).show();
                    }

                    //finish();
                    break;


            }
        }
    }




}
