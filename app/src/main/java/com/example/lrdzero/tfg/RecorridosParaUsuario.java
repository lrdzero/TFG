package com.example.lrdzero.tfg;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.lrdzero.tfg.R.drawable.f0907;


public class RecorridosParaUsuario extends Activity {

    private List<DatosRyR> dt = new ArrayList<DatosRyR>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos_para_usuario);




        Create();
        ListaView();


    }

    public void Create(){


        dt.add(new DatosRyR("RUTA PRIMERA", "23", "breve descripicon", "other"));
    }

    private void ListaView(){
        ArrayAdapter<DatosRyR> adapter= new PlaceList();
        ListView listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);
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

            //ImageView img = (ImageView) findViewById(R.id.imageView8);
            TextView txt1 = (TextView) findViewById(R.id.NombreRecorrido);
            TextView txt2 = (TextView) findViewById(R.id.nRutas);
            TextView txt3 = (TextView) findViewById(R.id.textView7);

            DatosRyR currentData = dt.get(position);

           // img.setImageResource(currentData.getImage());

           // txt1.setText("puta");
            //txt2.setText("puta2");
            //txt3.setText("puta3");

            return intenView;
        }

    }


}