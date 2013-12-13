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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
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
    private String numero;
    private static  Random nroAleatorio = new Random();
    private String  respuesta;
    public String generarNumero()
    {
        String num="";
        int  nroGenerado1 = nroAleatorio.nextInt(4);
        int  nroGenerado2 = nroAleatorio.nextInt(4);
        int  nroGenerado3 = nroAleatorio.nextInt(4);
        int  nroGenerado4 = nroAleatorio.nextInt(4);
        num = nroGenerado1+""+nroGenerado2+""+nroGenerado3+""+nroGenerado4;
        this.numero = num;
        return num;
    }
    public String verificarNumero(String numero1)
    {
        if(this.numero.equalsIgnoreCase(numero1))
        {
            return "XXXX";
        }
        String rta="";
        for(int i = 0 ; i < 4 ; i++)
        {
            if(this.numero.charAt(i)==numero1.charAt(i))
            {
                rta += "X";
            }
        }
        return rta;
    }
}
