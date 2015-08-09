package com.example.lrdzero.tfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lrdzero on 07/08/2015.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private final List<Items> mItems = new ArrayList<Items>();
    private Set<View> listatotal = new HashSet<View>();
    private ImageView image;
    private TextView text;

    public MyAdapter(Context c){
        context= c;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Items Item = (Items) getItem(position);
        LayoutInflater inflater = null;
        if(convertView == null){
            // rest of the code
            inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_lista_horizontal_mochila, null);
            //convertView = inflater.inflate(R.layout.options, null);
        }
        listatotal.add(convertView);
        text = (TextView)convertView.findViewById(R.id.ItemText);
        image=(ImageView)convertView.findViewById(R.id.ItemImage);

        text.setText(Item.getName());
        image.setImageResource(Item.getImage());




        return convertView;
    }

    public void addItem(Items n){
        mItems.add(n);
    }
}
