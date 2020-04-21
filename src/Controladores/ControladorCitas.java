/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import Grafico.Frm_Citas;
import Modelo.BasedeDatos;
import Modelo.Citas;
import Modelo.Medicos;
import Modelo.Pacientes;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XPC
 */
public class ControladorCitas {
   private Citas cita; 
    private Medicos medico;
    private Pacientes paciente;
   private Frm_Citas frmcitas;
    private Object[] obj;

    public ControladorCitas() {
        this.frmcitas = frmcitas;
        medico=null;
        paciente=null;
    }
   /**
    * guarda la cita en la base de datos
    * @param frmcitas 
    */
  public void guardarCita(Frm_Citas frmcitas){
      int cont;
       Date fecha = new Date(frmcitas.getJDateFechaCita().getDate().getTime());
     cita= new Citas(0, Integer.parseInt(frmcitas.getCbHoras().getSelectedItem().toString()),paciente,medico, fecha);
     cita.getPaciente().setId(frmcitas.getTxtCedulaPaciente().getText());
     cita.getMedico().setId(frmcitas.getTxtCedula().getText());
        if (buscarMedico(frmcitas)==true && buscarPaciente(frmcitas)==true) {
            BasedeDatos bd2= new BasedeDatos("Select count(*) from citas join pacientes on citas.Paciente= pacientes.cedula WHERE  Paciente=? and citas.fecha=? and citas.hora=?");
        bd2.ejecutar(new Object[]{this.paciente.getId(),cita.getFecha(),cita.getHora()});
        obj=bd2.getObject();
        cont = Integer.parseInt(obj[0].toString());
            if (cont>=1) {
                JOptionPane.showMessageDialog(null,"El paciente ya tiene una cita en ese dia y a esa misma hora");
            }else{
                BasedeDatos bd= new BasedeDatos("INSERT INTO `citas` VALUES (null,?,?,?,?)");
                   bd.ejecutar(new Object[]{cita.getFecha(),cita.getHora(),cita.getPaciente().getId(),cita.getMedico().getId()});
                }
        }
    } 
    /**
     * busca el medico para saber si existe
     * @param frmcitas
     * @return retorna un estado booleano
     */
    public boolean buscarMedico(Frm_Citas frmcitas){
        BasedeDatos bd = new BasedeDatos("SELECT `Cedula` FROM `medicos` WHERE Cedula=?"); 
        medico= new Medicos();
        medico.setId(frmcitas.getTxtCedula().getText());
        if (medico.contarDigitosCedu()==true) {
             bd.ejecutar(new Object[]{medico.getId()});
              obj=bd.getObject();
        if (obj==null) {
            return false;
        }else{
         if (obj[0].equals(this.medico.getId())) {
          return true;
         }
        }
        }
       return false;
    }
   /**
    * busca el paciente para saber si existe
    * @param frmcitas
    * @return retorna un estado booleano
    */
     public boolean buscarPaciente(Frm_Citas frmcitas){
        BasedeDatos bd = new BasedeDatos("SELECT `Cedula` FROM `pacientes` WHERE Cedula=?"); 
        paciente= new Pacientes();
        paciente.setId(frmcitas.getTxtCedulaPaciente().getText());
        if (paciente.contarDigitosCedu()==true) {
             bd.ejecutar(new Object[]{paciente.getId()});
              obj=bd.getObject();
        if (obj==null) {
            return false;
        }else{
         if (obj[0].equals(this.paciente.getId())) {
          return true;
         }
        }
        }
       return false;
    }
     /**
      * limita la citas a los medicos a 4 por fecha y hora
      * @param frmcitas
      * @return retorna un estado booleano
      */
     public boolean limitarCitas(Frm_Citas frmcitas){
     int cont;
  
     cita=new Citas();
     cita.setMedico(medico);
     cita.getMedico().setId(frmcitas.getTxtCedula().getText());
      Date fecha = new Date(frmcitas.getJDateFechaCita().getDate().getTime());
      cita.setFecha(fecha);
      cita.setHora(Integer.parseInt(frmcitas.getCbHoras().getSelectedItem().toString()));
     
      
        BasedeDatos bd= new BasedeDatos("Select count(*) FROM citas join medicos on citas.Medico = medicos.cedula where medicos.cedula=? and citas.fecha=? and citas.hora=?");
           bd.ejecutar(new Object[]{this.cita.getMedico().getId(),this.cita.getFecha(),this.cita.getHora()});
     obj=bd.getObject();
   cont = Integer.parseInt(obj[0].toString());
     if(cont>=4){
       JOptionPane.showMessageDialog(null, "Cantidad máxima de citas alcanzada!");
         return false;
        
     }else{
     this.guardarCita(frmcitas);
     return true;
     }
}
/**
 * extra: agrega datos del medico para saber a quien le peretece la cedula que se busco
 * @param frmcitas 
 */
     public void agregarDatosMedico(Frm_Citas frmcitas){
      BasedeDatos bd = new BasedeDatos("SELECT NombreCompleto,Especialidad FROM `medicos` WHERE Cedula=?"); 
        medico= new Medicos();
        medico.setId(frmcitas.getTxtCedula().getText());
           bd.ejecutar(new Object[]{medico.getId()});
          obj=bd.getObject();
            frmcitas.setTxtNombreMedico((String) obj[0]);
            frmcitas.setTxtEspecialidadMedico((String) obj[1]);
     }
     /**
      * extra:  agrega datos del paciente para saber a quien le peretece la cedula que se busco
      * @param frmcitas 
      */
     public void agregarDatosPaciente(Frm_Citas frmcitas){
      BasedeDatos bd = new BasedeDatos("SELECT Nombre,Fecha FROM `pacientes` WHERE Cedula=?"); 
        paciente= new Pacientes();
        paciente.setId(frmcitas.getTxtCedulaPaciente().getText());
           bd.ejecutar(new Object[]{paciente.getId()});
          obj=bd.getObject();
          frmcitas.setTxtNombrePaciente((String) obj[0]);
          frmcitas.setTxtFechaNacimiento((Date) obj[1]);
     }
     /**
      * busca todas las citas en la base de datos
      * @param frmcitas 
      */
     public void buscarTodasLasCitas(Frm_Citas frmcitas){
         BasedeDatos bd= new BasedeDatos("SELECT citas.ID,citas.Fecha,citas.Hora,citas.Paciente,citas.Medico FROM `citas` JOIN medicos on citas.Medico = medicos.Cedula JOIN pacientes on citas.Paciente = pacientes.Cedula");
         bd.ejectuar();
         DefaultTableModel modelo = (DefaultTableModel) frmcitas.getTablaCitas().getModel();
                  modelo.setNumRows(0);
             do {
                 obj = bd.getObject();
            if (obj != null) {      
                modelo.addRow(obj);
            }
        } while (obj!=null);   
     }
     /**
      * busca cita por fecha en la base de datos
      * @param frmcitas 
      */
     public void buscarCitaPorFecha(Frm_Citas frmcitas){
        BasedeDatos bd=new BasedeDatos("SELECT * FROM `citas` WHERE Fecha=?");
        Date fecha = new Date(frmcitas.getJDateBuscarCita().getDate().getTime());
        cita= new Citas();
        cita.setFecha(fecha);
        bd.ejecutar(new Object[]{cita.getFecha()});
         DefaultTableModel modelo = (DefaultTableModel) frmcitas.getTablaCitas().getModel();
         modelo.setNumRows(0);     
       do {
                 obj = bd.getObject();
            if (obj != null) {
                
                modelo.addRow(obj);
            }
        } while (obj!=null);
     }
     /**
      * elimina un cita en la base de datos
      * @param frmcitas 
      */
     public void eliminar(Frm_Citas frmcitas){
     BasedeDatos bd=new BasedeDatos("Delete FROM citas where ID=?");
     cita=new Citas();
     cita.setID(frmcitas.getId());
     bd.ejecutar(new Object[]{cita.getID()});
     }
  /**
   * actualiza una cita en la base de datos
   * @param frmcitas 
   */
     public void actualizarCita(Frm_Citas frmcitas){
     BasedeDatos bd= new BasedeDatos("UPDATE `citas` SET `Fecha`=?,`Hora`=?,`Paciente`=?,`Medico`=? WHERE ID=?");
     Date fecha = new Date(frmcitas.getJDateFechaCita().getDate().getTime());
     this.medico=new Medicos();
     this.paciente=new Pacientes();
     cita= new Citas(frmcitas.getId(), Integer.parseInt(frmcitas.getCbHoras().getSelectedItem().toString()), paciente, medico, fecha);
     cita.getMedico().setId(frmcitas.getTxtCedula().getText());
     cita.getPaciente().setId(frmcitas.getTxtCedulaPaciente().getText());
     bd.ejecutar(new Object[]{cita.getFecha(),cita.getHora(),cita.getPaciente().getId(),cita.getMedico().getId(),cita.getID()});
     }
}
