package com.example.lrdzero.tfg;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class SeleccionRecorridos extends Activity {

    ImageView User;
    ImageView Rc1;
    ImageView Rc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_recorridos);

        User = (ImageView) findViewById(R.id.imageView);

        Rc1 = (ImageView) findViewById(R.id.imageView2);
        Rc2 =(ImageView)findViewById(R.id.imageView3);



    }



}
