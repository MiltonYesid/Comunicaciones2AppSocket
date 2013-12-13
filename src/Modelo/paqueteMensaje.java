package Modelo;

import java.io.*;

public class paqueteMensaje implements Serializable {

    protected static final long serialVersionUID = 1112122200L;
    public static final int tipoQuienesSeEncuentranConectados = 0, tipoMensajeTexto = 1, tipoSalirConexion = 2;
    public static final int tipoNumeroJuego = 3;
    private int tipoMensaje;
    private String contenidoMensaje;

    // constructor
    public paqueteMensaje(int tipoDeMensaje, String contenido) {
        this.tipoMensaje = tipoDeMensaje;
        this.contenidoMensaje = contenido;
    }

    // getters
    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public String getMessage() {
        return contenidoMensaje;
    }
}