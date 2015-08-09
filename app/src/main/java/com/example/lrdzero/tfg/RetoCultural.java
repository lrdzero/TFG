package com.example.lrdzero.tfg;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class RetoCultural extends FragmentActivity implements View.OnClickListener {
    private ArrayList<Items> dt = new ArrayList<Items>();
    private ListView lista;
    private HorizontalListView lista2;
    private ArrayAdapter<Items> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_cultural);

   

        loadItems();
        ListaView();



    }

    private void ListaView(){
        adapter= new PlaceList();
        lista2 = (HorizontalListView) findViewById(R.id.listView3);
        lista2.setAdapter(adapter);
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

    }


}
