package view;

import java.util.List;

import dao.EmpleadoDao;
import dao.ProyectoDao;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Empleado;
import model.Proyecto;

public class ProyectoMenu {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
	private static EntityManager entityManager = entityManagerFactory.createEntityManager();
	
    public static void menu() {

        ProyectoDao pDao = new ProyectoDao(entityManager);

        while (true) {
            Integer opt = options();

            switch (opt) {
                case 1:
                    add(pDao);
                    break;

                case 2:
                    delete(pDao);
                    break;

                case 3:
                    update(pDao);
                    break;

                case 4:
                    showAll(pDao);
                    break;

                case 5:
                    searchById(pDao);
                    break;

                case 6:
                    searchByName(pDao);
                    break;

                case 7:
                    return;

                default:
                    IO.println("Invalid option");
                    break;
            }
        }

    }

    /** Muestra las opciones
     * 
     * @return Opcion
     */
    public static Integer options() {
        IO.println("""
                1. Add proyecto
                2. Remove proyecto
                3. Update proyecto
                4. Show all proyectos
                5. Search by id
                6. Search by name
                7. Exit
                """);

        Integer opt = IO.readInt();

        return opt;
    }

    /** Buscar por nombre
     * 
     * @param pDao Objeto ProyectoDao para realizar la busqueda por nombre
     */
    public static void searchByName(ProyectoDao pDao) {
        IO.println("Name to search ? ");
        String name = IO.readString();

        for (Proyecto p : pDao.findByName(name)) {
            IO.println(p.show());
        }
    }

    
    /** Buscar por ID
     * 
     * @param pDao Objeto ProyectoDao para realizar la busqueda por ID
     */
    public static void searchById(ProyectoDao pDao) {
        IO.println("ID to search ? ");
        Integer id = IO.readInt();

        Proyecto p = pDao.findById(id);
        
        if(p == null ) {
        	IO.println("Project not exist");
        	return;
        }

        IO.println(p.show());
    }

    
    /** Mostrar todos los proyectos
     * 
     * @param pDao Objeto ProyectoDao para realizar a mostrar todos los proyectos
     */
    public static void showAll(ProyectoDao pDao) {
	    List<Proyecto> proyectos = pDao.findAll();

	    if (proyectos.isEmpty()) {
	        IO.println("Empty projects");
	    } else {
	        for (Proyecto p : proyectos) {
	            IO.println(p.show());
	        }
	    }
	}

    
    /** Modificar proyecto
     * 
     * @param pDao Objeto ProyectoDao para realizar la modificacion de proyecto
     */
    public static void update(ProyectoDao pDao) {
        IO.println("Project ID ?");
        Integer id = IO.readInt();

        Proyecto p = pDao.findById(id);

        if (p == null) {
        	IO.println("Project not found");
        	return;
        }

        IO.printf("Change project name [%s] to ? ", p.getNombre());
        String name = IO.readString();
        p.setNombre(name);

        boolean updated = pDao.update(p);

        IO.println(updated ? "Project updated" : "Project not updated");
    }

    
    /** Borrar un proyecto
     * 
     * @param pDao Objeto ProyectoDao para realizar el delete
     */
    public static void delete(ProyectoDao pDao) {
        IO.println("Project ID ? ");
        Integer id = IO.readInt();
        
        if(pDao.findById(id) == null ) {
        	IO.println("Project not found");
        	return;
        }

        boolean deleted = pDao.delete(id);

        IO.print(deleted ? "Project deleted" : "Project not deleted");
    }

    
    /** Add proyecto
     * 
     * @param pDao Objeto ProyectoDao para realizar el add
     */
    public static void add(ProyectoDao pDao) {
        IO.println("Project name ? ");
        String name = IO.readString();

        Proyecto p = Proyecto.builder().nombre(name).build();

        boolean added = pDao.create(p);

        IO.print(added ? "Project added" : "Project not added");
    }
}
