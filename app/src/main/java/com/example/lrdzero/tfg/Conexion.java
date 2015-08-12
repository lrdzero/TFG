package com.example.lrdzero.tfg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by lrdzero on 11/08/2015.
 */
public class Conexion {

    private Socket sk;
    private String ip="192.168.1.33";
    private int port=7;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream objectOutput;
    private ObjectInputStream objectInput;

    public Conexion(){}

    private void conectar(){
        try {

            sk = new Socket(ip, port);
            in = new DataInputStream(sk.getInputStream());
            out = new DataOutputStream(sk.getOutputStream());
            objectOutput = new ObjectOutputStream(sk.getOutputStream());
            objectInput = new ObjectInputStream(sk.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void cerrar(){
        try{
            sk.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public int registrarse(ArrayList<String> datos){
        int respuesta=-1;
        try{
            conectar();
            out.writeUTF("registrar");
            if(in.readUTF().equals("continua")){
                objectOutput.writeObject(datos);
                respuesta = in.read();
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }
    public int IniciarSesion(String nombre, String contrasenia){
        int respuesta=-2;
        ArrayList<String> my = new ArrayList<String>();
        my.add(nombre);
        my.add(contrasenia);

        try{
            conectar();

            out.writeUTF("INICIO");
            if(in.readUTF().equals("continua")) {
                objectOutput.writeObject(my);
                respuesta = in.read();

            }

            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return respuesta;
    }


    public ArrayList<DatosRyR> cargaDeRecorridos(){
        ArrayList<DatosRyR> dt = new ArrayList<DatosRyR>();
        try{

            conectar();


            out.writeUTF("ListarDatosRutas");

            if(in.readUTF().equals("continuar")){

                int tama= in.read();
                for(int i=0;i<tama;i++) {
                    String Nombre = in.readUTF();
                    String num = in.readUTF();
                    String breveD = in.readUTF();
                    String autor = in.readUTF();
                    String Descrip = in.readUTF();
                    dt.add(new DatosRyR(Nombre, num, breveD, autor, R.drawable.f0907, Descrip));
                }
            }
            else{


            }
            cerrar();

        }catch(IOException e){
            e.printStackTrace();
        }
        return dt;
    }
}
