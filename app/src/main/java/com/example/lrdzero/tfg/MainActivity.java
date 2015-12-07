package com.example.lrdzero.tfg;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button inicio;
    private Button registro;

    private Conexion con;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);

        inicio = (Button) findViewById(R.id.botonSesion);
        registro = (Button) findViewById(R.id.botonRegistro);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        con = new Conexion();

        inicio.setOnClickListener(this);
        registro.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.botonSesion:


                LayoutInflater factory1 = LayoutInflater.from(this);
                final View textEntryView1 = factory1.inflate(R.layout.login, null);
                AlertDialog.Builder alert1 = new AlertDialog.Builder(this);

                alert1.setView(textEntryView1);
                alert1.setTitle("Por favor Inicie Sesión:");
                alert1.setMessage("Introduzca Nick y Contraseña");



                AlertDialog loginPrompt1 = alert1.create();

                final EditText txtUsername = (EditText) textEntryView1.findViewById(R.id.editText4);
                final EditText txtPassword = (EditText) textEntryView1.findViewById(R.id.textoPass);



                alert1.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        if(txtUsername.getText().toString().matches("")||txtPassword.getText().toString().matches("")){
                            Toast.makeText(MainActivity.this,"No ha introducido nombre o contraseña",Toast.LENGTH_LONG).show();
                        }
                        else{
                            int result = con.IniciarSesion(txtUsername.getText().toString(),txtPassword.getText().toString());
                            if(result==-1){
                                Toast.makeText(MainActivity.this,"Usuario Inexistente",Toast.LENGTH_LONG).show();
                            }
                            else if(result==0){
                                Toast.makeText(MainActivity.this, "Contraseña no válida.", Toast.LENGTH_LONG).show();
                            }
                            else if(result==1){
                                Intent nueva = new Intent(MainActivity.this, SeleccionRecorridos.class);
                                nueva.putExtra("NombreUser",txtUsername.getText().toString());

                                startActivity(nueva);
                            }

                        }



                    }
                });

                alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert1.show();



               break;
            case R.id.botonRegistro:
                Intent nueva = new Intent(MainActivity.this,CronometroAviso.class);
                nueva.putExtra("creador","l");
                nueva.putExtra("nombreRecorrido","esquina");
                nueva.putExtra("nombreRuta","rutac");
                startActivity(nueva);
                break;


        }

    }
}
