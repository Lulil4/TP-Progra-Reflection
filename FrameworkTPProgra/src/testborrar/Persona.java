package testborrar;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;

@Tabla(nombre = "personas")
public class Persona {
	@Id
	@Columna(nombre = "id")
	private Integer id;
	@Columna(nombre = "nombre")
	private String nombre;
	@Columna(nombre = "apellido")
	private String apellido;
	
	public Persona() {}

	public Persona(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	  @Override
	    public boolean equals(Object obj) {
	        boolean retorno = false;
	        if (obj != null){
	            if (this.getClass().equals(obj.getClass())){
	                Persona p = (Persona) obj;
	                if (this.id.equals(p.id) && this.apellido.equals(p.apellido)){
	                    retorno = true;
	                }
	            }
	        }
	        return retorno;
	    }

	    @Override
	    public int hashCode() {
	        int retorno = 0;
	        final int primo = 21;
	        retorno = primo*this.id + this.apellido.hashCode();
	        return retorno;
	    }

	    @Override
	    public String toString() {
	        String persona = "";
	        persona = persona.concat("ID: ".concat(this.id.toString()).concat(" - "));
	        persona = persona.concat("Apellido: ".concat(this.apellido).concat(" - "));
	        persona = persona.concat("Nombre: ".concat(this.nombre));
	        return persona;
	    }
}
