package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.lrdzero.tfg.R.drawable.f0907;


public class RecorridosParaUsuario extends Activity implements View.OnClickListener{

    private List<DatosRyR> dt = new ArrayList<DatosRyR>();
    private TextView tituloRecorrido;
    private TextView textoGuia;
    private Button atras;
    private final static String TITULO ="Recorridos para tí";
    private final static String TITULO2 ="Rutas Disponibles";
    private final static String TITULO3 ="HAGAMOSLO. Selecciona una ruta en la que quieras participar";
    private final static String TITULO4 ="Selecciona el recorrido en el que quieras participar";
    private ListView listView;
    private ArrayAdapter<DatosRyR>  adapter,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos_para_usuario);

        tituloRecorrido =(TextView) findViewById(R.id.TextoRecorrido);
        textoGuia =(TextView) findViewById(R.id.TextoGuia);
        atras =(Button) findViewById(R.id.button2);

        atras.setEnabled(false);
        atras.setVisibility(atras.INVISIBLE);
        atras.setOnClickListener(this);
        textoGuia.setText(TITULO4);
        Create();

        ListaView();


    }

    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.button2:
                tituloRecorrido.setText(TITULO);
                listView.setAdapter(adapter);
                atras.setEnabled(false);
                atras.setVisibility(atras.INVISIBLE);
                textoGuia.setText(TITULO4);
                break;


        }

    }

    public void Create(){

        dt.add(new DatosRyR("RUTA PRIMERA", "23", "breve descripicon", "JAVIEL RAMBIEL",R.drawable.f0907));

    }

    private void ListaView(){
        adapter= new PlaceList();
        listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               adapter2 = new PlaceList2(dt.get(position).getName());
               tituloRecorrido.setText(TITULO2);
               atras.setVisibility(atras.VISIBLE);
               atras.setEnabled(true);
               textoGuia.setText(TITULO3);
               listView.setAdapter(adapter2);

           }
       });
    }

    public class PlaceList extends ArrayAdapter<DatosRyR>{

            public PlaceList(){
                super(RecorridosParaUsuario.this,R.layout.activity_listas_con_imagen,dt);
            }

        public View getView(int position,View convertView, ViewGroup parent){
            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_listas_con_imagen,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.imageView8);
            TextView txt1 = (TextView) intenView.findViewById(R.id.NombreRecorrido);
            TextView txt2 = (TextView) intenView.findViewById(R.id.nRutas);
            TextView txt3 = (TextView) intenView.findViewById(R.id.textView7);

            DatosRyR currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());
            txt2.setText(currentData.getNumber());
            txt3.setText(currentData.getDescription());


            return intenView;
        }

    }

    public class PlaceList2 extends ArrayAdapter<DatosRyR>{
        private String nameRoute;
        public PlaceList2(String name){

            super(RecorridosParaUsuario.this,R.layout.activity_listas_con_imagen,dt);
            nameRoute=name;
        }

        public View getView(int position,View convertView, ViewGroup parent){
            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_listas_con_imagen,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.imageView8);
            TextView txt1 = (TextView) intenView.findViewById(R.id.NombreRecorrido);
            TextView txt2 = (TextView) intenView.findViewById(R.id.nRutas);
            TextView txt3 = (TextView) intenView.findViewById(R.id.textView7);

            DatosRyR currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());
            txt2.setText(currentData.getOther());
            txt3.setText(currentData.getDescription());


            return intenView;
        }
    }


}