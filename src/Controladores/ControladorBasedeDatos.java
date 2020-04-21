/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Grafico.Servidor;
import Modelo.BasedeDatos;

/**
 *
 * @author XPC
 */
public class ControladorBasedeDatos {
    private Servidor servidor;

    public ControladorBasedeDatos(Servidor servidor) {
        this.servidor = servidor;
    }
/**
 * comprueba si el servidor esta encendido
 * @return retorna un estado booleano
 */
    public boolean comprobar() {
        BasedeDatos bd = new  BasedeDatos();
        return "Encendido".equals(bd.comprobar());
    }
/**
 * conecta de nuevo el servidor
 * @return retorna un estado booleano
 */
    public boolean conectar() {
       BasedeDatos bd = new BasedeDatos(this.servidor.getTxtIP().getText(), this.servidor.getTxtUsu().getText(), this.servidor.getTxtPass().getText(),
               this.servidor.getTxtbd().getText(),this.servidor.getTxtRuta().getText());
       bd.setRuta(this.servidor.getTxtRuta().getText());
        return bd.encender() != false;
    }
/**
 * desconecta el servidor
 * @return retorna un estado booleano
 */
    public boolean desconectar() {
        BasedeDatos bd = new BasedeDatos();
        return bd.apagarservidor() == true;
    }
}
