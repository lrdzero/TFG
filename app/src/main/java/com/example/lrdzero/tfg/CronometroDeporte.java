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
import android.widget.Toast;


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
    private String sexo,edad;
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
        sexo=getIntent().getExtras().getString("sexo");
        edad=getIntent().getExtras().getString("edad");

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
                premio.putExtra("nombreRuta", nameRuta);
                premio.putExtra("edad",edad);
                premio.putExtra("sexo",sexo);

                startActivityForResult(premio, 1);

                break;
        }
    }
    @Override
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
                    Intent i = getIntent();
                    // Le metemos el resultado que queremos mandar a la
                    // actividad principal.
                    i.putExtra("RESULTADO", 1);
                    // Establecemos el resultado, y volvemos a la actividad
                    // principal. La variable que introducimos en primer lugar
                    // "RESULT_OK" es de la propia actividad, no tenemos que
                    // declararla nosotros.
                    setResult(RESULT_OK, i);
                    finish();
                    break;


            }
        }
    }
}
