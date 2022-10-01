/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laboral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class EmpleadosFich {

    public static List<Empleado> obtieneEmpleado() throws SQLException, DatosNoCorrectosException {
        Connection con = DBUtils.getConnection();
        ArrayList<Empleado> listaEmple = new ArrayList<Empleado>();
        Empleado emp;

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select Nombre, DNI, Sexo, Categoria, Anyos  from Empleados");

            while (rs.next()) {
                emp = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3).toCharArray()[0], rs.getInt(4), rs.getInt(5));
                listaEmple.add(emp);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listaEmple;

    }

    public static List<String[]> obtieneNomin() throws SQLException {
        Connection con = DBUtils.getConnection();
        Statement st = null;
        ResultSet rs = null;

        ArrayList<String[]> listaNominas = new ArrayList<String[]>();
        String[] nom = new String[2];

        try {

            st = con.createStatement();
            rs = st.executeQuery("Select DNI, Nomina  from Nominas");

            while (rs.next()) {
                nom[0] = rs.getString(1);
                nom[1] = String.valueOf(rs.getInt(2));
                listaNominas.add(nom);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

        }

        return listaNominas;

    }

    public static void altaEmpleado(Empleado emp) throws SQLException {

        Connection con = DBUtils.getConnection();
        Statement st = null;

        st = con.createStatement();
        st.execute("Insert into Empleados(Nombre, DNI, Sexo,Categoria, Anyos) values ('" + emp.nombre + "','" + emp.dni + "','" + emp.sexo + "','" + emp.getCategoria() + "','" + emp.anyos + "')");
        st.execute("Insert into Nominas(DNI, Nomina) values ('" + emp.dni + "','" + Nomina.sueldo(emp) + "')");

        backup(emp);
        st.close();
    }

    public static void altaEmpleado(String nombre, String dni, char sexo, int anyos, int categoria) throws SQLException {
        try {
            Empleado emp = new Empleado(nombre, dni, sexo, categoria, anyos);
            altaEmpleado(emp);

        } catch (DatosNoCorrectosException e) {
            e.getMessage();
        }

    }

    public static void altaEmpleado(String fichero) throws FileNotFoundException, IOException, DatosNoCorrectosException, SQLException {
        String linea;
        Empleado emp;
        String[] infoEmp;

        File entrada = new File(fichero);
        FileReader fr = new FileReader(entrada);
        BufferedReader br = new BufferedReader(fr);

        while (br.ready()) {
            linea = br.readLine();
            infoEmp = linea.split(";");
            if (infoEmp.length == 3) {
                emp = new Empleado(infoEmp[0], infoEmp[1], infoEmp[2].toCharArray()[0]);
            } else {
                emp = new Empleado(infoEmp[0], infoEmp[1], infoEmp[2].toCharArray()[0], Integer.parseInt(infoEmp[3]), Integer.parseInt(infoEmp[4]));
            }
            altaEmpleado(emp);
            updateNomina(emp.dni);

        }
        br.close();

    }

    public static int getSueldo(String dni) throws SQLException {
        //Conexón a la base de datos
        Connection con = DBUtils.getConnection();
        int sueldo = -1;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select nomina from nominas where dni='" + dni + "'");

            if (rs != null) {
                while (rs.next()) {
                    sueldo = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

        }

        return sueldo;

    }

    public static boolean emplExist(String dni) throws SQLException {
        //Conexón a la base de datos
        Connection con = DBUtils.getConnection();
        Empleado emp = null;
        Statement st = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            st = con.createStatement();
            rs = st.executeQuery("Select count(*) from empleados where dni='" + dni + "'");

            if (rs != null) {
                existe = true;
            }

        } catch (SQLException ex) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

            return existe;
        }
    }

    public static Empleado getEmpleado(String dni) throws SQLException {

        Connection con = DBUtils.getConnection();
        Empleado emp = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("Select Nombre, DNI, Sexo, Categoria, Anyos from empleados");

            while (rs.next()) {
                emp = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3).toCharArray()[0], rs.getInt(4), rs.getInt(5));
            }

        } catch (SQLException | DatosNoCorrectosException ex) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

            return emp;
        }

    }

    public static void  updateEmpleado(Empleado emp) throws SQLException {
        Connection con = DBUtils.getConnection();
        Statement st = null;

        try {
            st = con.createStatement();
            st.execute("Update Empleados emp set emp.nombre='" + emp.nombre + "' and emp.sexo='" + emp.sexo + "' and emp.categoria=" + emp.getCategoria() + " and emp.anyos=" + emp.anyos + " where emp.dni='" + emp.dni + "'");
            st.execute("Update nominas set Nomina=" + Nomina.sueldo(emp) + " where dni='" + emp.dni + "'");

        } catch (SQLException ex) {

        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

        }

    }

    public static void updateNomina(String dni) throws SQLException {

        Connection con = DBUtils.getConnection();
        Statement st = null;

        try {
            st = con.createStatement();
            st.execute("update nominas set sueldo=" + Nomina.sueldo(getEmpleado(dni)) + " where dni='" + dni + "'");

        } catch (SQLException ex) {

        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

        }

    }
    
    public static void deleteEmpleado(String dni) throws SQLException{
        
        Connection con = DBUtils.getConnection();
        Statement st = null;

        try {
            st = con.createStatement();
            st.execute("delete from empleados emp  where emp.dni='"+ dni +"'");

        } catch (SQLException ex) {

        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                DBUtils.close(con);
            } catch (SQLException e) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }

        }
        
        
    }
    
    public static void backup(Empleado emp){
        
        File fichBackup = new File("backupEmp.txt");
        try {
	        FileWriter fw = new FileWriter(fichBackup.getAbsoluteFile(),true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	      	bw.write(emp.nombre+"; "+emp.dni+"; "+emp.sexo+"; "+emp.getCategoria()+"; "+emp.anyos+"; "+Nomina.sueldo(emp)+'\n');
	        
                bw.close();
	        fw.close();
    	}catch (FileNotFoundException ex) {
    		      System.out.println("El fichero no se encuentra");
    	}catch (IOException ex) {
    		      System.out.println("Error al leer el fichero");
    	}   
       
    }
}
