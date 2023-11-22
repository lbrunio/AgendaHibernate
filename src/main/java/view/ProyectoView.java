package view;

import java.util.List;

import io.IO;
import model.Proyecto;


public class ProyectoView {

	final List<String> options = List.of(
			"Add proyecto", 
			"Remove proyecto", 
			"Update proyecto", 
			"Show alL", 
			"Search by Id",
			"Search by Name", 
			"Exit");

	/** get Opcion
	 * 
	 * @return Letra del menu en mayuscula
	 */
	public Character getOption() {
		IO.println("Projects: " + options);
		return Character.toUpperCase(IO.readChar());
	}
	
	/** Mostrar proyecto
	 * 
	 * @param project objeto proyecto
	 */
	public void show(Proyecto project) {
	    IO.println(project.show());
	}

	/** Mostrar todos los proyectos
	 * 
	 * @param projects lista de proyectos
	 */
	public void show(List<Proyecto> projects) {
	    for (Proyecto proyecto : projects) {
	        IO.println(proyecto.show());
	    }
	}

	/** Modificar proyecto
	 * 
	 * @param p objeto proyecto
	 * @return proyecto
	 */
	public Proyecto update(Proyecto p) {
		if (p == null) {
			IO.println("Project not found");
			return null;
		}
		IO.printf("Change project name [%s] to ? ", p.getNombre());
		String name = IO.readString();
		if (!name.isBlank()) p.setNombre(name);
		
		return p;
	}

	/** Buscar por inicio nombre
	 * 
	 * @return string introducido
	 */
	public String searchInitialName() {
		IO.print("Name starts with ? ");
		return IO.readString();
	}

	/** Buscar por ID
	 * 
	 * @return int introducido
	 */
	public int searchByCode() {
		IO.print("ID ? ");
		return IO.readInt();
	}


	/** Muestra un mensaje 
	 * 
	 * @param mensaje 
	 */
	public void result(String str) {
		IO.println(str);
	}
	
	
	/** Add proyecto
	 * 
	 * @return proyecto
	 */
	public Proyecto add() {
		IO.print("Name ? ");
		String name = IO.readString();
		Proyecto p = Proyecto.builder().nombre(name).build();
		return p;
	}




}
