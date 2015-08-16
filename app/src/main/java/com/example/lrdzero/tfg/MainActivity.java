package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button inicio;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicio = (Button) findViewById(R.id.botonSesion);
        registro = (Button) findViewById(R.id.botonRegistro);

        inicio.setOnClickListener(this);
        registro.setOnClickListener(this);
    }


    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.botonSesion:
                nueva = new Intent(MainActivity.this, SeleccionRecorridos.class);
                startActivity(nueva);
                break;
            case R.id.botonRegistro:
                nueva = new Intent(MainActivity.this, RecorridosParaUsuario.class);
                startActivity(nueva);
                break;


        }

    }
}
