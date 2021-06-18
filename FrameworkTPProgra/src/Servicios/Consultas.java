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
import testborrar.Persona;

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
	
	public static void modificar(Object o) {
		//Modifica todo excepto ID (se usara en el where)
		try {
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			
			UConexion con = UConexion.generarObjeto();
			con.abrirConexion();
			String query = "UPDATE " + o.getClass().getAnnotation(Tabla.class).nombre() + " SET ";
			String id = null;
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					 id = " WHERE " + f.getName().toString() + " = " + UBean.ejecutarGet(o, f.getName().toString());
				}
				else {
					if(f.getType() == String.class) {
						query += f.getName().toString() + " = '" + UBean.ejecutarGet(o, f.getName().toString()) + "', ";
					}
					else {
						query += f.getName().toString() + " = " + UBean.ejecutarGet(o, f.getName().toString()) + ", ";
					}
				}
			}
			query = query.substring(0, query.length()-2);
			query += " ";
			query += id;
			PreparedStatement pst = (PreparedStatement) UConexion.c
					.prepareStatement(query);
	
			if(pst != null) {
				pst.execute();
			}

			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminar(Object o) {
		try {
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			
			UConexion con = UConexion.generarObjeto();
			con.abrirConexion();
			PreparedStatement pst = null;
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					pst = (PreparedStatement) UConexion.c
							.prepareStatement("DELETE FROM " + o.getClass().getAnnotation(Tabla.class).nombre() + " WHERE " + f.getName().toString() + " = " + UBean.ejecutarGet(o, f.getName().toString()));
					break;
				}
			}
			
			if(pst != null) {
				pst.execute();
			}

			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object obtenerPorId(Class<?> c, Object o) {
		Object obtenido = null;

		try {
			Constructor[] constructores = c.getConstructors();
			for (Constructor con1 : constructores) {
				if (con1.getParameterCount() == 0) {
					obtenido = con1.newInstance();
					break;
				}
			}
			ArrayList<Field> fields = UBean.obtenerAtributos(obtenido);
			UConexion con = UConexion.generarObjeto();
			con.abrirConexion();
			
			PreparedStatement pst = null;
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					pst = (PreparedStatement) UConexion.c
							.prepareStatement("SELECT * FROM " + c.getAnnotation(Tabla.class).nombre() + " WHERE " + f.getName().toString() + " = " + o.toString());
				break;
				}
			}
			
			
			pst = (PreparedStatement) UConexion.c
					.prepareStatement("SELECT * FROM " + c.getAnnotation(Tabla.class).nombre() + " WHERE id = " + o.toString());
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				for (Field f : fields) {
					if (f.getAnnotation(Id.class) == null) {
						UBean.ejecutarSet(obtenido, f.getName().toString(), rs.getString(i));
					} else {
						UBean.ejecutarSet(obtenido, f.getName().toString(), rs.getInt(i));
					}

					i++;
				}
				break;
			}	
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obtenido;
	}
}
