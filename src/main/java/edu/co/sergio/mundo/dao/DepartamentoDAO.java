package edu.co.sergio.mundo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.co.sergio.mundo.vo.Departamento;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Isabel-Fabian
 * @since 12/08/2015
 * @version 2
 * Clase que permite la gestion de la tabla Depto en la base de datos.
 * 
 * CREATE TABLE Depto(
 *     id_depto integer,
 *     nom_depto varchar(40),
 *     PRIMARY KEY(id_depto)
 * );
 */
 

public class DepartamentoDAO implements IBaseDatos<Departamento> {

	/**
	 * Funcion que permite obtener una lista de los departamentos existentes en la base de datos
	 * @return List<Departamento> Retorna la lista de Departamentos existentes en la base de datos
	 */
    
        public ArrayList recursosPorProyecto(){
            ArrayList registros = null;
            String query = "SELECT nom_proy, count(*) as total FROM Proyecto LEFT JOIN Recurso using (id_proyecto) group by nom_proy";
            
            Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Statement st;
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
	    	if(registros == null){
	    		registros= new ArrayList<Departamento>();
	    	}
	      
                String nombreProyecto = rs.getString("nom_proy");
                registros.add(nombreProyecto);
                int contador = rs.getInt("total");
                registros.add(contador);
	    }
	    st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return registros;
        }
        
        public ArrayList departamentosConProyectos(){
            ArrayList registros = null;
            
            String query = "SELECT nom_depto, count(*) as num FROM (Depto JOIN DeptoProyecto using (id_depto)) JOIN Proyecto using (id_proyecto) group by nom_depto";
            
            Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Statement st;
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
	    	if(registros == null){
	    		registros= new ArrayList<Departamento>();
	    	}
	      
                String nombreDepartamento = rs.getString("nom_depto");
                registros.add(nombreDepartamento);
                int contador = rs.getInt("num");
                registros.add(contador);
	    }
	    st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return registros;
        }
        
        public ArrayList VariosEmpleadosConElMismoContrato(){
            ArrayList registros = null;
            
            String query = "SELECT nom_depto, tipo_contrato, count(*) as total FROM Depto JOIN Empleado using (id_depto) group by nom_depto, tipo_contrato having count(*)  > 1";
            
            Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Statement st;
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
	    	if(registros == null){
	    		registros= new ArrayList<Departamento>();
	    	}
	      
                String nombreDepartamento = rs.getString("nom_depto");
                registros.add(nombreDepartamento);
                String tipoContrato = rs.getString("tipo_contrato");
                registros.add(tipoContrato);
                int contador = rs.getInt("total");
                registros.add(contador);
	    }
	    st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return registros;
        }
    
	public List<Departamento> findAll() {
		List<Departamento> departamentos= null;
	    String query = "SELECT * FROM Depto";
	    Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    int id =0;
	    String nombre = null;
	
	    while (rs.next()){
	    	if(departamentos == null){
	    		departamentos= new ArrayList<Departamento>();
	    	}
	      
	        Departamento registro= new Departamento();
	        id = rs.getInt("id_depto");
	        registro.setId_departamento(id);
	        
	        nombre = rs.getString("nom_depto");
	        registro.setNom_departamento(nombre) ;
	        
	        departamentos.add(registro);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al obtener la lista de Departamentos");
			e.printStackTrace();
		}
	    
	    return departamentos;
	}

	
	/**
	 * Funcion que permite realizar la insercion de un nuevo registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de insercion es exitosa.
	 */
	public boolean insert(Departamento t) {
		boolean result=false;
		Connection connection=null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    String query = " insert into Depto (id_depto,nom_depto)"  + " values (?,?)";
        PreparedStatement preparedStmt=null;
	    try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt (1, t.getId_departamento());
                        preparedStmt.setString (2, t.getNom_departamento());
			result= preparedStmt.execute();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Funcion que permite realizar la actualizacion de un nuevo registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de actualizacion es exitosa.
	 */
	public boolean update(Departamento t) {
		boolean result=false;
		Connection connection= null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		String query = "update Depto set nom_depto = ? where id_depto = ?";
		PreparedStatement preparedStmt=null;
		try {
		    preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString(1, t.getNom_departamento());
                    preparedStmt.setInt   (2, t.getId_departamento());
		    if (preparedStmt.executeUpdate() > 0){
		    	result=true;
		    }
			    
		} catch (SQLException e) {
				e.printStackTrace();
		}
			
		return result;
	}

	/**
	 * Funcion que permite realizar la eliminario de registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de borrado es exitosa.
	 */
	public boolean delete(Departamento t) {
	   boolean result=false;
	   Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	   String query = "delete from Depto where id_depto = ?";
	   PreparedStatement preparedStmt=null;
	   try {
		     preparedStmt = connection.prepareStatement(query);
		     preparedStmt.setInt(1, t.getId_departamento());
		    result= preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	   return result;
	}
}
