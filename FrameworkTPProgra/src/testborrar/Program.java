package testborrar;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Servicios.Consultas;
import Utilidades.UBean;

public class Program {

	public static void main(String[] args) {
		Persona p = new Persona();

		// Test 1a obtenerAtributos
		ArrayList<Field> fields = UBean.obtenerAtributos(p);

		for (Field f : fields) {
			System.out.println(f.getName());
		}

		// Test 1b ejecutarSet
		UBean.ejecutarSet(p, "nombre", "Alan");
		UBean.ejecutarSet(p, "apellido", "Passucci");

		// Test 1c ejecutarGet
		System.out.println(UBean.ejecutarGet(p, "nombre"));
		System.out.println(UBean.ejecutarGet(p, "apellido"));

		// Test 3a guardar (por ahora solo trae la query armada)
		 System.out.println(Consultas.guardar(p));
	}

}
