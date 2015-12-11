package com.example.lrdzero.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lrdzero.datos.Conexion;

import java.util.ArrayList;


public class Registro extends Activity implements View.OnClickListener{
    private Conexion con=new Conexion();
    private EditText nombre,usuario,contrasenia,repcontra,correo,edad;
    private RadioGroup group;
    private Button registrar;
    private RadioButton hombre, mujer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_ThemeOverlay_AppCompat_Dark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        nombre=(EditText)findViewById(R.id.nombre);
        usuario=(EditText)findViewById(R.id.usuario);
        contrasenia=(EditText)findViewById(R.id.contrasenia);
        repcontra=(EditText)findViewById(R.id.repetirContr);
        correo=(EditText)findViewById(R.id.textoCorreo);
        edad=(EditText)findViewById(R.id.edad);

        hombre=(RadioButton) findViewById(R.id.radioButton);
        mujer=(RadioButton)findViewById(R.id.radioButton2);

        registrar=(Button) findViewById(R.id.botonConfirn1);

        group =(RadioGroup) findViewById(R.id.grupoSex);


        registrar.setOnClickListener(this);

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.botonConfirn1:
                ArrayList<String> envio =new ArrayList<>();

                    if(nombre.getText().toString().matches("")||usuario.getText().toString().matches("")||contrasenia.getText().toString().matches("")||repcontra.getText().toString().matches("")||correo.getText().toString().matches("")||edad.getText().toString().matches("")){
                        Toast.makeText(Registro.this,"Hay campos vacios",Toast.LENGTH_LONG).show();
                    }
                    else if(!contrasenia.getText().toString().equals(repcontra.getText().toString())){
                        Toast.makeText(Registro.this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                    }
                    else if(hombre.isChecked()==false&&mujer.isChecked()==false){
                        Toast.makeText(Registro.this,"No ha seleccionado Sexo.",Toast.LENGTH_LONG).show();
                    }
                     else {

                        envio.add(nombre.getText().toString());
                        envio.add(usuario.getText().toString());
                        envio.add(contrasenia.getText().toString());
                        envio.add(correo.getText().toString());
                        envio.add(edad.getText().toString());
                        if(hombre.isChecked()==true){
                            envio.add("H");
                        }
                        else if(mujer.isChecked()==true){
                            envio.add("M");
                        }
                        int  response=con.hacerconexionGenerica("registrarse",envio);

                        if(response==1){
                            Toast.makeText(Registro.this,"No se ha podido conectar",Toast.LENGTH_LONG).show();

                        }
                        else if(response==0){
                            Toast.makeText(Registro.this,"Error en el servidor o usuario ya existente",Toast.LENGTH_LONG).show();
                        }
                        else if(response==-1){
                            Toast.makeText(Registro.this,"Registrado correctamente",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                break;
        }
    }


}
