package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecogerPremio extends Activity implements View.OnClickListener{
    private ArrayList<Items> dt = new ArrayList<Items>();
    private PlaceList adapter;
    private HorizontalListView lista2;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_premio);
        image = (ImageView) findViewById(R.id.imageView14);

        image.setOnClickListener(this);

        loadItems();
        ListaView();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView14:
                dt.add(new Items("Premio 1",R.drawable.premiodefecto));
                adapter.notifyDataSetChanged();
                finish();
                break;
        }
    }
    public class PlaceList extends ArrayAdapter<Items> {
        Items currentData;
        public PlaceList(){
            super(RecogerPremio.this,R.layout.activity_lista_horizontal_mochila,dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);


            currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());


            return intenView;
        }

        public String getNombre(){return currentData.getName();}

    }

    public void loadItems(){
        dt.add(new Items("nombre 1",R.drawable.busto ));
        dt.add(new Items("nombre 2",R.drawable.busto ));
        dt.add(new Items("nombre 3",R.drawable.busto ));
        dt.add(new Items("nombre 4",R.drawable.busto ));


    }
    private void ListaView(){
        adapter= new PlaceList();
        lista2 = (HorizontalListView) findViewById(R.id.listView3);
        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Items currentData = (Items) lista2.getItemAtPosition(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(RecogerPremio.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Quieres utilizar este Item ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        aceptar(currentData);
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
    public void aceptar(Items aBorrar) {
        final Items aB =aBorrar;
        Toast t=Toast.makeText(this,"Se mostrará una pista ya sea mediante toast o por medio de otro Dialog", Toast.LENGTH_SHORT);
        t.show();
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(RecogerPremio.this);
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

    public void cancelar() {

    }
    public void aceptar2(){
        adapter.notifyDataSetChanged();

    }
}
