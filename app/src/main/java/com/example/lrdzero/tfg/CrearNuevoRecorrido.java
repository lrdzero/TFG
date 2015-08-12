package com.example.lrdzero.tfg;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CrearNuevoRecorrido extends Activity {
    private Button recomen;
    private ArrayList<Integer> recomendaciones = new ArrayList<>();
    private ArrayList<DatosRyR> retos= new ArrayList<>();
    private PlaceList adapter;
    private ListView listaRetos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nuevo_recorrido);

        recomen = (Button) findViewById(R.id.botonRecomendado);
        listaRetos=(ListView) findViewById(R.id.listView5);

        Create();
        Visualizar();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        recomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();

                final ArrayList<Integer> aux = new ArrayList<>();

                // Set the dialog title
                //Log.i("title", "prog");
                builder.setTitle("Recomendado Para")
                        .setMultiChoiceItems(R.array.recomendado, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {

                                            aux.add(which);
                                        } else if (aux.contains(which)) {

                                            aux.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                                // Set the action buttons*/
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                recomendaciones = aux;
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                Log.i("cred", "prog");

                builder.show();




            }
        });
    }

    public void Create(){
        retos.add(new DatosRyR("Reto 1", "2", "Breve descripcion del recorrido", "alguien", R.drawable.busto, "mas descripcion"));
        retos.add(new DatosRyR("Reto 2", "2", "Breve descripcion del recorrido", "alguien", R.drawable.busto, "mas descripcion"));
        retos.add(new DatosRyR("Reto 3", "2", "Breve descripcion del recorrido", "alguien", R.drawable.busto, "mas descripcion"));
    }

    public void Visualizar(){
        adapter=new PlaceList();
        listaRetos.setAdapter(adapter);

    }


    public class PlaceList extends ArrayAdapter<DatosRyR> {

        public PlaceList(){
            super(CrearNuevoRecorrido.this,R.layout.listaretosencreadorrecorridos,retos);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.listaretosencreadorrecorridos,parent,false);
            }
            final DatosRyR currentData = retos.get(position);

            TextView nombre=(TextView)intenView.findViewById(R.id.RetoNombre);
            TextView descr=(TextView) intenView.findViewById(R.id.retoDescription);
            TextView time=(TextView) intenView.findViewById(R.id.Time);
            ImageView imag =(ImageView)intenView.findViewById(R.id.imagenNuevoReto);

            ImageView modifi =(ImageView) intenView.findViewById(R.id.btnEditar);
            ImageView eliminar =(ImageView) intenView.findViewById(R.id.imagenEliminar);

            nombre.setText(currentData.getName());
            descr.setText(currentData.getDescription());
            time.setText(currentData.getNumber());
            imag.setImageResource(currentData.getImage());

            modifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });





            return intenView;
        }

    }


}
