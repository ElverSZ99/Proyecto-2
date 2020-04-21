/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author XPC
 */
public class Archivo {
    private String nombre;
    private String ruta;
    private String tipo;
    private File archivo;
    private BufferedReader lector;
    protected BufferedWriter escritor;
    public NodeList listaCaracteristicas;
    protected NodeList textos;
    public Node nodo;

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

    public Archivo(String ruta) {

        this.ruta = ruta;

        this.abrir();
    }

    public void abrir() {
        try {
            archivo = new File(ruta);
            if (!existe()) {
                this.crear();
                lector = new BufferedReader(new FileReader(archivo));
                escritor = new BufferedWriter(new FileWriter(archivo, true));
                this.limpiar();
                escribir("<Expediente>");
                escribir("<Cita>");
                escribir("<FechaDeLaCita>" + "</FechaDeLaCita>");
                escribir("<HoraDeLaCita>" + "</HoraDeLaCita>");
                escribir("<CedulaMedico>" + "</CedulaMedico>");
                escribir("<NombreMedico>" + "</NombreMedico>");
                escribir("<Especialidad>" + "</Especialidad>");
                escribir("<CedulaPaciente>" + "</CedulaPaciente>");
                escribir("<NombrePaciente>" + "</NombrePaciente>");
                escribir("<FechaNacimientoPaciente>" + "</FechaNacimientoPaciente>");
                escribir("<Comentario>" + "</Comentario>");
                escribir("</Cita>");
                escribir("</Expediente>");
                guardar();
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder construdocumenento = dbf.newDocumentBuilder();
            Document documento = construdocumenento.parse(archivo);
            documento.getDocumentElement().normalize();
            listaCaracteristicas = documento.getElementsByTagName("Expediente");
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrar() {
        try {
            escritor.close();
            lector.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crear() {
        try {
            archivo.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean existe() {
        return archivo.exists();
    }

    public void guardar() {
        try {
            escritor.flush();
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String leer() {
        try {
            return lector.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void escribir(String string) {
        try {
            escritor.write(string);
            escritor.newLine();
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrar() {
        this.cerrar();
        archivo.delete();
    }

    public void cambiarNombre(String nombre) {
        File nuevoArchivo = new File(nombre);
        this.archivo.renameTo(nuevoArchivo);
    }

    public void limpiar() {
        try {
            escritor = new BufferedWriter(new FileWriter(archivo, false));
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    

