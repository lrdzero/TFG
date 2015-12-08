package com.example.lrdzero.tfg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static com.example.lrdzero.tfg.R.id.icamara;


public class ProfileActivity extends Activity {
    ArrayList<Integer> Discapacidades = new ArrayList<>();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_AUDIO=123;
    private static final int PICK_IMAGE=200;
    private Uri fileUri;
    private String Name;
    private Conexion con;
    private DatosRyR datosUser =new DatosRyR();
    private ArrayList<Integer> pref = new ArrayList<Integer>();
    private ImageView editName,editEdad,editContrasenia;
    private TextView nom,ed,contr;
    private String musicaSelected="";

    protected void onDestroy(){
        super.onDestroy();
    }
    protected void onResume(){
        super.onResume();
    }
    protected void onPause(){
        super.onPause();
    }
    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Button discButton = (Button)findViewById(R.id.discapacidades);
        Button cerrarsesion = (Button)findViewById(R.id.button);
        Button discHerr = (Button)findViewById(R.id.button2);
        Button preferencias =(Button)findViewById(R.id.button3);
        ImageView musica = (ImageView) findViewById(R.id.notaMusical);


        con = new Conexion();

        editName = (ImageView) findViewById(R.id.imageView9);
        editEdad= (ImageView) findViewById(R.id.imageView10);
        editContrasenia = (ImageView) findViewById(R.id.imageView11);

        nom =(TextView)findViewById(R.id.nombre);
        ed =(TextView)findViewById(R.id.edad);
        contr =(TextView)findViewById(R.id.passwd);


        Name = getIntent().getExtras().getString("NombreUser");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        ImageView estadisticas = (ImageView)findViewById(R.id.imageestadisticas);
        Log.i("est", estadisticas.toString());

        datosUser=con.buscarUsuario(Name);

        nom.setText(datosUser.getName());
        ed.setText(datosUser.getNumber());
        contr.setText(datosUser.getDescription());

        final ImageView foto = (ImageView)findViewById(R.id.foto);



        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                // Le metemos el resultado que queremos mandar a la
                // actividad principal.
                i.putExtra("RESULTADO", 1);
                i.putExtra("musica",musicaSelected);
                // Establecemos el resultado, y volvemos a la actividad
                // principal. La variable que introducimos en primer lugar
                // "RESULT_OK" es de la propia actividad, no tenemos que
                // declararla nosotros.
                setResult(RESULT_OK, i);
                finish();
            }
        });
        discHerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(ProfileActivity.this, HerramientaNuevaRuta.class);
                n.putExtra("creador",Name);
                startActivity(n);
            }
        });
        musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent music = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(music, MEDIA_TYPE_AUDIO);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        preferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();



                // Set the dialog title
                //Log.i("title", "prog");
                datosUser=con.buscarUsuario(Name);
                if(datosUser.getPreferenciaUser1()!=null&&datosUser.getPreferenciaUser2()!=null){
                    String pref1,pref2;
                    pref1="";
                    pref2="";
                    if(datosUser.getPreferenciaUser1().equals("1")){
                        pref1="Problemas Cardiovasculares.";
                    }
                    if(datosUser.getPreferenciaUser2().equals("1")){
                        pref2="Problemas de movilidad.";
                    }
                    builder.setTitle("Advertencia")
                            .setMessage("Sus preferencias son:\n\t" + pref1 + "\n\t" + pref2+"\n¿Desea modificarlos?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    generarBuilderParaPreferencias("Preferencias","",R.array.recomendadoUser).show();
                                    // User clicked OK, so save the mSelectedItems results somewhere
                                    // or return them to the component that opened the dialog

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                }


                builder.show();

            }
        });
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                alertDialog.setTitle("Cambio de nombre");
                alertDialog.setMessage("Introduce nuevo nombre");

                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                //alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String nuevoNombre = input.getText().toString();
                                String resul=con.updateUsuario(Name,nuevoNombre,"nombre",null);
                                if(!resul.equals("error")) {
                                    nom.setText(resul);
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });
        editEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                alertDialog.setTitle("Cambio de edad");
                alertDialog.setMessage("Introduce nueva edad");

                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                //alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String nuevaEdad = input.getText().toString();
                                String resul=con.updateUsuario(Name,nuevaEdad, "edad",null);
                                if(!resul.equals("error")) {
                                    ed.setText(resul);
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });
        editContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                alertDialog.setTitle("Cambio de correo");
                alertDialog.setMessage("Introduce nuevo correo");

                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                //alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String nuevoContrasenia = input.getText().toString();
                               String resul=con.updateUsuario(Name,nuevoContrasenia,"correo",null);
                                if(!resul.equals("error")) {
                                    contr.setText(resul);
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });


        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder2.setView(inflater.inflate(R.layout.capturafoto, null));
        builder2.setTitle("Elija una opcion")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        final AlertDialog dialog = builder2.create();





        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogI) {
                    ImageView camara= (ImageView)dialog.findViewById(icamara);
                    ImageView galeria= (ImageView)dialog.findViewById(R.id.igallery);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    foto.setImageURI(fileUri);
                    camara.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                        }
                    });

                    galeria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // Log.i("Camara", String.valueOf(fileUri));
                            //Log.i("Camara", MediaStore.EXTRA_OUTPUT);
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Selecciona una foto"), PICK_IMAGE);
                            dialog.cancel();

                        }
                    });
                   // ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);

            }
        });

        //dialog.show();



        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                dialog.show();

            }
        });
        // start the image capture Intent

        estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistorialUsuario.class);
                i.putExtra("userName",Name);
                Log.i("est","intent craedo");
                startActivity(i);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    @TargetApi(Build.VERSION_CODES.FROYO)
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
                    "fotoperfil.jpg");

        }else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView iv = (ImageView)findViewById(R.id.foto);
        if (requestCode== MEDIA_TYPE_AUDIO){
            ArrayList<String> musc = new ArrayList<>();
            Uri path = data.getData();
            Toast.makeText(ProfileActivity.this,"Paht "+path.toString(),Toast.LENGTH_LONG).show();
            musc.add(path.toString());
            musc.add(Name);
            int r=con.hacerconexionGenerica("musicaUsuario", musc);
            if(r==0){
                Toast.makeText(ProfileActivity.this,"Error en inserción de musica.",Toast.LENGTH_SHORT).show();
            }
            else {
                musc.clear();
                musicaSelected=path.toString();
            }
        }
        else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //Log.i("Camara", String.valueOf(data));
               //Toast.makeText(this, "Image saved to:\n" +
                    //  fileUri, Toast.LENGTH_LONG).show();

                iv.setImageURI(fileUri);

                finish();
                startActivity(getIntent());
            } else if (resultCode != RESULT_CANCELED){
                Toast.makeText(this,"Error al capturar la imagen", Toast.LENGTH_LONG).show();
                // Image capture failed, advise user
            }
        }
        else if(requestCode == PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //Log.i("Camara", String.valueOf(data));


                 iv.setImageURI(data.getData());


            } else if (resultCode != RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(this,"Error al recoger la imagen", Toast.LENGTH_LONG).show();

            }

        }

    }


    private AlertDialog.Builder generarBuilderParaPreferencias(String tittle,String message, int list){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> aux = new ArrayList<>();
        builder.setTitle("Preferencias")
                .setMultiChoiceItems(R.array.recomendadoUser, null,
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
                        pref = aux;

                        ArrayList<Integer> datosTrueEnBD =new ArrayList<>();

                        datosTrueEnBD.add(0);
                        datosTrueEnBD.add(0);

                        for(int i=0;i<pref.size();i++){
                            datosTrueEnBD.set(pref.get(i),1);
                        }
                        con.updateUsuario(Name,"","preferencias",datosTrueEnBD);
                        //con.updateUsuario();
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

        return builder;
    }


}
