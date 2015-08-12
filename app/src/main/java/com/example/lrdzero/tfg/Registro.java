package com.example.lrdzero.tfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

        nombre=(EditText)findViewById(R.id.nombre);
        usuario=(EditText)findViewById(R.id.usuario);
        contrasenia=(EditText)findViewById(R.id.contraseña);
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
                        int  response=con.registrarse(envio);

                        if(response==1){
                            finish();
                        }
                        else{
                            Toast.makeText(Registro.this,"Error en el servidor",Toast.LENGTH_LONG).show();
                        }
                    }
                break;
        }
    }


}
