package chatMessage;

import Vista.FormCliente;
import java.net.*;
import java.io.*;

/*
 * The Client that can be run both as a console or a GUI
 */
public class Client {

    // for I/O
    private ObjectInputStream sInput;       // to read from the socket
    private ObjectOutputStream sOutput;     // to write on the socket
    private Socket socket;
    // if I use a GUI or not
    private ClientGUI cg;
    //formulario de presentacion del cliente
    private FormCliente cliente;
    // the server, the port and the username
    private String server, username;
    private int port;
    private ChatMessage cm;

    public Client(String server, int port, String username, FormCliente cliente) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.cliente = cliente;
    }

    public boolean start() {
        // try to connect to the server
        try {

            socket = new Socket(server, port);

        } // if it failed not much I can so
        catch (Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

//        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
//        display(msg);

        /* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            /*
             * indicamos que lo primero a imprimir es el nombre del cliente 
             * al que se ingreso al servidor
             */
            sOutput.writeObject(username);


        } catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    /*
     * To send a message to the console or the GUI
     */
    private void display(String msg) {
        if (cg == null) {
            System.out.println(msg);      // println in console mode
        } else {
            cg.append(msg + "\n");      // append to the ClientGUI JTextArea (or whatever)
        }
    }

    /*
     * To send a message to the server
     */
    public void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } // not much else I can do

        // inform the GUI
        if (cg != null) {
            cg.connectionFailed();
        }

    }

    public class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {

                     ChatMessage msg = (ChatMessage) sInput.readObject();
                    
                    // if console mode print the message and add back the prompt
                    if (cliente == null) {
                        System.out.println(msg);
                        System.out.print("> ");
                    } else {
                        //cliente.append(msg);
                        
                        if(msg.getType()==0)
                        {
                            cliente.añadirUsuarios(msg.getMessage());
                        }else
                        {
                            cliente.agregarMensaje(msg.getMessage());
                        }
                            
                            //cliente.agregarMensaje(msg);
                    }
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    if (cg != null) {
                        cg.connectionFailed();
                    }
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}