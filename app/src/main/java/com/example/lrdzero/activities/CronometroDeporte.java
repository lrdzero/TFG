package com.example.lrdzero.activities;

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

import com.example.lrdzero.datos.Conexion;

/**
 * Clase para cronometra el tiempo de ejecución de un reto deportivo.
 */


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
    private int tipoReto;
    private long timeMillisecons;
    private Conexion con;
    public void onResume(){
        super.onResume();

    }
    public void onPause(){
        super.onPause();

    }
    public void onDestroy(){
        super.onDestroy();

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
        tipoReto=getIntent().getExtras().getInt("tipoReto");
        tiempo=Integer.valueOf(getIntent().getExtras().getString("tiempo"));
       // Toast.makeText(CronometroDeporte.this, "Tiempo obtenido "+Integer.toString(tiempo),Toast.LENGTH_LONG).show();
        timeMillisecons = tiempo*60000;
        String tiempus = "TIEMPUS "+ timeMillisecons;
        //Toast.makeText(CronometroDeporte.this, tiempus,Toast.LENGTH_LONG).show();
        end.setEnabled(false);
        end.setVisibility(View.INVISIBLE);
        con = new Conexion();
        crono.setBase(SystemClock.elapsedRealtime());

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedMillis = SystemClock.elapsedRealtime() - crono.getBase();
                //String tiempus = "TIEMPUS "+ timeMillisecons;
                //Toast.makeText(CronometroDeporte.this,tiempus, Toast.LENGTH_LONG).show();
                if(myElapsedMillis>=timeMillisecons){
                    mp.stop();
                  finish();
                }
                else if(myElapsedMillis>= timeMillisecons/2){
                    end.setEnabled(true);
                    end.setVisibility(View.VISIBLE);
                    //String strElapsedMillis = "UUUUU milliseconds: " + myElapsedMillis;
                    //Toast.makeText(CronometroDeporte.this, strElapsedMillis, Toast.LENGTH_SHORT).show();
                }

            }
        });
        crono.start();


        mp = MediaPlayer.create(this,R.raw.deport);
        end.setOnClickListener(this);
        mp.setLooping(true);
        mp.start();

    }

    /**
     * Función para controlar eventos.
     * @param v
     */

    public void onClick(View v){
        switch (v.getId()){
            case R.id.botonEnd:
                mp.stop();
                crono.stop();
                Intent premio = new Intent(CronometroDeporte.this, RecogerPremio.class);
                premio.putExtra("nombreReto",nombreReto);
                premio.putExtra("nombreUser",nameUser);
                premio.putExtra("nombreRecorrido",nameRecorrido);
                premio.putExtra("nombreRuta", nameRuta);
                premio.putExtra("edad",edad);
                premio.putExtra("sexo",sexo);
                premio.putExtra("tipoReto",tipoReto);
                con.insertCompletado(nameUser, nombreReto);
                startActivityForResult(premio, 1);

                break;
        }
    }

    /**
     * Función para controlar respuesta de activity lanzada por esta clase.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                    mp.stop();
                    Intent i = getIntent();
                    // Le metemos el resultado que queremos mandar a la
                    // actividad principal.
                    i.putExtra("RESULTADO", 1);
                    // Establecemos el resultado, y volvemos a la actividad
                    // principal. La variable que introducimos en primer lugar
                    // "RESULT_OK" es de la propia actividad, no tenemos que
                    // declararla nosotros.
                    setResult(RESULT_OK, i);
                    crono.stop();
                    finish();
                    break;


            }
        }
    }
    public static int getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        int seconds = 0, minutes = 0, hours = 0;

        if(parts.length == 2){
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        }
        else if(parts.length == 3){
            seconds = Integer.parseInt(parts[2]);
            minutes = Integer.parseInt(parts[1]);
            hours = Integer.parseInt(parts[1]);
        }

        return seconds + (minutes*60) + (hours*3600);
    }
}
