/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Grafico.frmPacientes;
import Modelo.BasedeDatos;
import Modelo.Pacientes;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XPC
 */
public class ControladorPacientes {
    
    private Pacientes pacientes;
    private frmPacientes frmpacientes;

    public ControladorPacientes(frmPacientes frmpacientes) {
        this.frmpacientes = frmpacientes;
        pacientes = null;
    }
/**
 * guarda un paciente en la base de datos
 * @return retorna un estado booleano
 */
    public boolean guardarPaciente() {
        if (!"".equals(frmpacientes.getTxtcedula().getText())) {
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                    && frmpacientes.getTxtFecha().getDate()!=null && !"".equals(frmpacientes.getTxtTelefono().getText())
                    && !"".equals(frmpacientes.getTxtcorreo().getText())) {
                 BasedeDatos bd = new BasedeDatos("INSERT INTO pacientes VALUES (?,?,?,?,?)");
                Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
                pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
                this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }
                if (this.pacientes.contarDigitostel() == false) {
                    JOptionPane.showMessageDialog(null, "El numero es invalido");
                    return false;
                }
                bd.ejecutar(new Object[]{this.pacientes.getId(), this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
                return true;
            }
            
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                    &&  frmpacientes.getTxtFecha().getDate()!=null && !"".equals(frmpacientes.getTxtTelefono().getText())) {
                       BasedeDatos bd = new BasedeDatos("INSERT INTO pacientes VALUES (?,?,?,?,?)");
               Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
               pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
               frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
               this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }       
                if (this.pacientes.contarDigitostel() == false) {
                    JOptionPane.showMessageDialog(null, "El numero es invalido");
                    return false;
                }
         
                bd.ejecutar(new Object[]{this.pacientes.getId(), this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
                return true;
            }
        }
        
        if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                && frmpacientes.getTxtFecha().getDate()!=null) {
              BasedeDatos bd = new BasedeDatos("INSERT INTO pacientes VALUES (?,?,?,?,?)");
         Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
            pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());   
            this.pacientes.ponerMayusculas();
            if (this.pacientes.contarDigitosCedu() == false) {
                JOptionPane.showMessageDialog(null, "La cedula es invalida");
                return false;
            }
          
            bd.ejecutar(new Object[]{this.pacientes.getId(), this.pacientes.getNombre(),
                this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
            return true;
        }
        
        if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())) {
                     BasedeDatos bd = new BasedeDatos("INSERT INTO pacientes VALUES (?,?,?,?,?)");
       Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
           pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
            this.pacientes.ponerMayusculas();
            if (this.pacientes.contarDigitosCedu() == false) {
                JOptionPane.showMessageDialog(null, "La cedula es invalida");
                return false;
            }
            bd.ejecutar(new Object[]{this.pacientes.getId(), this.pacientes.getNombre(),
                this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
            return true;
        }
        return false;
    }
/**
 * busca un paciente en la base de datos
 */
    public void buscarpaciente() {
        if (!"".equals(frmpacientes.getTxtBuscar().getText())) {
            pacientes = new Pacientes();
            pacientes.setId(frmpacientes.getTxtBuscar().getText());
            BasedeDatos bd = new BasedeDatos("SELECT *  FROM `pacientes` WHERE Cedula=?");
            bd.ejecutar(new Object[]{pacientes.getId()});
            DefaultTableModel modelo = (DefaultTableModel) frmpacientes.getTablaPacientes().getModel();
            modelo.addRow(bd.getObject());
        }

    }
/**
 * proceso para saber que edad tiene el paciente
 * @return retorna su edad actual
 */
    public String edad() {
               Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
        return pacientes.calcularedad(fecha);
    }
/**
 * elimina un paciente de la base de datos
 * @return retorna un estado booleano
 */
    public boolean eliminar() {
        if (!"".equals(frmpacientes.getTxtBuscar().getText())) {
            BasedeDatos bd = new BasedeDatos("DELETE FROM pacientes WHERE Cedula=?");
            pacientes = new Pacientes();
            pacientes.setId(frmpacientes.getTxtBuscar().getText());   
            bd.ejecutar(new Object[]{pacientes.getId()});
            return true;
        }
        return false;
    }
/**
 * actualiza un paciente en la base de datos
 * @return retonar un estado booleano
 */
    public boolean actualizarPaciente() {
        if (!"".equals(frmpacientes.getTxtcedula().getText())) {
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                    && frmpacientes.getTxtFecha()!=null && !"".equals(frmpacientes.getTxtTelefono().getText())
                    && !"".equals(frmpacientes.getTxtcorreo().getText())) {
                 BasedeDatos bd = new BasedeDatos("UPDATE `pacientes` SET `Nombre`=?,`Fecha`=?,`Correo`=?,`Telefono`=? WHERE Cedula=?");
                Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
                pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
                this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }
                if (this.pacientes.contarDigitostel() == false) {
                    JOptionPane.showMessageDialog(null, "El numero es invalido");
                    return false;
                }
                bd.ejecutar(new Object[]{this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono(), this.pacientes.getId()});
                return true;
            }
            
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                    && frmpacientes.getTxtFecha().getDate()!=null && !"".equals(frmpacientes.getTxtTelefono().getText())) {
                  BasedeDatos bd = new BasedeDatos("UPDATE `pacientes` SET `Nombre`=?,`Fecha`=?,`Correo`=?,`Telefono`=? WHERE Cedula=?");
                Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
                pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
                this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }
                if (this.pacientes.contarDigitostel() == false) {
                    JOptionPane.showMessageDialog(null, "El numero es invalido");
                    return false;
                }
                bd.ejecutar(new Object[]{this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
                return true;
            }
            
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())
                    &&frmpacientes.getTxtFecha().getDate()!=null) {
                  BasedeDatos bd = new BasedeDatos("UPDATE `pacientes` SET `Nombre`=?,`Fecha`=?,`Correo`=?,`Telefono`=? WHERE Cedula=?");
                 Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
                pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());
                
                this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }
              
                bd.ejecutar(new Object[]{this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
                return true;
            }
            
            if (!"".equals(frmpacientes.getTxtcedula().getText()) && !"".equals(frmpacientes.getTxtnombre().getText())) {
                   BasedeDatos bd = new BasedeDatos("UPDATE `pacientes` SET `Nombre`=?,`Fecha`=?,`Correo`=?,`Telefono`=? WHERE Cedula=?");
              Date fecha = new Date(frmpacientes.getTxtFecha().getDate().getTime());
               pacientes = new Pacientes(frmpacientes.getTxtcedula().getText(), frmpacientes.getTxtnombre().getText(),fecha,
                frmpacientes.getTxtTelefono().getText(), frmpacientes.getTxtcorreo().getText());    
                this.pacientes.ponerMayusculas();
                if (this.pacientes.contarDigitosCedu() == false) {
                    JOptionPane.showMessageDialog(null, "La cedula es invalida");
                    return false;
                }
                bd.ejecutar(new Object[]{this.pacientes.getNombre(),
                    this.pacientes.getFechaNacimiento(), this.pacientes.getCorreo(), this.pacientes.getTelefono()});
                return true;
            }
        }
        return false;
    }
    
}
