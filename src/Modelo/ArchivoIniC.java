/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


 
public class ArchivoIniC {

    private String nombre;
    private String ruta;
    private String tipo;
    private File archivo;
    private BufferedReader lector;
    private BufferedWriter escritor;
    private Properties Parametro;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Properties getProperties() {
        return Parametro;
    }

    public ArchivoIniC(String ruta) {
        this.nombre = "Configuracion";
        this.ruta = ruta;
        this.tipo = ".ini";
        this.abrirIni();
    }

    public ArchivoIniC() {
    }

    public void abrirIni() {
        try {
            archivo = new File(ruta + "\\" + nombre + tipo);
            if (!existe()) {
                this.crear();
                lector = new BufferedReader(new FileReader(archivo));
                escritor = new BufferedWriter(new FileWriter(archivo, true));
                escribir("[Configuracion]");
                escribir("BD=");
                escribir("IP=");
                escribir("Usuario=");
                escribir("Pass=");
                escribir("ruta=");
                this.guardar();
            }
            lector = new BufferedReader(new FileReader(archivo));
            escritor = new BufferedWriter(new FileWriter(archivo, true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cerrar() {
        try {
            escritor.close();
            lector.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crear() {
        try {
            archivo.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean existe() {
        return archivo.exists();
    }

    public void guardar() {
        try {
            escritor.flush();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String leer() {
        try {
            return lector.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void escribir(String string) {
        try {
            escritor.write(string);
            escritor.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrar() {
        this.cerrar();
        archivo.delete();
    }

    public void limpiar() {
        try {
            escritor = new BufferedWriter(new FileWriter(archivo, false));
        } catch (IOException ex) {
            Logger.getLogger(ArchivoIniC.class.getName()).log(Level.SEVERE, null, ex);
        }
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
