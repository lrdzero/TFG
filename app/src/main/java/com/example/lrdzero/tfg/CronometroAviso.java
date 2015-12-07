package com.example.lrdzero.tfg;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class CronometroAviso extends ActionBarActivity {
    private Chronometer crono;
    private Button boton;
    private long tiempoEnd=20000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_aviso);

        crono=(Chronometer) findViewById(R.id.chronometer2);
        boton = (Button) findViewById(R.id.button4);


        crono.setBase(SystemClock.elapsedRealtime());
       

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedMillis = SystemClock.elapsedRealtime() - crono.getBase();
                //String tiempus = "TIEMPUS "+ timeMillisecons;
                //Toast.makeText(CronometroDeporte.this,tiempus, Toast.LENGTH_LONG).show();
                if (myElapsedMillis >= tiempoEnd) {
                    //mp.stop();
                    finish();
                }

            }
        });

        crono.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_horizontal_mochila, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
