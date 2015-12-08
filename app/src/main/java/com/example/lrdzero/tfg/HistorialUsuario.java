package com.example.lrdzero.tfg;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HistorialUsuario extends Activity {
    private Conexion con;
    private String userName;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_usuario);
        con =new Conexion();
        userName=getIntent().getExtras().getString("userName");

        createGroupList();

        //createCollection();

        expListView = (ExpandableListView) findViewById(R.id.recorrido_list);
        final ExpandableRecorridosAdapter expListAdapter = new ExpandableRecorridosAdapter(
                this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList() {
        laptopCollection = new LinkedHashMap<>();
        ArrayList<String>complen=con.cargarRecorridosParticipados(userName);
        ArrayList<ValoresHistorial> Totales_completados=new ArrayList<ValoresHistorial>();
        groupList = new ArrayList<>();
        if(!complen.isEmpty()) {
            for (int i = 0; i < complen.size(); i++) {
                 groupList.add(complen.get(i));
                //createCollection();
            }

             for (int i = 0; i < complen.size(); i++) {
                ArrayList<String> rutas = con.cargarRutasParaRecorridosTotales(userName, complen.get(i));
                laptopCollection.put(complen.get(i), rutas);
                ArrayList<String> rutasCompletadas = con.cargarRutasParaRecorridosCompletados(userName, complen.get(i));
                for (int j = 0; j < rutas.size(); j++) {
                    Totales_completados.add(con.obtenerRetosValoresRutaRecorrido(userName, complen.get(i), rutas.get(j)));
                 }
            }
            for (int i = 0; i < Totales_completados.size(); i++) {
                Toast.makeText(HistorialUsuario.this, Integer.toString(i) + " Totales : " + Integer.toString(Totales_completados.get(i).getTotales()) + " Completados: " + Integer.toString(Totales_completados.get(i).getCompletados()) + " Porcentaje: " + Integer.toString(Totales_completados.get(i).getPorcentaje()) + "%", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(HistorialUsuario.this,"No has participado en ningún recorrido todavia.",Toast.LENGTH_LONG).show();
        }
        /*
        groupList.add("Recorrido1");
        groupList.add("Recorrido2");
        groupList.add("Recorrido3");
        groupList.add("Recorrido4");
        groupList.add("Recorrido5");
        groupList.add("Recorrido6");
        */
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] hpModels = { "Ruta1", "Ruta2",
                "Ruta3" };
        String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
        String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
                "ThinkPad X Series", "Ideapad Z Series" };
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };

        laptopCollection = new LinkedHashMap<>();

        for (String recorridos : groupList) {
            switch (recorridos) {
                case "Recorrido1":
                    loadChild(hpModels);
                    break;
                case "Recorrido2":
                    loadChild(dellModels);
                    break;
                case "Sony":
                    loadChild(sonyModels);
                    break;
                case "HCL":
                    loadChild(hclModels);
                    break;
                case "Samsung":
                    loadChild(samsungModels);
                    break;
                default:
                    loadChild(lenovoModels);
                    break;
            }

            laptopCollection.put(recorridos, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<>();
        Collections.addAll(childList, laptopModels);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
