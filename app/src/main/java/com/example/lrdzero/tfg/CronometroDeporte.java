package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_deporte);
        crono=(Chronometer) findViewById(R.id.chronometer);
        end=(Button)findViewById(R.id.botonEnd);

        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        end.setOnClickListener(this);


    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.botonEnd:
                crono.stop();
                Intent premio = new Intent(CronometroDeporte.this,RecogerPremio.class);
                startActivity(premio);
                break;
        }
    }
}
