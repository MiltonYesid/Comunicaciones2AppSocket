package Cliente;

import Modelo.paqueteMensaje;
import Vista.FormCliente;
import java.net.*;
import java.io.*;

public class Cliente {

    private ObjectInputStream infoEntrada;
    private ObjectOutputStream infoSalida;
    private Socket socket;
    //formulario de presentacion del cliente
    private FormCliente cliente;
    // the nombreServidor, el puerto y el  nombreUsuario
    private String nombreServidor, nombreUsuario;
    private int puerto;

    public Cliente(String server, int port, String user, FormCliente cliente) {
        this.nombreServidor = server;
        this.puerto = port;
        this.nombreUsuario = user;
        this.cliente = cliente;
    }

    public boolean iniciar() {

        /*
         * intenta conectarse con el servidor con los parametros del constructor
         */
        try {
            socket = new Socket(nombreServidor, puerto);

        } // if it failed not much I can so
        catch (Exception ec) {
            return false;
        }

//        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
//        display(msg);

        /* Creating both Data Stream */
        try {
            infoEntrada = new ObjectInputStream(socket.getInputStream());
            infoSalida = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            return false;
        }

        // creates the Thread to listen from the nombreServidor
        new ListenFromServer().start();
        // Send our nombreUsuario to the nombreServidor this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            /*
             * indicamos que lo primero a imprimir es el nombre del cliente 
             * al que se ingreso al servidor
             */
            infoSalida.writeObject(nombreUsuario);


        } catch (IOException eIO) {
            desconectar();
            return false;
        }
        return true;
    }

    /*
     * enviar mensaje al servidor
     */
    public void enviarMensajeAlServidor(paqueteMensaje msg) {
        try {
            infoSalida.writeObject(msg);
        } catch (IOException e) {
        }
    }


    private void desconectar() {
        try {
            if (infoEntrada != null) {
                infoEntrada.close();
            }
        } catch (Exception e) {
        } 
        try {
            if (infoSalida != null) {
                infoSalida.close();
            }
        } catch (Exception e) {
        } 
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } 
    }

    public class ListenFromServer extends Thread {

        public void run() {
            /*
             * un ciclo infinito 
             */
            while (true) {
                try {
                    paqueteMensaje msg = (paqueteMensaje) infoEntrada.readObject();
                    if (msg.getTipoMensaje() == 0) {
                        cliente.añadirUsuarios(msg.getMessage());
                    } else {
                        if(msg.getTipoMensaje()==3)
                        {
                            cliente.agregarNumeroJuego(msg.getMessage());
                        }else
                        {
                        cliente.agregarMensaje(msg.getMessage());
                        }
                    }
                } catch (IOException e) {
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}