package Modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose,Marco,Yuliana,Elver
 */
public class BasedeDatos {

    private static Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet datos;
    private static String ip;
    private static String usuario;
    private static String pass;
    private static String bd;
    private ArchivoIniC ini;
    protected String mesaje;
    private String ruta;
    private String nombre;

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getMesaje() {
        return mesaje;
    }

    public static String getBd() {
        return bd;
    }

    public static String getIp() {
        return ip;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static String getPass() {
        return pass;
    }

    public BasedeDatos(String ip, String usuario, String pass, String bd, String ruta) {
        this.ip = ip;
        this.usuario = usuario;
        this.pass = pass;
        this.bd = bd;
        this.ruta = ruta;
        this.conectar();
    }

    public BasedeDatos() {
        this.conectar();
    }

    public BasedeDatos(String ruta, String nombre) {

    }

    public BasedeDatos(String sql) {
        this.conectar();
        this.setSentencia(sql);
    }

    /**
     * se conecta a la base de datos mediante archivo ini
     *
     * @return retorna un string verificador de casos: conecto,existe o fallo
     */
    private String conectar() {
        if (this.conexion == null) {
            try {
                ini = new ArchivoIniC();
                ini.leerArchivo(ruta + "\\Configuracion.ini");
                Class.forName("com.mysql.jdbc.Driver");
                this.conexion = DriverManager.getConnection("jdbc:mysql://localhost/consultoriomedico?useServerPrepStmts=true", "root", "johansel");

                return "Conecto";
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver no cargado");
                return "Fallo";
            } catch (SQLException ex) {
                System.out.println("Error al conectar");
                return "Fallo";
            }
        }
        return "Existe";
    }
//    ("jdbc:mysql://" + ini.getProperties().getProperty("IP", "default value")
//                        + "/" + ini.getProperties().getProperty("BD", "default value") + "?useServerPrepStmts=true",
//                        ini.getProperties().getProperty("Usuario", "default value"),
//                        ini.getProperties().getProperty("Pass", "default value"));

    public boolean setSentencia(String sql) {
        try {
            this.sentencia = this.conexion.prepareStatement(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
        } finally {
            return false;
        }
    }

    public boolean setParametros(Object parametro[]) {
        for (int i = 0; i < parametro.length; i++) {
            if (parametro[i] instanceof java.lang.String) {
                try {
                    this.sentencia.setString(i + 1, parametro[i].toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
                    return false;
                }
            }
            if (parametro[i] instanceof java.lang.Integer) {
                try {
                    this.sentencia.setInt(i + 1, Integer.parseInt(parametro[i].toString()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
                    return false;
                }
            }
            if (parametro[i] instanceof java.lang.Double) {
                try {
                    this.sentencia.setDouble(i + 1, Double.parseDouble(parametro[i].toString()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
                    return false;
                }
            }
            if (parametro[i] instanceof java.util.Date) {
                try {
                    this.sentencia.setDate(i + 1, (Date) parametro[i]);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * proceso para obtener un array de objetos con los resultados del resulset
     *
     * @return retrona un array de objetos
     */
    public Object[] getObject() {
        try {
            if (this.datos.next()) {
                ArrayList<Object> obj = new ArrayList();
                for (int i = 1; i <= this.datos.getMetaData().getColumnCount(); i++) {
                    obj.add(this.datos.getObject(i));
                }
                return obj.toArray();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BasedeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean ejecutar(Object[] parametro) {
        this.setParametros(parametro);
        return this.ejectuar();
    }

    public boolean ejectuar() {
        try {
            if (this.sentencia.execute()) {
                this.datos = this.sentencia.getResultSet();
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
        }
        return false;
    }

    /**
     * metodo para comprobar la conexion
     *
     * @return retorna un string verificador: encendido o apagado
     */
    public String comprobar() {
        if (this.conectar() == "Existe") {
            return "Encendido";
        }
        return "Apagado";
    }

    /**
     * metodo para apagar el servidor
     *
     * @return retorna un estado booleano
     */
    public boolean apagarservidor() {
        try {
            conexion.close();
            this.conexion = null;
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getErrorCode() + " " + " " + ex.getMessage());
        }
        return false;
    }

    /**
     * metodo en enciende de nuevo el servidor
     *
     * @return retorna un estado booleano
     */
    public Boolean encender() {
        this.ini = new ArchivoIniC(this.ruta);
        System.out.println(ruta);
        this.ini.limpiar();
        this.ini.escribir("[Configuracion]");
        this.ini.escribir("BD=" + this.bd);
        this.ini.escribir("IP=" + this.ip);
        this.ini.escribir("Usuario=" + this.usuario);
        this.ini.escribir("Pass=" + this.pass);
        this.ini.escribir("ruta=" + this.ruta);
        this.ini.guardar();
        this.ini.cerrar();
        this.conexion = null;
        if (this.conectar() == "Conecto") {
            return true;
        }
        return false;
    }

}
