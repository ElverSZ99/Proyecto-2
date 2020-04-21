/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author XPC
 */
public class Pacientes {
    String id;
    String nombre;
    Date fechaNacimiento;
    String telefono;
    String correo;
    //int edadActtual;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

//    public int getEdadActtual() {
//        return edadActtual;
//    }
//
//    public void setEdadActtual(int edadActtual) {
//        this.edadActtual = edadActtual;
//    }

    public Pacientes(String id, String nombre, Date fechaNacimiento, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        //this.edadActtual = edadActtual;
    }
    
    public Pacientes() {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
       // this.edadActtual = edadActtual;
    }
    
    
    
    public String calcularedad(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat fechas = new SimpleDateFormat("yyyy/MM/dd");
            String fechaString = fechas.format(fecha);
            DateTimeFormatter calendario = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fechaNac = LocalDate.parse(fechaString, calendario);
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNac, ahora);
            return periodo.getYears() + " años y  " + periodo.getMonths() + " meses";
        }
        return null;
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

    @Override
    public String toString() {
        return "Pacientes{" + "cedula=" + id + ", nombreCompleto=" + nombre + ", fecha=" + this.fechaNacimiento + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
    
    
    
}
