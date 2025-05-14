/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd;

import Modelo.Cita;
import Modelo.Consulta;
import Modelo.ConsultaEnfermeria;
import Modelo.Paciente;
import Modelo.Personal;
import Utilidades.Encriptado;
import Utilidades.Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Destro
 */
public class Conexion {

    public static Connection conn;

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://susan.hidencloud.com:24651/consultorio", "destro", "DestroXD");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void cerrarConexion() {

        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean acceder(String user, String pass) {
        try {
            String consulta = "SELECT usuario, contrasenya FROM personal WHERE usuario = ? AND contrasenya = ?";

            PreparedStatement pst = conn.prepareStatement(consulta);
            ResultSet rs;

            pst.setString(1, user);
            pst.setString(2, pass);

            pst.execute();

            rs = pst.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static String[] recuperaDatosUser(String user) {
        String[] usuario = new String[3];

        String consulta = "SELECT CONCAT (nombre, ' ', apellidos), numero_colegiado, tipo FROM personal WHERE usuario= '" + user + "'";

        try {
            Statement st = conn.createStatement();
            try (ResultSet rs = st.executeQuery(consulta)) {
                if (rs.next()) {
                    usuario[0] = rs.getString(1);
                    usuario[1] = rs.getString(2);
                    usuario[2] = rs.getString(3);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    }

    public static void cargarcitasMedicas(DefaultTableModel modelo) {

        try {
            Object[] datos = new Object[3];
            String consulta = "SELECT nombre, dia, hora FROM citas where dia = CURRENT_DATE()";

            ResultSet rs = conn.createStatement().executeQuery(consulta);
            while (rs.next()) {
                try {
                    datos[0] = Encriptado.desencriptar(rs.getString("nombre"));
                } catch (Exception ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                datos[1] = rs.getString("dia");
                datos[2] = rs.getString("hora");

                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void cargarcitasEnfermeria(DefaultTableModel modelo) {

        try {
            Object[] datos = new Object[3];
            String consulta = " SELECT nombre, dia, hora FROM citasEnfermeria where dia = CURRENT_DATE()";

            ResultSet rs = conn.createStatement().executeQuery(consulta);
            while (rs.next()) {
                try {
                    datos[0] = Encriptado.desencriptar(rs.getString("nombre"));
                } catch (Exception ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                datos[1] = rs.getString("dia");
                datos[2] = rs.getString("hora");

                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean compruebaDNI(String dni) {
        try {
            String consulta = "SELECT dnipaciente from paciente where dni = '" + dni + "'";

            PreparedStatement pst = conn.prepareStatement(consulta);
            ResultSet rs;

            try {
                pst.setString(1, Encriptado.desencriptar(dni));
            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            rs = pst.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public static Paciente recuperaDatosPaciente(String dni) throws Exception {

        Paciente p = null;

        String consulta = "SELECT dni, nombre, apellidos, telefono, email FROM paciente WHERE dni = ?";

        PreparedStatement pst;
        ResultSet rs;
        try {

            pst = conn.prepareStatement(consulta);
            pst.setString(1, Encriptado.encriptar(dni));
            rs = pst.executeQuery();

            if (rs.next()) {
                p = new Paciente(
                        Encriptado.desencriptar(rs.getString(2)),
                        Encriptado.desencriptar(rs.getString(3)),
                        rs.getInt(4),
                        rs.getString(5)
                );

            }

        } catch (SQLException ex) {

            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

    public static void cargarTablaConsultasMedicas(DefaultTableModel modelo, String dni) {

        try {
            Object[] datos = new Object[4];
            String consulta = " SELECT fechaConsulta, diagnostico, tratamiento, observaciones  from consultas where fechaConsulta = CURRENT_DATE()";

            ResultSet rs = conn.createStatement().executeQuery(consulta);
            while (rs.next()) {

                datos[0] = rs.getString("fechaConsulta");

                datos[1] = rs.getString("diagnostico");
                datos[2] = rs.getString("tratamiento");
                datos[3] = rs.getString("observaciones");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {

            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void cargarTablaConsultasMedicasPorPaciente(DefaultTableModel modelo, String dni) {
        try {
            Object[] datos = new Object[4];
            String consulta = "SELECT fechaConsulta, diagnostico, tratamiento, observaciones FROM consultas WHERE dniPaciente = ?";

            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, Encriptado.encriptar(dni));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("fechaConsulta");
                datos[1] = rs.getString("diagnostico");
                datos[2] = rs.getString("tratamiento");
                datos[3] = rs.getString("observaciones");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarTablaConsultasEnfermeria(DefaultTableModel modelo, String dni) {

        try {
            Object[] datos = new Object[5];
            String consulta = " SELECT fechaConsulta, tensionMax, tensionMin, glucosa, peso  from enfermeria where fechaConsulta = CURRENT_DATE()";

            ResultSet rs = conn.createStatement().executeQuery(consulta);
            while (rs.next()) {

                datos[0] = rs.getString("fechaConsulta");

                datos[1] = rs.getString("tensionMax");
                datos[2] = rs.getString("tensionMax");
                datos[3] = rs.getString("glucosa");
                datos[4] = rs.getString("peso");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean registrarConsultaMedica(Consulta c) {

        try {
            String consulta = "INSERT INTO consultas (dniPaciente, fechaConsulta, diagnostico, tratamiento, "
                    + "observaciones, codigofacultativo) "
                    + "values (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(consulta);
            try {
                pst.setString(1, Encriptado.encriptar(c.getDniPaciente()));
                pst.setDate(2, new java.sql.Date(c.getFechaConsulta().getTime()));
                pst.setString(3, c.getDiagnostico());
                pst.setString(4, c.getTratamiento());
                pst.setString(5, c.getObservaciones());
                pst.setInt(6, c.getCodigofacultativo());

            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            pst.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public static boolean registrarConsultasEnfermeria(ConsultaEnfermeria c) {

        try {
            String consulta = "INSERT INTO enfermeria(dniPaciente, fechaConsulta, tensionMax, tensionMin, "
                    + "glucosa, peso,  codigoFacultativo) "
                    + "values (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(consulta);
            try {
                pst.setString(1, Encriptado.encriptar(c.getDniPaciente()));
                pst.setDate(2, new java.sql.Date(c.getFechaConsulta().getTime()));
                pst.setDouble(3, c.getMaxima());
                pst.setDouble(4, c.getMinima());
                pst.setInt(5, c.getGlucosa());
                pst.setDouble(6, c.getPeso());
                pst.setInt(7, c.getCodigoFacultativo());

            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            pst.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public static boolean nuevaCitaMedica(Cita c) throws Exception {

        String consultaInsert = "INSERT INTO citas (dniPaciente, nombre, dia, hora) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(consultaInsert);

            st.setString(1, Encriptado.encriptar(c.getDniPaciente()));
            st.setString(2, Encriptado.encriptar(c.getNombre()));
            st.setDate(3, new java.sql.Date(c.getDia().getTime()));
            st.setDouble(4, c.getHora());

            st.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error al insertar la cita de enfermería: " + ex.getMessage());
        }
        return false;
    }

    public static boolean registrarPersonal(Personal p) throws Exception {

        String consultaInsert = "INSERT INTO personal (numero_colegiado, nombre, apellidos, telefono, usuario, contrasenya, tipo)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(consultaInsert)) {
            st.setString(1, String.valueOf(p.getNumero_colegiado()));
            st.setString(2, p.getNombre());
            st.setString(3, p.getApellidso());
            st.setString(4, String.valueOf(p.getTelefono()));
            st.setString(5, p.getUsuario());
            st.setString(6, Encriptado.encriptar(p.getContrasenya()));
            st.setString(7, p.getTipo());

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static boolean registrarCitaEnfermeria(Cita c) {
        String consultaInsert = "INSERT INTO citasEnfermeria (dniPaciente, nombre, dia, hora) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(consultaInsert);
            st.setString(1, c.getDniPaciente());
            st.setString(2, c.getNombre());
            st.setDate(3, new java.sql.Date(c.getDia().getTime()));
            st.setString(4, String.valueOf(c.getHora()));

            st.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error al insertar la cita: " + ex.getMessage());
        }

        return false;
    }

    public static void cargarDatosPacientes(DefaultTableModel modelo) {

        try {
            Object[] datos = new Object[5];

            String consulta = "SELECT dni, nombre, apellidos, telefono, cp FROM paciente ";

            ResultSet rs = conn.createStatement().executeQuery(consulta);
            while (rs.next()) {
                try {
                    datos[0] = Encriptado.desencriptar(rs.getString("dni"));
                    datos[1] = Encriptado.desencriptar(rs.getString("nombre"));
                    datos[2] = Encriptado.desencriptar(rs.getString("apellidos"));
                } catch (Exception ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }

                datos[3] = rs.getString("telefono");
                datos[4] = rs.getString("cp");

                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean actualizaDatos(String[] datosPaciente, String dni) {

        try {
            String consultasUpdate = "UPDATE paciente set nombre=?, apellidos=?, telefono=?, cp=? WHERE dni=?";

            PreparedStatement stat = conn.prepareStatement(consultasUpdate);

            try {
                stat.setString(1, Encriptado.encriptar(datosPaciente[0])); // nombre
                stat.setString(2, Encriptado.encriptar(datosPaciente[1])); // apellidos
                stat.setString(5, Encriptado.encriptar(dni));
            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            stat.setInt(3, Integer.parseInt(datosPaciente[2])); // telefono
            stat.setInt(4, Integer.parseInt(datosPaciente[3])); // cp

            stat.executeUpdate();
            stat.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void cargarCp(JComboBox cp) {

        try {
            String consulta = "SELECT codigopostal FROM codigospostales";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                cp.addItem(rs.getString("codigopostal"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean registrarPaciente(Paciente p) {

        String consultaInsert = "INSERT INTO paciente (dni, nombre, apellidos, fechaNacimiento, telefono, email, cp, sexo, tabaquismo, consumoAlcohol, antecedentesSalud, datosSaludGeneral, fechaRegistro)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(consultaInsert)) {
            try {
                st.setString(1, Encriptado.encriptar(p.getDni()));
                st.setString(2, Encriptado.encriptar(p.getNombre()));
                st.setString(3, Encriptado.encriptar(p.getApellidos()));
            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            st.setDate(4, new java.sql.Date(p.getFechaNacimiento().getTime()));

            st.setInt(5, p.getTelefono());
            st.setString(6, p.getEmail());
            st.setInt(7, p.getCp());
            st.setString(8, p.getSexo());
            st.setString(9, p.getTabaquismo());
            st.setString(10, p.getConsumoalchol());
            st.setString(11, p.getAntecedentesSalud());
            st.setString(12, p.getDatosSaludGeneral());
            st.setDate(13, new java.sql.Date(p.getFechaNacimiento().getTime()));

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
}
