package Utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UBean {

	public static ArrayList<Field> obtenerAtributos(Object o) {
		//DEVOLVER TODOS LOS ATRIBUTOS QUE POSEE EL OBJECT
		ArrayList<Field> fields = new ArrayList<Field>();
		Class c = o.getClass();
		
		for(Field f: c.getDeclaredFields()) {
			fields.add(f);
		}
			
		return fields;
	}
	
	public static void ejecutarSet(Object o, String att, Object valor) {
		//EJECUTAR SETTER DEL STRING EN EL OBJECT
		Class c = o.getClass();
		Object[] parametros = new Object[1];
		parametros[0] = valor;
		String nombreSetter = "set".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
		
		for(Method m: c.getDeclaredMethods()) {
			if(nombreSetter.equals(m.getName())) {
				try{
					m.invoke(o, parametros);
					break;
				} 
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
	
		}
	}
	
	public static Object ejecutarGet(Object o, String att) {
		// DEVUELVE EL VALOR DEL ATRIBUTO PASADO POR PARAMETRO, EJECUTANDO EL GETTER DEL
		// OBJECT
		Object retorno = null;
		Class c = o.getClass();
		String nombreGetter = "get".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));

		for (Method m : c.getDeclaredMethods()) {
			if (nombreGetter.equals(m.getName())) {
				try {
					retorno = m.invoke(o);
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}

		return retorno;
	}
}
