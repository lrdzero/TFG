package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.Chronometer;


public class CronometroDeporte extends Activity implements View.OnClickListener {
     private Chronometer crono;
    private AnalogClock clock;
    private long elapseTime=0;
    private String time="";
    private Button end;
    private String nombreReto;
    private int tiempo;
    private String nameUser,nameRuta,nameRecorrido;
    private MediaPlayer mp;

    public void onResume(){
        super.onResume();
        mp.setLooping(true);
        mp.start();
    }
    public void onPause(){
        super.onPause();
        mp.setLooping(false);
        mp.stop();
    }
    public void onDestroy(){
        super.onDestroy();
        mp.setLooping(false);
        mp.stop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_deporte);
        crono=(Chronometer) findViewById(R.id.chronometer);
        end=(Button)findViewById(R.id.botonEnd);
        nombreReto=getIntent().getExtras().getString("nombreReto");
        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido=getIntent().getExtras().getString("nombreRecorrido");
        nameRuta=getIntent().getExtras().getString("nombreRuta");

        tiempo=getIntent().getExtras().getInt("tiempo");

        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        mp = MediaPlayer.create(this,R.raw.deport);
        end.setOnClickListener(this);
        mp.setLooping(true);
        mp.start();

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.botonEnd:
                crono.stop();
                Intent premio = new Intent(CronometroDeporte.this,RecogerPremio.class);
                premio.putExtra("nombreReto",nombreReto);
                premio.putExtra("nombreUser",nameUser);
                premio.putExtra("nombreRecorrido",nameRecorrido);
                premio.putExtra("nombreRuta",nameRuta);
                startActivity(premio);
                break;
        }
    }
}
