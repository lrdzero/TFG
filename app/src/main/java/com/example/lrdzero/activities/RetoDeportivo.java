package com.example.lrdzero.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrdzero.data.Conexion;
import com.example.lrdzero.data.DatosRyR;
import com.example.lrdzero.data.Recompensa;

import java.util.ArrayList;

/**
 * Clase para control de interfaz de reto tipo deportivo.
 */
public class RetoDeportivo extends Activity implements View.OnClickListener{
    private HorizontalListView mochila;
    private ArrayList<Recompensa> dt = new ArrayList<Recompensa>();
    private PlaceList adapter;
    private Button go;
    private Conexion con;
    private DatosRyR datosReto;
    private String nameUser,nameRecorrido,nameRuta,sexo,edad,nombreReto;
    private ImageView parpadoder;
    private ImageView parpadoiz;
    private ImageView brazoDer;
    private ImageView brazoIz;
    private ImageView cuerpo;
    private ImageView boca;
    private ImageView ojos;
    private ImageView cola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_deportivo);
        TextView desafio =(TextView) findViewById(R.id.desafio);
        //Carga Avatar
        parpadoder =(ImageView) findViewById(R.id.parpder);
        parpadoiz=(ImageView) findViewById(R.id.parpizq);
        brazoDer =(ImageView) findViewById(R.id.brazoder);
        brazoIz=(ImageView) findViewById(R.id.brazoizq);
        cuerpo =(ImageView) findViewById(R.id.cabeza);
        boca = (ImageView)findViewById(R.id.bocaverde);
        ojos = (ImageView)findViewById(R.id.ojos);
        cola=(ImageView) findViewById(R.id.cola);

        con=new Conexion();
        nameUser=getIntent().getExtras().getString("nombreUser");
        nameRecorrido = getIntent().getExtras().getString("nombreRecorrido");
        nameRuta =getIntent().getExtras().getString("nombreRuta");
        nombreReto=getIntent().getExtras().getString("nombreReto");
        datosReto = con.buscarDatosRetoDeportivo(nombreReto);
        sexo =getIntent().getExtras().getString("sexo");
        edad=getIntent().getExtras().getString("edad");

        adaptacion(sexo,edad);
        if(datosReto==null){
            Toast.makeText(RetoDeportivo.this,"ERROR EN OBTENCION",Toast.LENGTH_LONG).show();
        }
        else{
            desafio.setText(datosReto.getDescription());
            Toast.makeText(RetoDeportivo.this,"Mi tiempo es "+datosReto.getNumber(),Toast.LENGTH_LONG).show();
        }
        go=(Button) findViewById(R.id.buttonStart);


       // loadItems();
        //ListView();

        go.setOnClickListener(this);

    }

    /**
     * Función para control de eventos.
     * @param v
     */
    public void onClick(View v){

        switch(v.getId()){
            case R.id.buttonStart:
                    Intent nuevo = new Intent(RetoDeportivo.this,CronometroDeporte.class);
                    nuevo.putExtra("nombreReto",datosReto.getName());
                    nuevo.putExtra("tiempo",datosReto.getNumber());
                    nuevo.putExtra("nombreUser",nameUser);
                    nuevo.putExtra("nombreRecorrido",nameRecorrido);
                    nuevo.putExtra("nombreRuta",nameRuta);
                    nuevo.putExtra("sexo",sexo);
                    nuevo.putExtra("edad",edad);
                    nuevo.putExtra("tipoReto",0);
                    startActivityForResult(nuevo,1);

                break;
        }

    }

    /**
     * Función depreciada para carga de items
     */
    public void loadItems(){

    }

    /**
     * Función depreciada para carga de vista.
     */
    public void ListView(){

    }

    /**
     * Clase depreciada para control de recompensas.
     */
    public class PlaceList extends ArrayAdapter<Recompensa> {
        Recompensa currentData;
        public PlaceList(){
            super(RetoDeportivo.this,R.layout.activity_lista_horizontal_mochila,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            final ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            currentData = dt.get(position);

            img.setImageResource(R.drawable.busto);
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    /**
     * Función depreciada para obtener una pista.
     * @param aBorrar
     */
    public void aceptar(Recompensa aBorrar) {
        final Recompensa aB =aBorrar;
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RetoDeportivo.this);
        dialogo2.setTitle("Pista");
        dialogo2.setMessage("Pista util para responder a la pregunta");
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dt.remove(aB);
                aceptar2();
            }
        });

        dialogo2.show();
    }

    /**
     * Función depreciada para cancelar el mostrado de una pista.
     */
    public void cancelar() {

    }

    /**
     * Función para notificar de cambios al adapter de la lista.
     */
    public void aceptar2(){
        adapter.notifyDataSetChanged();

    }

    /**
     * Función para recpción de datos de los activities lanzados desde esta clase.
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
            finish();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            String resultado = data.getExtras().getString("RESULTADO");
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case 1:
                    finish();
                    break;


            }
        }
    }

    /**
     * Función para adpatación del guía al usuario.
     * @param sexo
     * @param edad
     */
    private void adaptacion(String sexo,String edad){

        if(sexo.equals("H")){
            if(Integer.valueOf((edad))<18){
                boca.setImageResource(R.drawable.boca_n);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_n);
                parpadoiz.setImageResource(R.drawable.parpadoizq_n);
                brazoIz.setImageResource(R.drawable.manoizq);
                brazoDer.setImageResource(R.drawable.manoder);
                cuerpo.setImageResource(R.drawable.cuerpo_n);
                cola.setImageResource(R.drawable.cola_n);
            }
            else if(Integer.valueOf(edad)>=18&&Integer.valueOf(edad)<57) {
                boca.setImageResource(R.drawable.boca);
                ojos.setImageResource(R.drawable.ojos);
                //insertaMujer();
                parpadoder.setImageResource(R.drawable.parpadoder);
                parpadoiz.setImageResource(R.drawable.parpadoizq);
                brazoIz.setImageResource(R.drawable.manoizq);
                brazoDer.setImageResource(R.drawable.manoder);
                cuerpo.setImageResource(R.drawable.cuerpo);
                cola.setImageResource(R.drawable.cola);
            }
            else{
                boca.setImageResource(R.drawable.boca_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_a);
                brazoIz.setImageResource(R.drawable.manoizq_a);
                brazoDer.setImageResource(R.drawable.manoder_a);
                cuerpo.setImageResource(R.drawable.cuerpo_a);
                cola.setImageResource(R.drawable.cola_a);
            }
        }
        else{
            if(Integer.valueOf((edad))<18){
                boca.setImageResource(R.drawable.boca_h_n);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_h_n);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h_n);
                brazoIz.setImageResource(R.drawable.manoizq_h_n);
                brazoDer.setImageResource(R.drawable.manoder_h_n);
                cuerpo.setImageResource(R.drawable.cuerpo_h_n);
                cola.setImageResource(R.drawable.cola_h_n);
            }
            else if(Integer.valueOf(edad)>=18&&Integer.valueOf(edad)<57) {
                boca.setImageResource(R.drawable.boca_h);
                ojos.setImageResource(R.drawable.ojos);
                //insertaMujer();
                parpadoder.setImageResource(R.drawable.parpadoder_h);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h);
                brazoIz.setImageResource(R.drawable.manoizq_h);
                brazoDer.setImageResource(R.drawable.manoder_h);
                cuerpo.setImageResource(R.drawable.cuerpo_h);
                cola.setImageResource(R.drawable.cola_h);
            }
            else{
                boca.setImageResource(R.drawable.boca_h_a);
                ojos.setImageResource(R.drawable.ojos);
                parpadoder.setImageResource(R.drawable.parpadoder_h_a);
                parpadoiz.setImageResource(R.drawable.parpadoizq_h_a);
                brazoIz.setImageResource(R.drawable.manoizq_h_a);
                brazoDer.setImageResource(R.drawable.manoder_h_a);
                cuerpo.setImageResource(R.drawable.cuerpo_h_a);
                cola.setImageResource(R.drawable.cola_h_a);
            }

        }
    }
}
