package com.example.lrdzero.tfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class creador_recompensa extends AppCompatActivity {
    private String nombreReto;
    private String descripcionRecompensa;
    private String nombreRecompensa;
    private ImageView porDefecto;
    private ImageView camera;
    private ImageView fondo;
    private boolean drawa;
    private ArrayList<String> envio = new ArrayList<String>();
    private ArrayList<Items> dt = new ArrayList<Items>();
    private MediaPlayer error;
    private Uri fileUriLugar;
    private Uri fileUri;
    private int theImage;
    private static String nombreArchivo;
    private Conexion con;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int PICK_IMAGE=200;
    private static final int UPDATE_POSXY=300;
    private boolean seleccionado = false;
    private Button crear;
    private String nombreDrawa="";
    private static int POSICIONAMIENTO=234;
    private boolean activado=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_recompensa);

        con= new Conexion();
        porDefecto=(ImageView) findViewById(R.id.imagenItemCultural);
        camera=(ImageView) findViewById(R.id.imagenCameraCultural);
        fondo=(ImageView) findViewById(R.id.imageView6);
        error=MediaPlayer.create(this,R.raw.alert);
        crear=(Button) findViewById(R.id.botonCrear);

        nombreRecompensa=getIntent().getExtras().getString("nombreRecompensa");
        nombreReto=getIntent().getExtras().getString("nombrereto");
        descripcionRecompensa=getIntent().getExtras().getString("descripcion");

        fileUriLugar = Uri.parse(getIntent().getExtras().getString("uri"));
        nombreArchivo=getIntent().getExtras().getString("nombrefile");
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        theImage=0;
        porDefecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarElecciones(nombreReto,descripcionRecompensa).show();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!seleccionado) {
                    Log.e("CAMERA", "CAMARA");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });
        fondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent n = new Intent(creador_recompensa.this, FondoRecompensa.class);
                n.putExtra("fondo",fileUriLugar.toString() );
                n.putExtra("nombreReto",nombreReto);

                n.putExtra("imagenTipo",drawa);
                if (drawa){
                    n.putExtra("reconD",theImage);
                    n.putExtra("nombreRecom",nombreDrawa);
                }
                else{
                    n.putExtra("reconS",fileUri.toString());
                    n.putExtra("nombreRecom",nombreRecompensa);
                }
                startActivityForResult(n, POSICIONAMIENTO);
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activado) {
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
                else{
                    Toast.makeText(creador_recompensa.this,"Aún debe seleccionar la posición del objeto.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private AlertDialog.Builder mostrarElecciones(String nombreReto,String descripcionRecompensa){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!seleccionado) {
            if (!nombreReto.matches("")) {
                if (dt.isEmpty()) {
                    dt.add(new Items("zapatillas", R.drawable.zapatillas, nombreReto, descripcionRecompensa));
                    dt.add(new Items("pesas", R.drawable.pesas, nombreReto, descripcionRecompensa));
                    dt.add(new Items("libro", R.drawable.libro, nombreReto, descripcionRecompensa));
                    dt.add(new Items("brújula", R.drawable.brujula, nombreReto, descripcionRecompensa));
                }
                final PlaceList adapter = new PlaceList();

                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            } else {
                error.start();
                builder.setTitle("Error").setMessage("Introduzca un nombre para el reto").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        }
        else{
            builder.setTitle("Error").setMessage("Ya has obtenido una fotografia.").setPositiveButton("0k", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        return builder;

    }
    public class PlaceList extends ArrayAdapter<Items> {
        //Items currentData;
        public PlaceList() {
            super(creador_recompensa.this, R.layout.activity_lista_horizontal_mochila, dt);
        }

        public View getView(int position,View convertView, ViewGroup parent){


            View intenView=convertView;
            if(intenView == null){
                intenView = getLayoutInflater().inflate(R.layout.activity_lista_horizontal_mochila,parent,false);
            }

            final ImageView img = (ImageView) intenView.findViewById(R.id.ItemImage);
            TextView txt1 = (TextView) intenView.findViewById(R.id.ItemText);
            //final CheckBox check = (CheckBox) intenView.findViewById(R.id.CheckItem);

            final Items currentData = dt.get(position);

            img.setImageResource(currentData.getImage());
            txt1.setText(currentData.getName());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(creador_recompensa.this);
                    builder.setTitle("¿Confimar?").setMessage("Desea asignar este Item al reto "+currentData.getName())
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(CreadorRetoDeportivo.this, Integer.toString(R.drawable.aniadiritem), Toast.LENGTH_LONG).show();

                                    envio.add(currentData.getName());
                                    nombreDrawa=currentData.getName();
                                    envio.add(currentData.getNombreReto());
                                    envio.add(Integer.toString(currentData.getImage()));
                                    theImage=currentData.getImage();
                                    envio.add(fileUriLugar.toString());
                                    envio.add(currentData.getDescripcionRecompensa());
                                    envio.add("1");
                                    con.hacerconexionGenerica("insertPremio", envio);

                                    envio.clear();
                                    seleccionado = true;
                                    drawa=true;

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    builder.show();
                }
            });



            return intenView;
        }

       // public String getNombre(){return currentData.getName();}

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView iv = (ImageView)findViewById(R.id.foto);
        if(requestCode == POSICIONAMIENTO){
            if(resultCode==RESULT_OK){
                activado=true;
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(creador_recompensa.this,"Se ha producido un error",Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                envio.add(nombreRecompensa);
                envio.add(nombreReto);
                envio.add(fileUri.toString());
                envio.add(fileUriLugar.toString());
                envio.add(descripcionRecompensa);
                envio.add("2");
                con.hacerconexionGenerica("insertPremio", envio);

                envio.clear();
                seleccionado = true;
                drawa=false;
                finish();
                startActivity(getIntent());
            } else if (resultCode != RESULT_CANCELED){

                Toast.makeText(this, "Error al capturar la imagen", Toast.LENGTH_LONG).show();
                // Image capture failed, advise user
            }
        }
        else if(requestCode == PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //Log.i("Camara", String.valueOf(data));


                //iv.setImageURI(data.getData());


            } else if (resultCode != RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(this,"Error al recoger la imagen", Toast.LENGTH_LONG).show();

            }
        }
        else if(requestCode==UPDATE_POSXY){
            if(resultCode==RESULT_OK){
                //UPDATEAR EL RETO
            }
        }

    }
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "RutasGranada");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Camara", "failed to create directory");
                return null;
            }
        }



        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    nombreArchivo+".png");

        }else {
            return null;
        }

        return mediaFile;
    }
    private Drawable reduceImagen(Uri unUri,int tam1,int tam2){
        Drawable yourDrawable;
        try {
            InputStream inputStream = getContentResolver().openInputStream(unUri);
            yourDrawable = Drawable.createFromStream(inputStream, unUri.toString());
        } catch (FileNotFoundException e) {
            yourDrawable = getResources().getDrawable(R.drawable.pesas);
        }
        Bitmap bitmap = ((BitmapDrawable) yourDrawable).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, tam1, tam2, true));
        yourDrawable.invalidateSelf();
        return d;
    }
}
