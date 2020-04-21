/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

 


public class ArchivosIniL {

    private Properties Parametro;
    private String ruta;

    public Properties getProperties() {
        return Parametro;
    }

    public void leerArchivo(String ruta) {
        this.Parametro = new Properties();
        try {
            Parametro.load(new FileReader(ruta));

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo ini");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer el archivo");
        }
    }

}
