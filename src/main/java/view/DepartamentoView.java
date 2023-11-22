package view;

import java.util.List;

import io.IO;
import model.Departamento;
import model.Empleado;

public class DepartamentoView {

	final List<String> options = List.of(
			"Add empleado", 
			"Remove empleado", 
			"Update empleado", 
			"Show alL", 
			"Search by Id",
			"Search by Name", 
			"Exit");

	/** Opciones del departameno
	 * 
	 * @return opcion
	 */
	public Character getOption() {
		IO.println("Departments : " + options);
		return Character.toUpperCase(IO.readChar());
	}
	
	/** Mostrar todos los departamentos
	 * 
	 * @param departments lista de departamentos
	 */
	public void show(List<Departamento> departments) {
		for (Departamento d : departments) {
			IO.println(d.show());
		}
	}

	/** Mostrar los empleados de departamento
	 * 
	 * @param d departamento
	 * @param employees lista de empleados
	 */
	public void show(Departamento d, List<Empleado> employees) {
		if (d == null) {
			return;
		}
		IO.println(d.show());
		IO.println("* Departments employees :");
		for (Empleado e : employees) {
			IO.println(e.show());
		}
	}

	/** Actualizar departamento
	 * 
	 * @param d departamento
	 * @return departamento cambiado 
	 */
	public Departamento update(Departamento d) {
		if (d == null) {
			IO.println("Department not found");
			return null;
		}
		IO.printf("Change department name [%s] to ? ", d.getNombre());
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			d.setNombre(nombre);
		}
		IO.printf("Change head [%s] to ? ", d.getJefe() == null ? "Head not found" : d.getJefe().show());
		Integer jefe = IO.readIntOrNull();
		if (jefe != null) {
			d.setJefe(Empleado.builder().id(jefe).build());
		}
		return d;
	}

	
	
	/** Buscar por inicial
 	 * 
	 * @return departamento
	 */
	public String searchByNameInitial() {
		IO.print("Name starts with ? ");
		return IO.readString();
	}

	
	/** Busacar por ID
	 * 
	 * @return departamento
	 */
	public int searchByCode() {
		IO.print("ID ? ");
		return IO.readInt();
	}


	public void result(String str) {
		IO.println(str);
	}
	
	/** Add departamento 
	 * 
	 * @return departamento
	 */
	public Departamento add() {
		IO.print("Name ? ");
		String nombre = IO.readString();
		Departamento d = Departamento.builder().nombre(nombre).build();
		return d;
	}

}
