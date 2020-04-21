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
public class Medicos extends Usuarios{
    String codigoColegio;
    String especialidad;
    Double salario;
    public double SalarioNeto;

    
    public Medicos(){
        
        
    }
     public Medicos(String nombre, String cedula, String correo, String telefono, String usuario, String pass, String especialidad, String codigo, double salario, Date fecha) {
        this.nombre = nombre;
        this.id = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fecha;
        this.nombreUsuario = usuario;
        this.contraseña = pass;
        this.especialidad = especialidad;
        this.codigoColegio = codigo;
        this.salario = salario;
    }
    
    public Medicos(Object[] obj){
        this.nombre = (String) obj[1];
        this.id = (String) obj[0];
        this.correo = (String) obj[3];
        this.telefono = (String) obj[5];
        this.fechaNacimiento = (Date) obj[2];
        this.nombreUsuario = (String) obj[8];
        this.contraseña = (String) obj[9];
        this.especialidad = (String) obj[6];
        this.codigoColegio = (String) obj[4];
        this.salario = (double) obj[7];
        
        
    }
    

    public String getCodigoColegio() {
        return codigoColegio;
    }

    public void setCodigoColegio(String codigoColegio) {
        this.codigoColegio = codigoColegio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        if (salario > 0.0) {
            this.salario = salario;
        }
    }
    
    
    
    public String calcularSalarioNeto() {
        if (this.salario < 817000) {
            this.deducionesDelSalario();
            return ("El salario neto es: " + this.SalarioNeto);

        }
        if (this.salario > 817001 && this.salario < 1226000) {
            double ImpuestosDeRenta10 = (this.salario * 10) / 100;
            this.deducionesDelSalario();
            this.SalarioNeto = this.SalarioNeto - ImpuestosDeRenta10;
            return ("El salario neto es: " + this.SalarioNeto);

        }
        if (this.salario > 1226001) {
            double ImpuestosDeRenta15 = (this.salario * 15) / 100;
            this.deducionesDelSalario();
            this.SalarioNeto = this.SalarioNeto - ImpuestosDeRenta15;
            return ("El salario neto es: " + this.SalarioNeto);
        }
        return null;
    }

    public void deducionesDelSalario() {
        double EnfermadYMaternidad = (this.salario * 5.5) / 100;
        double InvalidezYMuerte = (this.salario * 3.64) / 100;
        double AporteDelTrabajador = (this.salario * 1) / 100;
        double AportaALaAsociaciónSolidarista = (this.salario * 3.3) / 100;
        double total = EnfermadYMaternidad + InvalidezYMuerte + AporteDelTrabajador + AportaALaAsociaciónSolidarista;
        this.SalarioNeto = this.salario - total;
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
        return new Object[]{this.id, this.nombre, this.fechaNacimiento, this.correo, this.codigoColegio, this.telefono, this.especialidad, this.salario, this.nombreUsuario, this.contraseña};
    }
  
    
    

    
}
