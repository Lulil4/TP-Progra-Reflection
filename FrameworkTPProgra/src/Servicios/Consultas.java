package Servicios;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import Utilidades.UBean;

public class Consultas {

		public static String guardar(Object o) {
			//GUARDA CON INSERT EL OBJETO EN LA DB. 
			//DEVUELVE EL INSERT FORMADO
			String query = "insert into ";
			Class c = o.getClass();
			query+= c.getName() + " (";
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			
			for(Field f: fields) {
				query+= f.getName() + ", ";
			}
			
			query = query.substring(0, query.length() - 2);
			query += ") values (";
			
			for(Field f: fields) {
				query += UBean.ejecutarGet(o, f.getName()).toString() + ", ";
			}
			
			query = query.substring(0, query.length() - 2);
			query+= ")";
			
			return query;
		}
}
