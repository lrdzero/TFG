package com.example.lrdzero.tfg;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        Button boton =(Button) findViewById(R.id.botonPosicionado);
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

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.updateRecom(nombreRecompensa,nombreReto,objeto.getX(),objeto.getY());
                Intent i = getIntent();
                // Le metemos el resultado que queremos mandar a la
                // actividad principal.
                i.putExtra("RESULTADO", 1);
                // Establecemos el resultado, y volvemos a la actividad
                // principal. La variable que introducimos en primer lugar
                // "RESULT_OK" es de la propia actividad, no tenemos que
                // declararla nosotros.
                setResult(RESULT_OK, i);
                finish();
            }
        });

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
                    //Toast.makeText(FondoRecompensa.this, "X:"+ event.getX()+" e y:"+ event.getY(), Toast.LENGTH_LONG).show();
                    objeto.setX(event.getX());
                    objeto.setY(event.getY());
                   // Toast.makeText(FondoRecompensa.this, "X:"+ Float.toString(objeto.getX())+" e y:"+ Float.toString(objeto.getY()), Toast.LENGTH_LONG).show();




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
