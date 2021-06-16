package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Properties.Propiedades;


public class UConexion {
	public static Connection c;
	private static UConexion conObjeto;
	
	private UConexion() {
	}
	
	public static UConexion generarObjeto() {
		if (conObjeto == null) {
			conObjeto = new UConexion();
		}
		
		return conObjeto;
	}
	
	
	
	public static void abrirConexion() {
		Propiedades prop = Propiedades.generarObjeto();
		
		try {
			Class.forName(prop.getDriver());
			c = DriverManager.getConnection(prop.getUbicacionDB(), prop.getUsuario(), prop.getContrasenia());
			System.out.println("Se conecto!!!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void cerrarConexion() {
		try {
			c.close();
			c = null;
			System.out.println("Se desconecto!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
