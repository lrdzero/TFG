package com.example.lrdzero.tfg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
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
    private boolean recorr;
    private final static String TITULO ="Recorridos para tí";
    private final static String TITULO2 ="Rutas Disponibles";
    private final static String TITULO3 ="HAGAMOSLO. Selecciona una ruta en la que quieras participar";
    private final static String TITULO4 ="Selecciona el recorrido en el que quieras participar";
    private ListView listView;
    private ArrayAdapter<DatosRyR>  adapter,adapter2;

    private static Socket sk;
    private static int port=7;
    private static String ip="192.168.1.33";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos_para_usuario);

        tituloRecorrido =(TextView) findViewById(R.id.TextoRecorrido);
        textoGuia =(TextView) findViewById(R.id.TextoGuia);
        atras =(Button) findViewById(R.id.button2);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        atras.setEnabled(false);
        atras.setVisibility(atras.INVISIBLE);
        atras.setOnClickListener(this);
        textoGuia.setText(TITULO4);
        recorr =true;
        Create();

        ListaView();


    }

    public void onClick(View v) {

        Intent nueva;
        switch (v.getId()) {
            case R.id.button2:
                tituloRecorrido.setText(TITULO);

                atras.setEnabled(false);
                atras.setVisibility(atras.INVISIBLE);
                textoGuia.setText(TITULO4);
                dt.clear();
                Create();
                listView.setAdapter(adapter);
                recorr=true;
                break;


        }

    }

    public void Create(){
        /*
        try{
            Log.e("TagConnect","Create part intentando conexion");
            sk = new Socket(ip, port);
            DataInputStream in = new DataInputStream(sk.getInputStream());
            DataOutputStream out = new DataOutputStream(sk.getOutputStream());
            Log.e("TagConnect","Enviando");
            out.writeUTF("ListarDatosRutas");
            Log.e("TagConnect","Enviado");
            if(in.readUTF().equals("continuar")){
                Log.e("TagConnect","Aceptado");
                int tama= in.read();
                for(int i=0;i<tama;i++) {
                    String Nombre = in.readUTF();
                    String num = in.readUTF();
                    String breveD = in.readUTF();
                    String autor = in.readUTF();
                    String Descrip = in.readUTF();
                    dt.add(new DatosRyR(Nombre, num, breveD, autor, R.drawable.f0907, Descrip));
                }
            }
            else{
                Toast.makeText(RecorridosParaUsuario.this,"Error en conexion",Toast.LENGTH_LONG).show();

            }
            sk.close();

        }catch(IOException e){
            e.printStackTrace();
        }
*/
        dt.add(new DatosRyR("RECORRIDO 1", "2", "Ruta de muestra inicial 1", "JAVIEL RAMBIEL",R.drawable.f0907,"Una ruta POSICION 1 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        dt.add(new DatosRyR("RECORRIDO 2", "3", "Ruta de muestra inicial 2", "ISMAEL",R.drawable.f0907,"Una ruta POSICION 2 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        dt.add(new DatosRyR("RECORRIDO 3", "4", "Ruta de muestra inicial 3", "FEDERICO",R.drawable.f0907,"Una ruta POSICION 3 que no tiene nada por el momento y que es utilizada a modo de prueba"));
        dt.add(new DatosRyR("RECORRIDO 4", "7", "Ruta de muestra inicial 4", "PAQUITO",R.drawable.f0907,"Una ruta POSICION 4 que no tiene nada por el momento y que es utilizada a modo de prueba"));

    }

    private void ListaView(){
        adapter= new PlaceList();
        listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(recorr)
                    textoGuia.setText(dt.get(position).getLargeDescription());



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

            final DatosRyR currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());
            txt2.setText(currentData.getNumber());
            txt3.setText(currentData.getDescription());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        dt.clear();
                    dt.add(new DatosRyR("RUTA PRIMERA", "1", "Ruta de muestra inicial", "JAVIEL RAMBIEL",R.drawable.f0907,"Una ruta inicial que no tiene nada por el momento y que es utilizada a modo de prueba"));
                        adapter2 = new PlaceList2(currentData.getName());
                        atras.setVisibility(atras.VISIBLE);
                        atras.setEnabled(true);
                        tituloRecorrido.setText(TITULO2);
                        textoGuia.setText(TITULO3);
                        listView.setAdapter(adapter2);
                        recorr =false;


                }
            });


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