package view;

import java.util.List;

import dao.DepartamentoDao;
import dao.EmpleadoDao;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Departamento;
import model.Empleado;

public class DepartamentoMenu {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
	private static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public static void menu() {
		DepartamentoDao dDao = new DepartamentoDao(entityManager);

		while (true) {
			Integer opt = options();

			switch (opt) {
			case 1:
				add(dDao);
				break;

			case 2:
				delete(dDao);
				break;

			case 3:
				update(dDao);
				break;

			case 4:
				showAll(dDao);
				break;

			case 5:
				searchById(dDao);
				break;

			case 6:
				searchByName(dDao);
				break;

			case 7:
				return;

			default:
				IO.println("Invalid option");
				break;
			}
		}
	}
	
	
	/** Opciones menu departamentos 
	 * 
	 * @return opcion
	 */
	public static Integer options() {
		IO.println("""
				1. Add departamento
				2. Remove departamento
				3. Update departamento
				4. Show all departamentos
				5. Search by id
				6. Search by name
				7. Exit
				""");

		Integer opt = IO.readInt();

		return opt;
	}

	/** Buscar por nombre
	 * 
	 * @param dDao Departamento Dao
	 */
	public static void searchByName(DepartamentoDao dDao) {
		IO.println("Department name ? ");
		String name = IO.readString();

		for (Departamento d : dDao.findByName(name)) {
			IO.println(d.show());
		}
	}

	/** Buscar por id
	 *  
	 * @param dDao Departamento dao
	 */
	public static void searchById(DepartamentoDao dDao) {
		IO.println("Department ID ? ");
		Integer id = IO.readInt();

		Departamento d = dDao.findById(id);

		if (d == null) {
			IO.println("Department not found");
			return;
		}

		IO.println(d.show());

	}

	/** Mostrar todos los departamentos
	 * 
	 * @param dDao Departamento dao
	 */
	public static void showAll(DepartamentoDao dDao) {
		List<Departamento> departments = dDao.findAll();

		if (departments.isEmpty()) {
			IO.println("Empty departments");
		} else {
			for (Departamento d : departments) {
				IO.println(d.show());
			}
		}
	}

	
	/** Modificar departmento
	 * 
	 * @param dDao Departamento dao 
	 */
	public static void update(DepartamentoDao dDao) {
		IO.println("Department ID ? ");
		Integer id = IO.readInt();

		Departamento d = dDao.findById(id);

		if (d == null) {
			IO.println("Department not found");
			return;
		}
		
		IO.printf("Change department name [%s] to ? ", d.getNombre());
		String name = IO.readString();

		if (!name.isBlank())
			d.setNombre(name);

		IO.printf("Change boss [%s] to ? ", d.getJefe().show());
		Integer eId = IO.readIntOrNull();

		if (eId != null)
			d.setJefe(Empleado.builder().id(eId).build());

		boolean updated = dDao.update(d);

		IO.println(updated ? "Department updated" : "Department not updated");
	}
	
	/** Borrar departamento 
	 * 
	 * @param dDao Departamento Dao 
	 */
	public static void delete(DepartamentoDao dDao) {
		IO.println("Department ID ? ");
		Integer id = IO.readInt();

		if (dDao.findById(id) == null) {
			IO.println("Department not found");
			return;
		}

		IO.printf("Do you want to delete the department [%s] ? Y/N ", dDao.findById(id).getNombre());
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("n")) {
			IO.printf("Deletion of department [%s] canceled \n", dDao.findById(id).getNombre());
			return;
		}

		boolean deleted = dDao.delete(id);

		IO.println(deleted ? "Department deleted" : "Department not deleted");
	}

	
	/** Add un departamento
	 * 
	 * @param dDao Departamento dao
	 */
	public static void add(DepartamentoDao dDao) {

		boolean added;
		Departamento d;

		IO.println("Name ? ");
		String name = IO.readString();

		IO.println("Do you want to add a existing employee as the head of the new department ? Y/N");
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("y")) {
			IO.println("Employee ID ? ");
			Integer idEmple = IO.readInt();

			EmpleadoDao eDao = new EmpleadoDao(entityManager);

			Empleado e = eDao.findById(idEmple);

			if (e == null)
				IO.println("Employee not found");

			d = Departamento.builder().nombre(name).jefe(e).build();

			added = dDao.create(d);

			IO.println(added ? "Department added" : "Department not added");

		} else {
			d = Departamento.builder().nombre(name).build();

			added = dDao.create(d);

			IO.println(added ? "Department added" : "Department not added");

		}
	}
}
