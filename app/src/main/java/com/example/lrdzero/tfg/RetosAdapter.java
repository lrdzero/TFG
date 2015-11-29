package com.example.lrdzero.tfg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class RetosAdapter extends BaseAdapter {
    private final List<Reto> mItems = new ArrayList<Reto>();
    private Context context;
    private int puntos;
    private Ruta r;


    public RetosAdapter(Context c, Ruta rut) {
        context = c;
        r=rut;

    }

    public void addReto(Reto r) {
        mItems.add(r);
    }


    public void clear() {

        mItems.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItems.size();
    }

    @Override
    public Object getItem(int arg) {
        // TODO Auto-generated method stub
        return mItems.get(arg);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int arg, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        final double latitude;
        final double longitude;
        final boolean pulsado = false;
        //final GPSAlarmService service=new GPSAlarmService();


        // TODO - Inflate the View for this ToDoItem
        // from todo_item.xml
        LayoutInflater inflater = null;
        if (convertView == null) {
            // rest of the code
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.retoeditor, null);
            //convertView = inflater.inflate(R.layout.options, null);
        }

        final TextView nombre = (TextView) convertView.findViewById(R.id.nomreto);
        nombre.setText(mItems.get(arg).getNombre());
        final SeekBar barra = (SeekBar) convertView.findViewById(R.id.seekBar);
        barra.setMax(r.getPoints().size() - 1);
        barra.setProgress(mItems.get(arg).getMarkerLocation());

        ;
        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("prueba", String.valueOf(r.getPoints().size()));


                if(r.getPoints().size()>0)
                 mItems.get(arg).setLocation(r.getPoints().get(barra.getProgress()));



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Return the View you just created
        return convertView;


    }
}
