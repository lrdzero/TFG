package com.example.lrdzero.data;

import android.net.Uri;
import android.util.Log;

import com.example.lrdzero.activities.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase encargada de la conexión con el servidor.
 */
public class Conexion {
    //private int ent;
    private Socket sk;
    private String ip="217.216.101.222";
    private int port=7;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream objectOutput;
    private ObjectInputStream objectInput;
    private Object object;
    private ArrayList<String> listReception=new ArrayList<>();

    /**
     * Constructor por defecto.
     */
    public Conexion(){}

    /**
     * Función para conectar con el servidor por Socket
     */
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

    /**
     * Función para cerrar la conexión con el servidor.
     */
    private void cerrar(){
        try{
            sk.close();
            listReception.clear();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función para solicitar modificación de recompensa.
     * @param nombreRecomp
     * @param nombreReto
     * @param x
     * @param y
     */
    public void updateRecom(String nombreRecomp,String nombreReto, float x,float y){
        try{
            conectar();
            out.writeUTF("updateRecompensa");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRecomp);
                out.writeUTF(nombreReto);
                out.writeUTF(Float.toString(x));
                out.writeUTF(Float.toString(y));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función que solicitar modificación del recorrido.
     * @param tipo
     * @param data
     * @param nombreRecorrido
     * @return respuesta
     */
    public int updateRecorridoPreferencias(String tipo,ArrayList<Integer> data,String nombreRecorrido){
        int respuesta=-1;
        try{
            conectar();
            out.writeUTF(tipo);
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRecorrido);
                objectOutput.writeObject(data);
                respuesta = Integer.valueOf(in.readUTF());
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función que solicita carga de posición de los retos.
     * @param name
     * @return respuesta
     */
    public ArrayList<String> cargarPositonRetos(String name){
        ArrayList<String> respuesta=new ArrayList<>();
        try{
            conectar();
            out.writeUTF("cargarPositionRetos");
            if(in.readUTF().equals("continua")) {
                out.writeUTF(name);
                try {
                    object = objectInput.readObject();
                    listReception = (ArrayList<String>) object;
                    for(int i=0;i<listReception.size();i++){
                        respuesta.add(listReception.get(i));
                    }

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return respuesta;
    }

    /**
     * Función que solicita modificación de posición de retos.
     * @param m
     * @param lat
     * @param log
     */
    public void updateRetoPos(String m,double lat,double log){
        try{
            conectar();
            out.writeUTF("updateRetoPost");
            if(in.readUTF().equals("continua")){
                out.writeUTF(m);
                out.writeUTF(Double.toString(lat));
                out.writeUTF(Double.toString(log));
               int result= in.readInt();
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función para llevar a cabo una solicitud de tipo generica.
     * @param tipo
     * @param data
     * @return respuesta
     */
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

    /**
     * Función que solicita la comprobación de un nombre para la tabla recorrido
     * @param nombreRecorrido
     * @return resultado
     */
    public boolean existeRecorrido(String nombreRecorrido){
        boolean resultado=false;
        try{
            conectar();
            out.writeUTF("comprobarRecorrido");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRecorrido);
                resultado=in.readBoolean();
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Función que solicita la comprobación de un nombre en la tabla ruta.
     * @param nombreRuta
     * @return
     */
    public boolean existeRuta(String nombreRuta){
        boolean resultado=false;
        try{
            conectar();
            out.writeUTF("comprobarRuta");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRuta);
                resultado=in.readBoolean();
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }

        return resultado;
    }

    /**
     * Función que solicita la música para un usuario
     * @param usuario
     * @return uri
     */
    public String obtenerMusicaUsuario(String usuario){
        String uri=null;
        try{
            conectar();
            out.writeUTF("buscarMusica");
            if(in.readUTF().equals("continua")){
                out.writeUTF(usuario);
                uri=in.readUTF();
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * Función para cargar retos de una ruta.
     * @param name
     * @return respuesta.
     */
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
               Log.e("CARGA DE RETOS", Integer.toString(listReception.size()));
               for(int i=0;i<listReception.size();i=i+6){

                   String nombre =listReception.get(i);
                   Log.e("LISRECEPTION: ",listReception.get(i));

                   String brevDes =listReception.get(i+1);
                   String desp =listReception.get(i+2);
                   String num = listReception.get(i+3);
                   String foto =listReception.get(i+4);
                   String position=listReception.get(i+5);

                   Log.e("CARGA DE URI:",foto);
                   Uri una = Uri.parse(foto);
                   DatosRyR nuevo =new DatosRyR(nombre,num,brevDes,"Alguien", R.drawable.premiodefecto,desp,una);
                   nuevo.setPosition(Integer.valueOf(position));

                   respuesta.add(nuevo);




               }
           }
           cerrar();
       }catch (IOException e){
           e.printStackTrace();
       }

       return respuesta;
   }

    /**
     * Función que solicita la creación de una nueva ruta.
     * @param nombre
     * @param descripcion
     * @param historia
     * @param historiaFinal
     * @return respuesta
     */
   public int nuevaRuta(String nombre,String descripcion,String historia,String historiaFinal){
       int respuesta=-1;
       try{
           conectar();
           out.writeUTF("crearNuevaRuta");
           if(in.readUTF().equals("continua")){
               out.writeUTF(nombre);
               out.writeUTF(descripcion);
               out.writeUTF(historia);
               out.writeUTF(historiaFinal);
           }
           cerrar();
       }catch (IOException e){
           e.printStackTrace();
       }
       return respuesta;
   }

    /**
     * Función para llevar a cabo el inicio de sesión
     * @param nombre
     * @param contrasenia
     * @return respuesta
     */
    public int IniciarSesion(String nombre, String contrasenia){
        int respuesta=-2;
        ArrayList<String> my = new ArrayList<>();
        my.add(nombre);
        my.add(contrasenia);

        try{
            conectar();

            out.writeUTF("INICIO");
            if(in.readUTF().equals("continua")) {
                objectOutput.writeObject(my);
                respuesta = Integer.valueOf(in.readUTF());
                //Log.e("TTAAAAA", Integer.toString(respuesta));
            }

            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return respuesta;
    }

    /**
     * Función para enviar los tramos de una ruta.
     * @param name
     * @param obj
     * @param tamanio
     */
    public void hacerConexionJSON(String name, JSONObject obj, int tamanio){
        ArrayList<String> envio = new ArrayList<>();
        for(int i=0;i<tamanio;i++) {
            try {
                envio.add(obj.getString("Ruta"+i));
                envio.add(Double.toString(obj.getDouble("latitudO" + i)));
                envio.add(Double.toString(obj.getDouble("longitudO" + i)));
                envio.add(Double.toString(obj.getDouble("latitudF" + i)));
                envio.add(Double.toString(obj.getDouble("longitudF" + i)));
                envio.add(Integer.toString(obj.getInt("posicion" + i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        hacerconexionGenerica(name,envio);

    }

    /**
     * Función para cargar la ruta de un recorrido
     * @param name
     * @return respuesta
     */
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
                for(int i=0;i<listReception.size();i=i+3){
                    String nombre=listReception.get(i);
                    String historia=listReception.get(i+1);
                    String nRetos=listReception.get(i+2);
                    Uri n =null;
                    dt.add(new DatosRyR(nombre,nRetos,"","",R.drawable.recorridodefecto,historia,n));
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

    /**
     * Función para solicitar la carga de recorridos para un usuario.
     * @param tipo
     * @param edad
     * @param pref1
     * @param pref2
     * @param dificultad
     * @return dt
     */
    public ArrayList<DatosRyR> cargaDeRecorridosPorAdaptacion(int tipo,String edad,String pref1,String pref2,String dificultad){
        ArrayList<DatosRyR> dt = new ArrayList<DatosRyR>();
        DatosRyR nm = new DatosRyR();
        try{
            conectar();
            out.writeUTF("cargarRecorridosPorAdaptacion");
            if(in.readUTF().equals("continua")){
                try{
                    out.writeUTF(Integer.toString(tipo));
                    out.writeUTF(edad);
                    out.writeUTF(pref1);
                    out.writeUTF(pref2);
                    out.writeUTF(dificultad);
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
                    Uri m=null;


                    dt.add(new DatosRyR(nombre,Tipo,descripcion,otro,R.drawable.recorridodefecto,"",m));
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return dt;
    }

    /**
     * Función para solicitar la carga de los valores de retos completados y retos totales de una ruta para un usuario.
     * @param nombreUser
     * @param nombreRecorrido
     * @param nombreRuta
     * @return
     */
    public ValoresHistorial obtenerRetosValoresRutaRecorrido(String nombreUser,String nombreRecorrido,String nombreRuta){

        ValoresHistorial nuevo=new ValoresHistorial();
        try{
            conectar();
            out.writeUTF("obtenerRetosValoresRutaRecorrido");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreUser);
                out.writeUTF(nombreRecorrido);
                out.writeUTF(nombreRuta);
                int t=in.readInt();
                int c=in.readInt();
                nuevo=new ValoresHistorial(t,c);


            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Función para solicitar la creación de un nuevo reto completado.
     * @param nombre
     * @param nombreReto
     */
    public void insertCompletado(String nombre, String nombreReto){
        try{
            conectar();
            out.writeUTF("insertCompletado");
            if(in.readUTF().equals("continua")) {
                out.writeUTF(nombre);
                out.writeUTF(nombreReto);
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función para cargar los recorridos que crea un usuario.
     * @param tipo
     * @param usuario
     * @return dt
     */
    public ArrayList<DatosRyR> cargaDeRecorridos(int tipo,String usuario){
        ArrayList<DatosRyR> dt = new ArrayList<DatosRyR>();
        DatosRyR nm = new DatosRyR();
        try{
            conectar();
            out.writeUTF("cargarRecorridos");
            if(in.readUTF().equals("continua")){
                try{
                    out.writeUTF(Integer.toString(tipo));
                    out.writeUTF(usuario);
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
                    Uri m=null;


                    dt.add(new DatosRyR(nombre,Tipo,descripcion,otro,R.drawable.recorridodefecto,"",m));
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * Función para solicitar carga de rutas completadas para un usuario.
     * @param username
     * @param nombreReco
     * @return respuesta
     */
    public  ArrayList<String> cargarRutasParaRecorridosCompletados(String username,String nombreReco){
        ArrayList<String> respuesta = new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("cargarRutasParaRecorridosCompletados");
            if(in.readUTF().equals("continua")){
                out.writeUTF(username);
                out.writeUTF(nombreReco);
                try{
                    object = objectInput.readObject();
                    respuesta= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función para solicitar la carga de las rutas totales para un recorrido.
     * @param username
     * @param nombreReco
     * @return
     */
    public  ArrayList<String> cargarRutasParaRecorridosTotales(String username,String nombreReco){
        ArrayList<String> respuesta = new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("cargarRutasParaRecorridosTotales");
            if(in.readUTF().equals("continua")){
                out.writeUTF(username);
                out.writeUTF(nombreReco);
                try{
                    object = objectInput.readObject();
                    respuesta= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función para solicitar modificación de tabla recorren
     * @param nombreUser
     * @param nombreRuta
     */
    public void updateRecorren(String nombreUser,String nombreRuta){
        try{
            conectar();
            out.writeUTF("updateRecorren");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreUser);
                out.writeUTF(nombreRuta);
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función para solicitar inserción en la tabla recorren.
     * @param nombre
     * @param nombreRuta
     * @return i
     */
    public int insertRecorren(String nombre, String nombreRuta){
        int i=-1;
        try{
            conectar();
            out.writeUTF("insertRecorren");
            if(in.readUTF().equals("correcto")){
                out.writeUTF(nombre);
                out.writeUTF(nombreRuta);
                i=in.readInt();
            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Función para solicitar la carga de recorridos en los que el usuario ha participado.
     * @param nombreUsuario
     * @return respuesta
     */
     public ArrayList<String> cargarRecorridosParticipados(String nombreUsuario){
        ArrayList<String> respuesta=new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("cargarRecorridosCompletados");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreUsuario);
                try{
                    object = objectInput.readObject();
                    respuesta= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función que solicita la carga de un premio para un reto.
     * @param nombreReto
     * @return respuesta
     */
    public ArrayList<String> cargarPremio(String nombreReto){
        ArrayList<String> respuesta=new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("buscarPremio");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreReto);
                try{
                    object = objectInput.readObject();
                    respuesta= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función que solicita el borrado de una mochila para una ruta y usuario específicos.
     * @param usuario
     * @param nombreRecorrido
     * @param nombreRuta
     */
    public void borrarMochila(String usuario,String nombreRecorrido,String nombreRuta){
        try{
            conectar();
            out.writeUTF("borrarMochila");
            if(in.readUTF().equals("continua")){
                out.writeUTF(usuario);
                out.writeUTF(nombreRecorrido);
                out.writeUTF(nombreRuta);

            }
            cerrar();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Función para cargar la mochila de un usuario.
     * @param nombreCreador
     * @param nombreRecorrido
     * @param nombreRuta
     * @return respuesta
     */
    public ArrayList<String> cargarMochila(String nombreCreador,String nombreRecorrido,String nombreRuta){
        ArrayList<String> respuesta=new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("buscarMochila");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreCreador);
                out.writeUTF(nombreRecorrido);
                out.writeUTF(nombreRuta);

                try{
                    object = objectInput.readObject();
                    respuesta= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Función que solicita la carga de un tipo de reto deportivo
     * @param name
     * @return nuvo
     */
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

    /**
     * Función para cargar una lista de recomendaciones existente en un recorrido
     * @param nombreRecorrido
     * @return respuesta
     */
    public ArrayList<String> cargaRecomendaciones(String nombreRecorrido){
        ArrayList<String> respuesta=new ArrayList<String>();
        try{
            conectar();
            out.writeUTF("buscarRecomendacionesRecorrido");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRecorrido);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                respuesta.add(listReception.get(0));
                respuesta.add(listReception.get(1));
                respuesta.add(listReception.get(2));
                respuesta.add(listReception.get(3));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }

        return respuesta;
    }

    /**
     * Función que solicita la carga de un reto de tipo cultural.
     * @param name
     * @return nuvo
     */
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
                nuvo.setRespuesta(listReception.get(7));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return nuvo;
    }

    /**
     * Función que solicita la carga del final de la historia para una ruta.
     * @param nombreRuta
     * @return finalHistoria
     */
    public String finalHistoria(String nombreRuta){
        String finalHistoria="";
        try{
            conectar();
            out.writeUTF("historiaFinalRuta");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombreRuta);
                finalHistoria=in.readUTF();
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }
        return finalHistoria;
    }

    /**
     * Función que solicita la carga de los datos de una ruta.
     * @param nombre
     * @return n
     */
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

    /**
     * Función que solicita la carga de los datos de un usuario.
     * @param nombre
     * @return n
     */
    public DatosRyR buscarUsuario(String nombre){
        DatosRyR n = new DatosRyR();
        try{
            conectar();
            out.writeUTF("buscarUsuario");
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
                n.setAdic(listReception.get(3));
                n.setPreferenciaUser1(listReception.get(4));
                n.setPreferenciaUser2(listReception.get(5));
            }
            cerrar();
        }catch (IOException e){
            e.printStackTrace();
        }


        return n;
    }

    /**
     * Función que solicita la modificación de los elementos de un usuario.
     * @param nombre
     * @param nuevonombre
     * @param action
     * @param preferencias
     * @return devol
     */
    public String updateUsuario(String nombre, String nuevonombre,String action,ArrayList<Integer> preferencias){
        String devol="";
        if(!action.equals("preferencias")) {
            try {
                conectar();
                out.writeUTF("updateUsuario");
                if (in.readUTF().equals("continua")) {
                    out.writeUTF(nombre);
                    out.writeUTF(action);
                    out.writeUTF(nuevonombre);

                    devol = in.readUTF();
                }
                cerrar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                conectar();
                out.writeUTF("updateUsuario");
                if (in.readUTF().equals("continua")) {
                    out.writeUTF(nombre);
                    out.writeUTF(action);
                    objectOutput.writeObject(preferencias);

                    devol = in.readUTF();
                }
                cerrar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return devol;
    }

    /**
     * Función que solicita la carga del conjuto de tramos existentes para una ruta.
     * @param nombre
     * @return result
     */
    public ArrayList<Tramo> cargarVisionRuta(String nombre){
        ArrayList<Tramo> result = new ArrayList<Tramo>();
        try{
            conectar();
            out.writeUTF("obtenerDibujoRuta");
            if(in.readUTF().equals("continua")){
                out.writeUTF(nombre);
                try{
                    object = objectInput.readObject();
                    listReception= (ArrayList<String>) object;

                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            for(int i=0;i<listReception.size();i=i+4) {

                Double ini1 = Double.valueOf(listReception.get(i));
                Double ini2 = Double.valueOf(listReception.get((i+1)));
                Double ini3 = Double.valueOf(listReception.get((i+2)));
                Double ini4 = Double.valueOf(listReception.get((i+3)));

                Tramo nuevo = new Tramo(new LatLng(ini1,ini2), new LatLng(ini3, ini4));
                result.add(nuevo);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        cerrar();

        return result;
    }
}
