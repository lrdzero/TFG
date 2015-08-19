package com.example.lrdzero.tfg;

import android.util.Log;

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
    private Object object;
    private ArrayList<String> listReception=new ArrayList<String>();

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
            listReception.clear();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int hacerconexionGenerica(String tipo,ArrayList<String>data){
        int respuesta=-1;
        try{
            conectar();
            out.writeUTF(tipo);
            if(in.readUTF().equals("continua")){
                objectOutput.writeObject(data);
                respuesta = Integer.valueOf(in.readUTF());
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

   public ArrayList<DatosRyR> cargaDeRetos(String name){
       ArrayList<DatosRyR> respuesta = new ArrayList<DatosRyR>();

       try{
           conectar();
           out.writeUTF("cargarRetos");
           if(in.readUTF().equals("continua")){
               out.writeUTF(name);
               try{
               object = objectInput.readObject();
               listReception= (ArrayList<String>) object;
                } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                    e1.printStackTrace();
                 }

               for(int i=0;i<listReception.size();i=i+4){
                   String nombre =listReception.get(i);
                   String brevDes =listReception.get(i+1);
                   String desp =listReception.get(i+2);
                   String num = listReception.get(i+3);
                   respuesta.add(new DatosRyR(nombre,num,brevDes,"Alguien",R.drawable.premiodefecto,desp));

               }
           }
           cerrar();
       }catch (IOException e){
           e.printStackTrace();
       }

       return respuesta;
   }
   public int nuevaRuta(String nombre,String nombre2){
       int respuesta=-1;
       try{
           conectar();
           out.writeUTF("crearNuevaRuta");
           if(in.readUTF().equals("continua")){
               out.writeUTF(nombre);
               out.writeUTF(nombre2);
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
                respuesta = Integer.valueOf(in.readUTF());
                Log.e("TTAAAAA", Integer.toString(respuesta));
            }

            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return respuesta;
    }


    public ArrayList<DatosRyR> cargaDeRutas(String name){
        ArrayList<DatosRyR> dt = new ArrayList<DatosRyR>();
        DatosRyR nm = new DatosRyR();
        try{

            conectar();


            out.writeUTF("ListarDatosRutas");

            if(in.readUTF().equals("continua")){
                out.writeUTF(name);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                for(int i=0;i<listReception.size();i++){
                    String nombre=listReception.get(i);
                    dt.add(new DatosRyR(nombre,"","","",R.drawable.recorridodefecto,""));
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
    public ArrayList<DatosRyR> cargaDeRecorridos(){
        ArrayList<DatosRyR> dt = new ArrayList<DatosRyR>();
        DatosRyR nm = new DatosRyR();
        try{
            conectar();
            out.writeUTF("cargarRecorridos");
            if(in.readUTF().equals("continua")){
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                for(int i=0;i<listReception.size();i=i+4){
                    String nombre=listReception.get(i);
                    String Tipo = listReception.get(i + 1);
                    String otro = listReception.get(i+2);
                    String descripcion=listReception.get(i+3);


                    dt.add(new DatosRyR(nombre,Tipo,descripcion,otro,R.drawable.recorridodefecto,""));
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return dt;
    }

    public DatosRyR buscarDatosRetoDeportivo(String name){
        DatosRyR nuvo = new DatosRyR();
        try{
            conectar();
            out.writeUTF("buscarReto");
            if(in.readUTF().equals("continua")){
                out.writeUTF(name);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                nuvo.setName(listReception.get(0));
                nuvo.setDescription(listReception.get(1));
                nuvo.setNumber(listReception.get(2));
                nuvo.setLargeDescription(listReception.get(3));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return nuvo;
    }
    public DatosRyR buscarDatosRetoCultural(String name){
        DatosRyR nuvo = new DatosRyR();
        try{
            conectar();
            out.writeUTF("buscarRetoCultural");
            if(in.readUTF().equals("continua")){
                out.writeUTF(name);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                nuvo.setName(listReception.get(0));
                nuvo.setDescription(listReception.get(1));
                nuvo.setLargeDescription(listReception.get(2));
                nuvo.setOther(listReception.get(3));
                nuvo.setNumber(listReception.get(4));
                nuvo.setAdic(listReception.get(5));
                nuvo.setAux(listReception.get(6));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return nuvo;
    }

    public DatosRyR buscarDatosRuta(String nombre){
        DatosRyR n = new DatosRyR();
        try{
            conectar();
            out.writeUTF("buscarRuta");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombre);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                n.setName(listReception.get(0));
                n.setDescription(listReception.get(1));
                n.setNumber(listReception.get(2));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }


        return n;
    }


}
