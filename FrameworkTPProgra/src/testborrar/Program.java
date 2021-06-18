package testborrar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import Servicios.Consultas;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Program {

	public static void main(String[] args) {
		
		Persona p = new Persona();
		/*
		Test 1a obtenerAtributos
		ArrayList<Field> fields = UBean.obtenerAtributos(p);
		for (Field f : fields) {
			System.out.println(f.getName());
		}
		*/
		/*
		Test 1b ejecutarSet
		UBean.ejecutarSet(p, "id", 8);
		UBean.ejecutarSet(p, "nombre", "Lucia");
		UBean.ejecutarSet(p, "apellido", "Morel");
		*/
		/*
		Test 1c ejecutarGet
		System.out.println(UBean.ejecutarGet(p, "nombre"));
		System.out.println(UBean.ejecutarGet(p, "apellido"));
		*/
		/*
		Test 3a guardar (por ahora solo trae la query armada)
		System.out.println(Consultas.guardar(p));
		*/
		/*
		Test 3b (modificar)
		Persona p = new Persona("Demian", "Murray");
		p.setId(1);
		Consultas.modificar(p);
		*/
		/*
		Test 3c (eliminar)
		Persona p = new Persona();
		p.setId(2);
		Consultas.eliminar(p);
		*/
		/*
		Test 3d (traer por id)
		Persona p = (Persona)Consultas.obtenerPorId(Persona.class, 1);
		System.out.println(p.toString());
		*/
		/*
		Test 7 (traer todos)
		ArrayList<Persona> arr = (ArrayList<Persona>) Consultas.obtenerTodos(Persona.class);
			
		for (Object o : arr) {
			System.out.println(o.toString());
		}
		*/
	}

}
