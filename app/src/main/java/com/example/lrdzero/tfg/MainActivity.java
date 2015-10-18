package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button inicio;
    private Button registro;
    private EditText user;
    private EditText contrasenia;
    private Conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicio = (Button) findViewById(R.id.botonSesion);
        registro = (Button) findViewById(R.id.botonRegistro);

        user=(EditText)findViewById(R.id.nombre);
        contrasenia=(EditText)findViewById(R.id.contrasenia);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        con = new Conexion();
        inicio.setOnClickListener(this);
        registro.setOnClickListener(this);
    }


    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.botonSesion:
               if(user.getText().toString().matches("")||contrasenia.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this,"No ha introducido nombre o contraseña",Toast.LENGTH_LONG).show();
                }
                else{
                   int result = con.IniciarSesion(user.getText().toString(),contrasenia.getText().toString());
                    if(result==-1){
                        Toast.makeText(MainActivity.this,"Usuario Inexistente",Toast.LENGTH_LONG).show();
                    }
                    else if(result==0){
                        Toast.makeText(MainActivity.this, "Contraseña no válida.", Toast.LENGTH_LONG).show();
                    }
                   else if(result==1){
                        nueva = new Intent(MainActivity.this, SeleccionRecorridos.class);
                        nueva.putExtra("NombreUser",user.getText().toString());

                        startActivity(nueva);
                    }

                }

                break;
            case R.id.botonRegistro:
                nueva = new Intent(MainActivity.this,Mapa.class);
                startActivity(nueva);
                break;


        }

    }
}
