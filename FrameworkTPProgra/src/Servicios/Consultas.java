package Servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import Anotaciones.Id;
import Anotaciones.Tabla;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Consultas {

	public static String guardar(Object o) {
		// GUARDA CON INSERT EL OBJETO EN LA DB.
		// DEVUELVE EL INSERT FORMADO
		String query = "insert into ";
		query += o.getClass().getAnnotation(Tabla.class).nombre() + " (";
		ArrayList<Field> fields = UBean.obtenerAtributos(o);

		for (Field f : fields) {
			if (f.getAnnotation(Id.class) == null) {
				query += f.getName() + ", ";
			}
		}

		query = query.substring(0, query.length() - 2);
		query += ") values (";

		for (Field f : fields) {
			if (f.getAnnotation(Id.class) == null) {
				query += "'" + UBean.ejecutarGet(o, f.getName()).toString() + "' , ";
			}
		}

		query = query.substring(0, query.length() - 2);
		query += ")";

		try {
			UConexion c = UConexion.generarObjeto();
			UConexion.abrirConexion();
			PreparedStatement pst = (PreparedStatement) UConexion.c.prepareStatement(query);
			pst.execute();
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return query;
	}

	public static ArrayList<?> obtenerTodos(Class<?> c) {
		// GUARDA CON INSERT EL OBJETO EN LA DB.
		// DEVUELVE EL INSERT FORMADO
		ArrayList<Object> lista = new ArrayList<Object>();
		Object o = null;

		try {
			Constructor[] constructores = c.getConstructors();
			for (Constructor con1 : constructores) {
				if (con1.getParameterCount() == 0) {
					o = con1.newInstance();
					break;
				}
			}
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			UConexion con = UConexion.generarObjeto();
			con.abrirConexion();
			PreparedStatement pst = (PreparedStatement) UConexion.c
					.prepareStatement("SELECT * FROM " + c.getAnnotation(Tabla.class).nombre());
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				for (Field f : fields) {

					if (f.getAnnotation(Id.class) == null) {
						UBean.ejecutarSet(o, f.getName().toString(), rs.getString(i));
					} else {
						UBean.ejecutarSet(o, f.getName().toString(), rs.getInt(i));
					}

					i++;
				}
				
				lista.add(c.cast(o));
				
				constructores = c.getConstructors();
				for (Constructor con1 : constructores) {
					if (con1.getParameterCount() == 0) {
						o = con1.newInstance();
						break;
					}
				}
				i = 1;
			}	
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
}
