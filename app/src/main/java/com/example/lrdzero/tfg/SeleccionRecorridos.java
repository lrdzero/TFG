package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class SeleccionRecorridos extends Activity implements View.OnClickListener {

    ImageView User;
    ImageView Rc1;
    ImageView Rc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_recorridos);

        User = (ImageView) findViewById(R.id.imageView);

        Rc1 = (ImageView) findViewById(R.id.imageView2);
        Rc2 = (ImageView) findViewById(R.id.imageView3);

        User.setOnClickListener(this);
        Rc1.setOnClickListener(this);
        Rc2.setOnClickListener(this);
    }

    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.imageView2:
                nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                startActivity(nueva);
                break;
            case R.id.imageView3:
                nueva = new Intent(SeleccionRecorridos.this, RecorridosParaUsuario.class);
                startActivity(nueva);
                break;
            case R.id.imageView:
                nueva = new Intent(SeleccionRecorridos.this, ProfileActivity.class);
                startActivity(nueva);
                break;
        }

    }





}
