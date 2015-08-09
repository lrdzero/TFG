package com.example.lrdzero.tfg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RetoCultural extends FragmentActivity implements View.OnClickListener {
    private ArrayList<Items> dt = new ArrayList<Items>();
    private ListView lista;
    private HorizontalListView lista2;
    private ArrayAdapter<Items> adapter;
    private TextView pregunta, introduzcion;
    private Button respuesta;
    private RadioButton resp1,resp2,resp3,resp4;
    private RadioGroup group1,group2;
    private AlertDialog alert;


    private static int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_cultural);

        group1 =(RadioGroup) findViewById(R.id.rGroup1);
        group2 =(RadioGroup) findViewById(R.id.rGroup2);
        resp1 = (RadioButton) findViewById(R.id.responseA);
        resp2 = (RadioButton) findViewById(R.id.responseB);
        resp3 = (RadioButton) findViewById(R.id.responseC);
        resp4 = (RadioButton) findViewById(R.id.responseD);
        respuesta = (Button) findViewById(R.id.buttonResponse);


        loadItems();
        ListaView();

        resp1.setOnClickListener(this);
        resp2.setOnClickListener(this);
        resp3.setOnClickListener(this);
        resp4.setOnClickListener(this);
        respuesta.setOnClickListener(this);


    }

    private void ListaView(){
        adapter= new PlaceList();
        lista2 = (HorizontalListView) findViewById(R.id.listView3);
        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(RetoCultural.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Quieres utilizar este Item ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();


            }
        });
        lista2.setAdapter(adapter);
    }
    private void repaint(){


    }
    public class PlaceList extends ArrayAdapter<Items> {

        public PlaceList(){
            super(RetoCultural.this,R.layout.activity_listas_con_imagen,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            final Items currentData = dt.get(position);

            img.setImageResource(R.drawable.busto);
            txt1.setText(currentData.getName());


             return intenView;
        }

    }

    public void loadItems(){
        dt.add(new Items("nombre 1",R.drawable.busto ));
        dt.add(new Items("nombre 2",R.drawable.busto ));
        dt.add(new Items("nombre 2",R.drawable.busto ));
        dt.add(new Items("nombre 2",R.drawable.busto ));


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.responseA:
                group2.clearCheck();
                selected=0;
                break;
            case R.id.responseB:
                group2.clearCheck();
                selected=1;
                break;
            case R.id.responseC:
                group1.clearCheck();
                selected=2;
                break;
            case R.id.responseD:
                group1.clearCheck();
                selected=3;
                break;
            case R.id.buttonResponse:
                    Toast.makeText(RetoCultural.this,"Tu respuesta ha sido "+ selected,Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void aceptar() {
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RetoCultural.this);
        dialogo2.setTitle("Pista");
        dialogo2.setMessage("Pista util para responder a la pregunta");
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar2();
            }
        });

        dialogo2.show();
    }

    public void cancelar() {

    }
    public void aceptar2(){

    }
}
