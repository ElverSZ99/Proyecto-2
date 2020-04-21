/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author XPC
 */
public class Secretarios extends Usuarios {
    
    
    
    public Secretarios(){
        
    }
    
    public Secretarios(String nombre, String cedula, String correo, String telefono, String usuario, String pass, Date fecha) {
        this.nombre = nombre;
        this.id = cedula;
        this.fechaNacimiento = fecha;
        this.correo = correo;
        this.telefono = telefono;
        this.nombreUsuario = usuario;
        this.contraseña = pass;
    }
  

    public Secretarios(Object[] obj) {
        this.nombre = (String) obj[1];
        this.id = (String) obj[0];
        this.fechaNacimiento = (Date) obj[2];
        this.correo = (String) obj[3];
        this.telefono = (String) obj[4];
        this.nombreUsuario = (String) obj[5];
        this.contraseña = (String) obj[6];
    }
    
    public boolean contarDigitosCedu() {
        this.id = id.replaceAll("[^0-9]", "");
        return id.length() == 9 || id.length() == 10;
    }

    public boolean contarDigitostel() {
        this.telefono = telefono.replaceAll("[^0-9]", "");
        return this.telefono.length() == 8;
    }

    public void ponerMayusculas() {
        String nombreConLasMayusculas = "";
        for (String palabra : this.nombre.split(" ")) {
            nombreConLasMayusculas += palabra.substring(0, 1).toUpperCase() + palabra.substring(1, palabra.length()).toLowerCase() + " ";
        }
        nombreConLasMayusculas = nombreConLasMayusculas.trim();
        this.nombre = nombreConLasMayusculas;
    }

    public Object[] toObject() {
        return new Object[]{this.id, this.nombre, this.fechaNacimiento, this.correo, this.telefono, this.nombreUsuario, this.contraseña};
    }
    
    
}
