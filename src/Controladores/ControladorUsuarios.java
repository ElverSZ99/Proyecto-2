/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Grafico.BuscarUsuario;
import Grafico.CambioDeContraseña;
import Grafico.InicioDeSesion;
//import Modelo.Usuarios;
import Grafico.frmUsuarios;
import Modelo.BasedeDatos;
import Modelo.Medicos;
import Modelo.Secretarios;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XPC
 */
public class ControladorUsuarios {
    private Secretarios secretaria;
    private Medicos medico;
    
    private  Object obj[];
    
    public ControladorUsuarios() {
        this.secretaria = null;
        this.medico= null;
    }
    /**
     * Metodo que agrega usuario segun su tipo(medico o secretaria)
     * @param frmUsuarios
     * @return retorna un estado booleano
     */
    public boolean agregar(frmUsuarios frmUsuarios){
        if (!"".equals(frmUsuarios.getTxtNumCedula().getText())) {
             if ("Secretaria".equals(frmUsuarios.getCbUsuario().getSelectedItem())) {
                 this.secretaria= new Secretarios();
                 this.secretaria.setId(frmUsuarios.getTxtNumCedula().getText());
                 if (this.secretaria.contarDigitosCedu()==false) {
                     JOptionPane.showMessageDialog(null, "La cedula es invalida");
                     return false;
                 }
            if (comprobarCedulaSecre(frmUsuarios)==true) {
            if (comprobarUsuarioSecre(frmUsuarios)==false) {
                frmUsuarios.setTxtMensaje("El usuario no esta disponible, por favor elija otro");
            }else{
                Date fecha = new Date(frmUsuarios.getJdateFecha().getDate().getTime());
                this.secretaria= new Secretarios(frmUsuarios.getTxtNombreCompleto().getText(),frmUsuarios.getTxtNumCedula().getText(),
                    frmUsuarios.getTxtCorreo().getText(),frmUsuarios.getTxtNumTelefono().getText(),frmUsuarios.getTxtNombreUsuario().getText(),
                    frmUsuarios.getTxtContra().getText(),fecha);
                this.secretaria.ponerMayusculas();
                if (this.secretaria.contarDigitostel()==false) {
                     JOptionPane.showMessageDialog(null, "El numero de telefono es invalido");
                     return false;
                 }
                   BasedeDatos bd=new BasedeDatos("INSERT INTO secretarias VALUES (?,?,?,?,?,?,?)");
                 bd.ejecutar(new Object[]{this.secretaria.getId(),this.secretaria.getNombre(),this.secretaria.getFechaNacimiento(),
                this.secretaria.getCorreo(),this.secretaria.getTelefono(),this.secretaria.getNombreUsuario(),this.secretaria.getContraseña()});
                 frmUsuarios.setTxtMensaje("");
                  return true;
            }
            }else{
            frmUsuarios.setTxtMensajeC("La cedula ya existe");
            return false;
            }
             }
        }
        
        if ("Medico".equals(frmUsuarios.getCbUsuario().getSelectedItem())) {
            this.medico=new Medicos();
            this.medico.setId(frmUsuarios.getTxtNumCedula().getText());
            if (this.medico.contarDigitosCedu()==false) {
                JOptionPane.showMessageDialog(null, "La cedula es invalida");
            }
            if ( comprobarCedulaMedic(frmUsuarios)==true) {              
            if (comprobarUsuarioMedic(frmUsuarios)==false) {
                    frmUsuarios.setTxtMensaje("El usuario no esta disponible, por favor elija otro");
            }else{
                    Date fecha = new Date(frmUsuarios.getJdateFecha().getDate().getTime());
                  this.medico= new Medicos(frmUsuarios.getTxtNombreCompleto().getText(),frmUsuarios.getTxtNumCedula().getText(),
                    frmUsuarios.getTxtCorreo().getText(),frmUsuarios.getTxtNumTelefono().getText(),frmUsuarios.getTxtNombreUsuario().getText(),
                    frmUsuarios.getTxtContra().getText(), (String) frmUsuarios.getCbEspecialidad().getSelectedItem(),frmUsuarios.getTxtCodigoCole().getText(),
                    Double.parseDouble(frmUsuarios.getTxtSalario().getText()),fecha);
                   this.medico.ponerMayusculas();
                  if (this.medico.contarDigitostel()==false) {
                     JOptionPane.showMessageDialog(null, "El numero de telefono es invalido");
                     return false;
                 }
                    BasedeDatos bd=new BasedeDatos("INSERT INTO medicos VALUES (?,?,?,?,?,?,?,?,?,?)");
            bd.ejecutar(new Object[]{this.medico.getId(),this.medico.getNombre(),this.medico.getFechaNacimiento()
                    ,this.medico.getCorreo(),this.medico.getCodigoColegio(),this.medico.getTelefono(),this.medico.getEspecialidad()
                    ,this.medico.getSalario(),this.medico.getNombreUsuario(),this.medico.getContraseña()});
                    frmUsuarios.setTxtMensaje("");
              return true;
            }
            }else{
                   frmUsuarios.setTxtMensajeC("La cedula ya existe");
                   return false;
                   }
        }
        
         return false;
    }
    /**
     * comprueba el usuario si existe o no para que el usuario se entere de ello
     * @param frmUsuarios
     * @return retorna un estado booleano
     */
    public boolean comprobarUs(frmUsuarios frmUsuarios){
        if ("Secretaria".equals(frmUsuarios.getCbUsuario().getSelectedItem())) {
              this.secretaria= new Secretarios();
              this.secretaria.setNombreUsuario(frmUsuarios.getTxtNombreUsuario().getText());
              if (comprobarUsuarioSecre(frmUsuarios)==false) {
                frmUsuarios.setTxtMensaje("El usuario no esta disponible, por favor elija otro");
                return false;
                    }
        }
        
        if ("Medico".equals(frmUsuarios.getCbUsuario().getSelectedItem())){
        this.medico= new Medicos();
        this.medico.setNombreUsuario(frmUsuarios.getTxtNombreUsuario().getText());
            if (comprobarUsuarioMedic(frmUsuarios)==false) {
                 frmUsuarios.setTxtMensaje("El usuario no esta disponible, por favor elija otro");
                 return false;
            }
        }     
            frmUsuarios.setTxtMensaje("El usuario esta disponible");
        return true;
    }
    
    /**
     * comprueba si el usuario de la secretaria si no existe en la base de datos para poder ser guardarlo en la base de datos
     * @param frmUsuarios
     * @return retorna un estado booleano
     */
    public boolean comprobarUsuarioSecre(frmUsuarios frmUsuarios){
       this.secretaria= new Secretarios();
       this.secretaria.setNombreUsuario(frmUsuarios.getTxtNombreUsuario().getText());
        BasedeDatos bd= new BasedeDatos("SELECT `Usuario` FROM `secretarias` WHERE Usuario=?");
        bd.ejecutar(new Object[]{this.secretaria.getNombreUsuario()});
       this.obj=bd.getObject();
        if (this.obj==null) {
            return true;
        }else{
         if (this.obj[0].equals(this.secretaria.getNombreUsuario())) {
          return false;
         }
        }
        return true;
        
    }
    /**
     * comprueba si el usuario del medico si no existe en la base de datos para poder ser agregado a la base de datos
     * @param frmUsuarios
     * @return retonar un estado booleano
     */
    public boolean comprobarUsuarioMedic(frmUsuarios frmUsuarios){
        this.medico=new Medicos();
        this.medico.setNombreUsuario(frmUsuarios.getTxtNombreUsuario().getText());
    BasedeDatos bd= new BasedeDatos("SELECT `Usuario` FROM `medicos` WHERE Usuario=?");
    bd.ejecutar(new Object[]{this.medico.getNombreUsuario()});
    this.obj=bd.getObject();
        if (this.obj==null) {
            return true;
        }else{
         if (this.obj[0].equals(this.medico.getNombreUsuario())) {
          return false;
         }
        }
        return true;
    }
    /**
     * comprueba si no existe la cedula de la secretaria en la base datos para poder ser agregada si no existe
     * @param frmUsuarios
     * @return retorna un estado booleano
     */
   public boolean comprobarCedulaSecre(frmUsuarios frmUsuarios){
    BasedeDatos bd= new BasedeDatos("SELECT `Cedula` FROM `secretarias` WHERE Cedula=?");
    this.secretaria=new Secretarios();
    this.secretaria.setId(frmUsuarios.getTxtNumCedula().getText());
    bd.ejecutar(new Object[]{this.secretaria.getId()});
    this.obj=bd.getObject();
        if (this.obj==null) {
            return true;
        }else{
         if (this.obj[0].equals(this.secretaria.getId())) {
          return false;
         }
        }
        return true;
    }
   /**
    * compruba si no existe la cedula del medico en la base de datos para poder ser agregado si no existe
     * @param frmUsuarios
    * @return retorna un estado booleano
    */
     public boolean comprobarCedulaMedic(frmUsuarios frmUsuarios){
       BasedeDatos bd= new BasedeDatos("SELECT `Cedula` FROM `medicos` WHERE Cedula=?");
       this.medico=new Medicos();
       this.medico.setId(frmUsuarios.getTxtNumCedula().getText());
       bd.ejecutar(new Object[]{this.medico.getId()});
        this.obj=bd.getObject();
        if (this.obj==null) {
            return true;
        }else{
         if (this.obj[0].equals(this.medico.getId())) {
          return false;
         }
        }
        return true;
    }
     /**
      * busca si el medico tiene un cita pendiente ya que no puede ser eliminado por que debe cumplir con su trabajo
      * @param frmUsuarios
      * @return retorna una estado booleano
      */
     public boolean buscarCitaMedico(frmUsuarios frmUsuarios){
     BasedeDatos bd= new BasedeDatos("SELECT * FROM `medicos` JOIN citas ON medicos.Cedula=citas.Medico  WHERE Cedula=?");
        this.medico= new Medicos();
        this.medico.setId(frmUsuarios.getTxtNumCedula().getText());
        bd.ejecutar(new Object[]{this.medico.getId()});
        obj=bd.getObject();
        return obj != null;       
     }
     
     /***
      * elimina un usuario dependiendo su tipo(medico o secretaria) en la base de datos
      * @param frmUsuarios
      * @return retorna un estado booleano
      */
      public boolean eliminar(frmUsuarios frmUsuarios) {
          if (frmUsuarios.getCbUsuario().getSelectedItem().equals("Secretaria")) {
              BasedeDatos bd = new BasedeDatos("DELETE FROM secretarias WHERE Cedula=?");
              this.secretaria=new Secretarios();
              this.secretaria.setId(frmUsuarios.getTxtNumCedula().getText());
            bd.ejecutar(new Object[]{this.secretaria.getId()});
            return true;
          }
          
             if (frmUsuarios.getCbUsuario().getSelectedItem().equals("Medico")) {
                BasedeDatos bd = new BasedeDatos("DELETE FROM medicos WHERE Cedula=?");
                this.medico=new Medicos();
                this.medico.setId(frmUsuarios.getTxtNumCedula().getText());
            bd.ejecutar(new Object[]{this.medico.getId()});
            return true;
          }
          return false;
        }
   /**
    * cambia la contraseña de la secretaria
    * @param frmcambiocont
    * @return retorna un estado booleano
    */
      public boolean cambioContraSec(CambioDeContraseña frmcambiocont){
      BasedeDatos bd= new BasedeDatos("SELECT `Contra` FROM `secretarias` WHERE Usuario=?");
      this.secretaria= new Secretarios();
      this.secretaria.setNombreUsuario(frmcambiocont.getTxtUsuario().getText());
      bd.ejecutar(new Object[]{this.secretaria.getNombreUsuario()});
      this.obj=bd.getObject();
          if (this.obj==null) {
              return false;
          }else{
          if(this.obj[0].equals(frmcambiocont.getTxtContraAntigua().getText())){
          JOptionPane.showMessageDialog(null,"Contraseña correcta");
          BasedeDatos bd2= new BasedeDatos("UPDATE `secretarias` SET `Contra`=? WHERE Usuario=?");
          if(frmcambiocont.getTxtContraNueva().getText().equals(frmcambiocont.getTxtVerificarContra().getText())){
              bd2.ejecutar(new Object[]{frmcambiocont.getTxtContraNueva().getText(),this.secretaria.getNombreUsuario()});
              return true;
          }else{   
          }
          JOptionPane.showMessageDialog(null, "Las contraseña no son iguales");
      }
          }
         return false;
      }
      /**
       * cambia la contraseña del medico
       * @param frmcambiocont
       * @return retorna un estado booleano
       */
     public boolean cambioContraMedic(CambioDeContraseña frmcambiocont){
          BasedeDatos bd= new BasedeDatos("SELECT `Contraseña` FROM `medicos` WHERE Usuario=?");
      this.medico= new Medicos();
      this.medico.setNombreUsuario(frmcambiocont.getTxtUsuario().getText());
      bd.ejecutar(new Object[]{this.medico.getNombreUsuario()});
      this.obj=bd.getObject();
          if (this.obj==null) {
             return false;
         }else{
          if(this.obj[0].equals(frmcambiocont.getTxtContraAntigua().getText())){
          JOptionPane.showMessageDialog(null,"Contraseña correcta");
          BasedeDatos bd2= new BasedeDatos("UPDATE `medicos` SET `Contraseña`=? WHERE Usuario=?");
          if(frmcambiocont.getTxtContraNueva().getText().equals(frmcambiocont.getTxtVerificarContra().getText())){
              bd2.ejecutar(new Object[]{frmcambiocont.getTxtContraNueva().getText(),medico.getNombreUsuario()});
                   return true;
          }   
      }
          }
          return false;
     } 
     /**
      * busca un medico segun su cedula en la base de datos
      * @param frmpbuscar 
      */
       public void buscarMedicoCedu(BuscarUsuario frmpbuscar) {
        if (!"".equals(frmpbuscar.getBuscar().getText())) {
            this.medico= new Medicos();
            this.medico.setId(frmpbuscar.getBuscar().getText());
            BasedeDatos bd = new BasedeDatos("SELECT *  FROM `medicos` WHERE Cedula=?");
            bd.ejecutar(new Object[]{this.medico.getNombreUsuario()});
            DefaultTableModel modelo = (DefaultTableModel) frmpbuscar.getjTable2().getModel();
            modelo.addRow(bd.getObject());
        }
       }
       /**
        * busca una secretaria segun su cedula en la base de datos
        * @param frmpbuscar 
        */
       public void buscarSecretariaCedu(BuscarUsuario frmpbuscar) {
        if (!"".equals(frmpbuscar.getBuscar().getText())) {
            this.secretaria = new Secretarios();
            this.secretaria.setId(frmpbuscar.getBuscar().getText());
            BasedeDatos bd = new BasedeDatos("SELECT *  FROM `secretarias` WHERE Cedula=?");
            bd.ejecutar(new Object[]{this.secretaria.getId()});
            DefaultTableModel modelo = (DefaultTableModel) frmpbuscar.getjTable1().getModel();
            modelo.addRow(bd.getObject());
        }
       }
       /**
        * busca filtrado segun el nombre del medico
        * @param frmpbuscar 
        */
       public void buscarMedicoNom(BuscarUsuario frmpbuscar){
        this.medico= new Medicos();
        this.medico.setNombre(frmpbuscar.getBuscar().getText());
           BasedeDatos bd = new BasedeDatos("SELECT * FROM `medicos` WHERE NombreCompleto LIKE ?"); 
             bd.ejecutar(new Object[]{"%"+this.medico.getNombre()+"%"});
              DefaultTableModel modelo = (DefaultTableModel)
                      frmpbuscar.getjTable2().getModel();
              modelo.setNumRows(0);
             do {
                 this.obj = bd.getObject();
            if (this.obj != null) {
                this.medico = new Medicos(obj);
                modelo.addRow(this.medico.toObject());
            }
        } while (this.obj!=null);
       }
       /**
        * busca filtrando segun el nombre de la secretaria
        * @param frmpbuscar 
        */
       public void buscarSecretariaNom(BuscarUsuario frmpbuscar){
        this.secretaria= new Secretarios();
        this.secretaria.setNombre(frmpbuscar.getBuscar().getText());
           BasedeDatos bd = new BasedeDatos("SELECT * FROM `secretarias` WHERE 	`Nombre Completo` LIKE ?"); 
             bd.ejecutar(new Object[]{"%"+this.secretaria.getNombre()+"%"});
              DefaultTableModel modelo = (DefaultTableModel)
                      frmpbuscar.getjTable1().getModel();
              modelo.setNumRows(0);
             do {
                 this.obj = bd.getObject();
            if (this.obj != null) {
                this.secretaria = new Secretarios(obj);
                modelo.addRow(this.secretaria.toObject());
            }
        } while (this.obj!=null);
       }
    /**
     * cuenta los medicos para saber si se pueden eliminar por que debe de haber 1 almenos
     * @param frmUsuarios
     * @return retonar un estado booleano
     */
       public boolean contarUsuariosDeMedico(frmUsuarios frmUsuarios){
           if (frmUsuarios.getCbUsuario().getSelectedItem()=="Medico") {
               int cont;
           BasedeDatos bd= new BasedeDatos("Select count(*) FROM `medicos` WHERE Cedula=Cedula");
           bd.ejectuar();
           this.obj=bd.getObject();
           cont=Integer.parseInt(obj[0].toString());
               if (cont>0) {
                   return true;
               }
           }
           
           return false;
       }
       /**
        * cuenta las secretarias para saber si se pueden eliminar por que debe de haber 1 almenos
        * @param frmUsuarios
        * @return retorna un estado booleano
        */
       public boolean contarUsuariosDeSecre(frmUsuarios frmUsuarios){
       if (frmUsuarios.getCbUsuario().getSelectedItem()=="Secretaria") {
                    int cont;
           BasedeDatos bd= new BasedeDatos("Select count(*) FROM `secretarias` WHERE Cedula=Cedula");
           bd.ejectuar();
           obj=bd.getObject();
           cont=Integer.parseInt(obj[0].toString());
           if (cont>0) {
               return true;
           }
           }
       return false;
       }
       /**
        * calcula el salario neto del medico
        * @param frmUsuarios
        * @return retorna su salario neto
        */
       public String salarioNeto(frmUsuarios frmUsuarios){
           this.medico= new Medicos();
           this.medico.setSalario(Double.parseDouble(frmUsuarios.getTxtSalario().getText()));
           return this.medico.calcularSalarioNeto();
       }
       /**
        * calcula el salario neto del medico cuando se busca en la base de datos
        * @param frmbuscar
        * @param salario
        * @return retorna su salario
        */
       public String salarioNeto2(BuscarUsuario frmbuscar, String salario){
           this.medico= new Medicos();
           this.medico.setSalario(Double.parseDouble(salario));
           return this.medico.calcularSalarioNeto();
       }
       /**
        * actualiza el usuario segun su tipo(medico o secretaria) en la base de datos
        * @param frmUsuarios
        * @return retorna un estado booleano
        */
       public boolean actualizar(frmUsuarios frmUsuarios){
        if (frmUsuarios.getCbUsuario().getSelectedItem().equals("Secretaria")) {
              BasedeDatos bd = new BasedeDatos("UPDATE `secretarias` SET `Nombre Completo`=?,`Fecha de nacimiento`=?,`Correo electronico`=?,`Telefono`=?,`Usuario`=? WHERE Cedula=?");
                 Date fecha = new Date(frmUsuarios.getJdateFecha().getDate().getTime());
              this.secretaria=new Secretarios(frmUsuarios.getTxtNombreCompleto().getText(), frmUsuarios.getTxtNumCedula().getText(), frmUsuarios.getTxtCorreo().getText(), frmUsuarios.getTxtNumTelefono().getText(), frmUsuarios.getTxtNombreUsuario().getText(), frmUsuarios.getTxtContra().getText(), fecha);
            bd.ejecutar(new Object[]{this.secretaria.getId()});
            return true;
          }
             if (frmUsuarios.getCbUsuario().getSelectedItem().equals("Medico")) {
                BasedeDatos bd = new BasedeDatos("UPDATE `medicos` SET `NombreCompleto`=?,`FechaDeNacimiento`=?,`CorreoElectronico`=?,`CodigoDeColegio`=?,`NumeroTelefono`=?,`Especialidad`=?,`Salario`=?,`Usuario`=? WHERE Cedula=?");
               Date fecha = new Date(frmUsuarios.getJdateFecha().getDate().getTime());
                this.medico=new Medicos(frmUsuarios.getTxtNombreCompleto().getText(), frmUsuarios.getTxtNumCedula().getText(), frmUsuarios.getTxtCorreo().getText(), frmUsuarios.getTxtNumTelefono().getText(), frmUsuarios.getTxtNombreUsuario().getText(), frmUsuarios.getTxtContra().getText(), (String) frmUsuarios.getCbEspecialidad().getSelectedItem(), frmUsuarios.getTxtCodigoCole().getText(),Double.parseDouble(frmUsuarios.getTxtSalario().getText()), fecha);
            bd.ejecutar(new Object[]{this.medico.getNombre(),this.medico.getFechaNacimiento(),this.medico.getCorreo(),this.medico.getCodigoColegio(),this.medico.getTelefono(),this.medico.getEspecialidad(),this.medico.getSalario(),this.medico.getNombreUsuario(),this.medico.getId()});
            return true;
          }
          return false;
       }
       /**
        * inicia sesion el medico para poder atender la cita y hacer el expendite
        * @param frmsesion
        * @return retorna un estado booleano
        */
       public boolean iniciarSesion(InicioDeSesion frmsesion){
          BasedeDatos bd = new BasedeDatos("SELECT `Usuario`,`Contraseña` FROM `medicos` WHERE Usuario=? and Contraseña=?");
          this.medico=new Medicos();
          this.medico.setNombreUsuario(frmsesion.getTxtUsuario().getText());
          this.medico.setContraseña(frmsesion.getContraseña().getText());
          bd.ejecutar(new Object[]{this.medico.getNombreUsuario(),this.medico.getContraseña()});
          this.obj=bd.getObject();
           if (this.obj==null) {
               return false;
           }else{
               if (this.obj[0].equals(this.medico.getNombreUsuario())&&this.obj[1].equals(this.medico.getContraseña())) {
                   return true;
               }
           }
          return false;
       }
       
       /**
        * busca si esta disponible la cedula en la base de datos
        * @param usuarios
        * @return retonar un estado booleano
        */
       public boolean buscarCedulas(frmUsuarios usuarios){
           if (usuarios.getCbUsuario().getSelectedItem()=="Medico") {
        BasedeDatos bd = new BasedeDatos("SELECT `Cedula` FROM `medicos` WHERE Cedula=?"); 
        this.medico= new Medicos();
        this.medico.setId(usuarios.getTxtNumCedula().getText());
        if (this.medico.contarDigitosCedu()==true) {
             bd.ejecutar(new Object[]{this.medico.getId()});
              this.obj=bd.getObject();
        if (this.obj==null) {
            return false;
        }else{
         if (this.obj[0].equals(this.medico.getId())) {
          return true;
         }
        }
        }
           }
           if (usuarios.getCbUsuario().getSelectedItem()=="Secretaria") {
                    BasedeDatos bd = new BasedeDatos("SELECT `Cedula` FROM `secretarias` WHERE Cedula=?"); 
        this.secretaria= new Secretarios();
        this.secretaria.setId(usuarios.getTxtNumCedula().getText());
        if (this.secretaria.contarDigitosCedu()==true) {
             bd.ejecutar(new Object[]{this.secretaria.getId()});
              this.obj=bd.getObject();
        if (this.obj==null) {
            return false;
        }else{
         if (this.obj[0].equals(this.secretaria.getId())) {
          return true;
         }
        }
        }
           }
       return false;
    }
    
}
