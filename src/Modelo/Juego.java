/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Random;

/**
 *
 * @author milton.fernandez
 */
public class Juego {

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Random getNroAleatorio() {
        return nroAleatorio;
    }

    public void setNroAleatorio(Random nroAleatorio) {
        Juego.nroAleatorio = nroAleatorio;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    private int numero;
    private static  Random nroAleatorio = new Random();
    private String  respuesta;
    public String generarNumero()
    {
        String num="";
        int  nroGenerado1 = nroAleatorio.nextInt(4);
        int  nroGenerado2 = nroAleatorio.nextInt(4);
        int  nroGenerado3 = nroAleatorio.nextInt(4);
        int  nroGenerado4 = nroAleatorio.nextInt(4);
        num = nroGenerado1+"."+nroGenerado2+"."+nroGenerado3+"."+nroGenerado4;
        return num;
    }
}
