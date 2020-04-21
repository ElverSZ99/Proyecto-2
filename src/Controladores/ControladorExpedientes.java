/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Grafico.ExpedienteSencillo;
import Grafico.InicioDeSesion;
import Modelo.Archivo;
import Modelo.BasedeDatos;
import Modelo.Citas;
import Modelo.Expedientes;
import Modelo.Medicos;
import Modelo.Pacientes;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XPC
 */
public class ControladorExpedientes {
    private Expedientes expediente;
    private Citas citas;
    private Medicos medico;
    private Pacientes pacientes;
    private Expedientes modelo;
    private Archivo arch;
    private Object[] obj;
    
    
    public ControladorExpedientes() {
        citas = null;
        medico = null;
        pacientes = null;
    }

    /**
     * busca datos del paciente para que se muestren
     *
     * @param exp
     */
    public void buscarpaciente(ExpedienteSencillo exp) {
        if (!"".equals(exp.getTxtCedulaPaciente().getText())) {
            this.pacientes = new Pacientes();
            this.pacientes.setId(exp.getTxtCedulaPaciente().getText());
            BasedeDatos bd = new BasedeDatos("SELECT Nombre,Fecha  FROM `pacientes` WHERE Cedula=?");
            bd.ejecutar(new Object[]{this.pacientes.getId()});
            Object[] obj = bd.getObject();
            exp.setTxtNombrePaciente(obj[0].toString());
            SimpleDateFormat fechas = new SimpleDateFormat("dd/MM/yyyy");
            exp.setTxtFechaPaciente(fechas.format(obj[1]));
        }
    }

    /**
     * carga datos del medico cuando inicie sesion al expediente
     *
     * @param frmsesion
     * @param exp
     */
    public void cargarMedico(InicioDeSesion frmsesion, ExpedienteSencillo exp) {
        BasedeDatos bd = new BasedeDatos("SELECT `NombreCompleto`,`Cedula`,`Especialidad` FROM `medicos` WHERE Usuario=?");
        this.medico = new Medicos();
        this.medico.setEspecialidad(frmsesion.getTxtUsuario().getText());
        bd.ejecutar(new Object[]{this.medico.getId()});
        Object[] obj = bd.getObject();
        exp.setTxtMedico(obj[0].toString());
        exp.setTxtEspecialidad(obj[2].toString());
        exp.setTxtCedulaMedic(obj[1].toString());
    }

    /**
     * busca las citas del dia, del medico que esta atendido mediante la fecha
     *
     * @param frmsesion
     * @param exp
     */
    public void buscarFecha(InicioDeSesion frmsesion, ExpedienteSencillo exp) {
        BasedeDatos bd = new BasedeDatos("SELECT Cedula FROM `medicos` WHERE Usuario=?");
        this.medico = new Medicos();
        this.medico.setEspecialidad(frmsesion.getTxtUsuario().getText());
        bd.ejecutar(new Object[]{this.medico.getId()});
        this.obj = bd.getObject();
        BasedeDatos bd2 = new BasedeDatos("SELECT ID,Fecha,Hora,Paciente FROM `citas`  WHERE Medico=? and Fecha=? order by Hora");
        Date fecha = new Date(frmsesion.getjDateChooser1().getDate().getTime());
        this.citas = new Citas();
        this.citas.setFecha(fecha);
        bd2.ejecutar(new Object[]{this.obj[0], this.citas.getFecha()});
        Object obj2[];
        DefaultTableModel modelo = (DefaultTableModel) exp.getTablaCitasDelDia().getModel();
        modelo.setNumRows(0);
        do {
            obj2 = bd2.getObject();
            if (obj2 != null) {
                modelo.addRow(obj2);
            }
        } while (obj2 != null);
    }

    /**
     * verifica la existencia de un expendiente
     *
     * @param exp
     * @return
     */
    public boolean verificarExistenciaDeExpediente(ExpedienteSencillo exp) {
        try {
        int cont;
        BasedeDatos bd = new BasedeDatos("SELECT COUNT(*) FROM `expediente` WHERE Fecha=? AND Paciente=? AND Hora=?");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = formato.parse(exp.getTxtFechaAtencion().getText());
        java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
        this.pacientes = new Pacientes();
        this.modelo = new Expedientes();
        this.modelo.setFecha(fecha);
        this.modelo.setPaciente(pacientes);
        this.modelo.getPaciente().setId(exp.getTxtCedulaPaciente().getText());
        this.modelo.setHora(exp.getTxtHora().getText());
        bd.ejecutar(new Object[]{this.modelo.getFecha(), this.modelo.getPaciente().getId(), this.modelo.getHora()});
        this.obj = bd.getObject();
        cont = Integer.parseInt(this.obj[0].toString());
        if (cont >= 1) {
            return false;
        } else {
            exportarDatos(exp);
            exportarXML(exp);
            return true;
        }
         } catch (ParseException ex) {
            Logger.getLogger(ControladorExpedientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }

    /**
     * guarda el expediente en la base de datos
     *
     * @param exp
     */
    public void exportarDatos(ExpedienteSencillo exp) {
        try{
        BasedeDatos bd = new BasedeDatos("INSERT INTO expediente VALUES (?,?,?,?,?)");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = formato.parse(exp.getTxtFechaAtencion().getText());
        java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
        this.medico = new Medicos();
        this.pacientes = new Pacientes();
        this.modelo = new Expedientes(fecha, exp.getTxtHora().getText(), medico, pacientes, exp.getTxtComentario().getText());
        this.modelo.getMedico().setId(exp.getTxtCedulaMedic().getText());
        this.modelo.getPaciente().setId(exp.getTxtCedulaPaciente().getText());
        bd.ejecutar(new Object[]{this.modelo.getFecha(), this.modelo.getHora(), this.modelo.getMedico().getId(), this.modelo.getPaciente().getId(), this.modelo.getDescripcion()});
        } catch (ParseException ex) {
            Logger.getLogger(ControladorExpedientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * guarda y acutaliza el expediente en un archivo XML
     *
     * @param exp
     */
    public void exportarXML(ExpedienteSencillo exp) {
        String ruta = System.getProperty("user.dir");
        BasedeDatos bd = new BasedeDatos("Select expediente.Fecha,Hora,Medico,medicos.NombreCompleto,medicos.Especialidad,Paciente,pacientes.Nombre,pacientes.Fecha,Descripcion FROM `expediente` JOIN pacientes on expediente.Paciente=pacientes.Cedula JOIN medicos ON expediente.Medico=medicos.Cedula WHERE Paciente=?");
        this.modelo = new Expedientes();
        this.pacientes = new Pacientes();
        this.modelo.setPaciente(this.pacientes);
        this.modelo.getPaciente().setId(exp.getTxtCedulaPaciente().getText());
        bd.ejecutar(new Object[]{this.modelo.getPaciente().getId()});
        String[] fecha = exp.getTxtFechaAtencion().getText().split("/");
        this.arch = new Archivo(ruta + "\\Citas\\" + this.modelo.getPaciente().getId() + "[" + fecha[0] + "-" + fecha[1] + "-" + fecha[2] + "]" + ".xml");
        this.arch.limpiar();
        this.arch.escribir("<Expediente>");
        do {
            this.obj = bd.getObject();
            if (this.obj != null) {
                this.arch.escribir("<Cita>");
                this.arch.escribir("<FechaDeLaCita>" + this.obj[0].toString() + "</FechaDeLaCita>");
                this.arch.escribir("<HoraDeLaCita>" + this.obj[1].toString() + "</HoraDeLaCita>");
                this.arch.escribir("<CedulaMedico>" + this.obj[2].toString() + "</CedulaMedico>");
                this.arch.escribir("<NombreMedico>" + this.obj[3].toString() + "</NombreMedico>");
                this.arch.escribir("<Especialidad>" + this.obj[4].toString() + "</Especialidad>");
                this.arch.escribir("<CedulaPaciente>" + this.obj[5].toString() + "</CedulaPaciente>");
                this.arch.escribir("<NombrePaciente>" + this.obj[6].toString() + "</NombrePaciente>");
                this.arch.escribir("<FechaNacimientoPaciente>" + this.obj[7].toString() + "</FechaNacimientoPaciente>");
                this.arch.escribir("<Comentario>" + this.obj[8].toString() + "</Comentario>");
                this.arch.escribir("</Cita>");

            }
        } while (this.obj != null);
        this.arch.escribir("</Expediente>");
        this.arch.guardar();
    }

    /**
     * busca las observaciones que ha tenido anteriormente el paciente que esta
     * atendiendo
     *
     * @param exp
     */
    public void buscarObservaciones(ExpedienteSencillo exp) {
        BasedeDatos bd = new BasedeDatos("SELECT Fecha,Descripcion FROM `expediente` WHERE Paciente=?");
        this.modelo = new Expedientes();
        this.pacientes = new Pacientes();
        this.modelo.setPaciente(this.pacientes);
        this.modelo.getPaciente().setId(exp.getTxtCedulaPaciente().getText());
        bd.ejecutar(new Object[]{this.modelo.getPaciente().getId()});
        DefaultTableModel modelo = (DefaultTableModel) exp.getTablaObser().getModel();
        modelo.setNumRows(0);
        do {
            this.obj = bd.getObject();
            if (obj != null) {
                modelo.addRow(this.obj);
            }
        } while (obj != null);
    }

    /**
     * actualiza el expendiente en la base de datos
     *
     * @param exp
     */
    public void acutalizarExpediente(ExpedienteSencillo exp) {
        try {
        BasedeDatos bd = new BasedeDatos("UPDATE `expedientes` SET `DescripcionConsulta`=?  WHERE MedicoQueAtiende=? and Fecha=? AND Hora=? AND Paciente=?");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate;
        utilDate = formato.parse(exp.getTxtFechaAtencion().getText());
        java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
        this.medico = new Medicos();
        this.pacientes = new Pacientes();
        this.modelo = new Expedientes(fecha, exp.getTxtHora().getText(), this.medico, this.pacientes, exp.getTxtComentario().getText());
        this.modelo.getMedico().setId(exp.getTxtCedulaMedic().getText());
        this.modelo.getPaciente().setId(exp.getTxtCedulaPaciente().getText());
        bd.ejecutar(new Object[]{this.modelo.getDescripcion(), this.modelo.getMedico().getId(), this.modelo.getFecha(), this.modelo.getHora(), this.modelo.getPaciente().getId()});
        } catch (ParseException ex) {
            Logger.getLogger(ControladorExpedientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    
    

