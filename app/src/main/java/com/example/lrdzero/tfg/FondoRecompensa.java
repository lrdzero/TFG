package com.example.lrdzero.tfg;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FondoRecompensa extends AppCompatActivity {
    private ImageView objeto;
    private boolean imagenTipo;
    private int drawable=0;
    private String uri;
    private String fondoI;
    private Conexion con;
    private String nombreRecompensa;
    private String nombreReto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fondo_recompensa);

        ImageView fondo=(ImageView)findViewById(R.id.fondo);
        objeto =(ImageView)findViewById(R.id.objeto);
        RelativeLayout r =(RelativeLayout)findViewById(R.id.rfondo);
        con=new Conexion();

        fondoI=getIntent().getExtras().getString("fondo");
        imagenTipo=getIntent().getExtras().getBoolean("imagenTipo");
        nombreReto=getIntent().getExtras().getString("nombreReto");

        Uri fondoLugar = Uri.parse(fondoI);
        fondo.setImageURI(fondoLugar);
        if(imagenTipo){
            drawable=getIntent().getExtras().getInt("reconD");
            nombreRecompensa=getIntent().getExtras().getString("nombreRecom");
            objeto.setImageResource(drawable);
        }
        else{
            uri = getIntent().getExtras().getString("reconS");
            nombreRecompensa=getIntent().getExtras().getString("nombreRecom");
            Uri object = Uri.parse(uri);
            objeto.setImageURI(object);
        }



        r.setOnDragListener(new MyDragListener());


        View.OnTouchListener ot = new View.OnTouchListener(){
            //private final class MyTouchListener implements OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        };

        objeto.setOnTouchListener(ot);
    }

    class MyDragListener implements View.OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View view = (View) event.getLocalState();
            view.setVisibility(View.VISIBLE);
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    //v.setBackgroundDrawable(normalShape);

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    Toast.makeText(FondoRecompensa.this, "X:"+ event.getX()+" e y:"+ event.getY(), Toast.LENGTH_LONG).show();
                    objeto.setX(event.getX());
                    objeto.setY(event.getY());
                    Toast.makeText(FondoRecompensa.this, "X:"+ Float.toString(objeto.getX())+" e y:"+ Float.toString(objeto.getY()), Toast.LENGTH_LONG).show();
                    con.updateRecom(nombreRecompensa,nombreReto,objeto.getX(),objeto.getY());



                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
