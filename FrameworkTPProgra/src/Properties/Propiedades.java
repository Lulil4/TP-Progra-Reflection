package Properties;

import java.util.ResourceBundle;

public class Propiedades {
	private static Propiedades propObjeto;
	private static String driver;
	private static String ubicacionDB;
	private static String usuario;
	private static String contrasenia;
	
	private Propiedades() {
	}
	
	public static Propiedades generarObjeto() {
		if (propObjeto == null) {
			propObjeto = new Propiedades();
			
			ResourceBundle rb = ResourceBundle.getBundle("Properties.framework");
			driver = rb.getString("DRIVER");
			ubicacionDB = rb.getString("UBICACIONDB");	
			usuario = rb.getString("USUARIO");
			contrasenia = rb.getString("CONTRASENIA");	
		}
		
		return propObjeto;
	}

	public static String getUsuario() {
		return usuario;
	}

	public static String getContrasenia() {
		return contrasenia;
	}

	public static String getUbicacionDB() {
		return ubicacionDB;
	}

	public static String getDriver() {
		return driver;
	}
	
}
