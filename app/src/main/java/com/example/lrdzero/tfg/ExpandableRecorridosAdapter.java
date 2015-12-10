package com.example.lrdzero.tfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableRecorridosAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<String>> RecorridoRutas;
    private List<String> recorridos;
    private ArrayList<ValoresHistorial> valores;
    private int index=0;

    public ExpandableRecorridosAdapter(Activity context, List<String> recs,
                                 Map<String, List<String>> relations, ArrayList<ValoresHistorial> v) {
        this.context = context;
        this.RecorridoRutas = relations;
        this.recorridos = recs;
        this.valores=v;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return RecorridoRutas.get(recorridos.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rutaexpandable, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.nomruta);

        TextView percent = (TextView) convertView.findViewById(R.id.completado);
        TextView retos = (TextView) convertView.findViewById(R.id.puntuacion);

        item.setText(laptop);
        retos.setText(String.valueOf(valores.get(index).getCompletados())+"/"+valores.get(index).getTotales());
        percent.setText(String.valueOf(valores.get(index).getPorcentaje())+"%");
        index++;
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return RecorridoRutas.get(recorridos.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return recorridos.get(groupPosition);
    }

    public int getGroupCount() {
        return recorridos.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.nomruta);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
