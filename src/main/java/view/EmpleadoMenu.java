package view;

import java.util.ArrayList;
import java.util.List;

import dao.DepartamentoDao;
import dao.EmpleadoDao;
import dao.ProyectoDao;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class EmpleadoMenu {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void menu() {

        EmpleadoDao eDao = new EmpleadoDao(entityManager);

        while (true) {
            Integer opt = options();  

            switch (opt) {
                case 1:
                    add(eDao);  
                    break;

                case 2:
                    delete(eDao); 
                    break;

                case 3:
                    update(eDao);  
                    break;

                case 4:
                    showAll(eDao); 
                    break;

                case 5:
                    searchById(eDao);  
                    break;

                case 6:
                    searchByName(eDao);
                    break;

                case 7:
                    return; 

                default:
                    IO.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Muestra las opciones disponibles menu Empleado
     *
     * @return opcion
     */
    public static Integer options() {
        IO.println("""
                1. Add e
                2. Remove empleado
                3. Update empleado
                4. Show all empleado
                5. Search by id
                6. Search by name
                7. Exit
                """);

        Integer option = IO.readInt();

        return option;
    }

    /**
     * Busca empleados por nombre
     *
     * @param eDao Objeto EmpleadoDao para realizar la busqueda por nombre
     */
    public static void searchByName(EmpleadoDao eDao) {
        IO.println("Employee name ? ");
        String name = IO.readString();

        for (Empleado e : eDao.findByName(name)) {
            IO.println(e.show());
        }
    }

    /**
     * Busca un empleado por su ID
     *
     * @param eDao Objeto EmpleadoDao para realizar la bu squeda por ID
     */
    public static void searchById(EmpleadoDao eDao) {
        IO.println("Employee ID ? ");
        Integer id = IO.readInt();

        Empleado e = eDao.findById(id);

        if (e == null) {
            IO.println("Employee not found");
            return;
        }

        IO.println(e.show());
    }

    /**
     * Muestra informacion de todos los empleados
     *
     * @param eDao Objeto EmpleadoDao para obtener la lista de empleados
     */
    public static void showAll(EmpleadoDao eDao) {
        List<Empleado> employees = eDao.findAll();

        if (employees.isEmpty()) {
            IO.println("Empty employee");
        } else {
            for (Empleado d : employees) {
                IO.println(d.show());
            }
        }
    }

    /**
     * Actualizar la informacion de un empleado
     *
     * @param eDao Objeto EmpleadoDao para realizar la actualizacion
     */
    public static void update(EmpleadoDao eDao) {
        IO.println("Employee ID to update ? ");
        Integer id = IO.readInt();
        Empleado e = eDao.findById(id);

        if (e == null) {
            IO.println("Employee not found");
            return;
        }

        IO.printf("Change employee name [%s] to ? ", e.getNombre());
        String name = IO.readString();

        if (!name.isBlank())
            e.setNombre(name); 

        IO.printf("Change employee salary [%f] to ? ", e.getSalario());
        Double salary = IO.readDouble();

        if (salary != null)
            e.setSalario(salary);

        if (e.getDepartamento() == null) {
            IO.printf("Employee [%s] has no assigned department. To assign a department, enter the ID department ", e.getNombre());
            Integer dID = IO.readIntOrNull();

            if (dID != null) {
                e.setDepartamento(Departamento.builder().id(dID).build());
            }

        } else {
            IO.printf("Change employee department [%s] to ? ", e.getDepartamento().show());
            Integer dID = IO.readIntOrNull();
            if (dID != null) {
                e.setDepartamento(Departamento.builder().id(dID).build());
            }
        }

        IO.println("Do you want to change projects? (Y/N)");
        String yn = IO.readString();

        if (yn.equalsIgnoreCase("y")) {
            List<Proyecto> projects = e.getProyectos();
            if (projects != null && !projects.isEmpty()) {
                IO.println("Select the project to change:");

                // Mostrar proyectos actuales
                for (int i = 0; i < projects.size(); i++) {
                    IO.println((i + 1) + ". " + projects.get(i).show());
                }

                // Obtener indice del proyecto a cambiar
                Integer pIndex = IO.readInt();
                if (pIndex >= 1 && pIndex <= projects.size()) {
                    Proyecto selectedProject = projects.get(pIndex - 1);

                    // Solicitar nuevo ID de proyecto
                    IO.printf("Change project [%s] to ? ", selectedProject.show());
                    Integer newProjectID = IO.readIntOrNull();

                    if (newProjectID != null) {
                        ProyectoDao pDao = new ProyectoDao(entityManager);
                        Proyecto newProject = pDao.findById(newProjectID);

                        // Verificar si el nuevo proyecto existe
                        if (newProject != null) {
                            projects.set(pIndex - 1, newProject);
                            e.setProyectos(projects);
                        } else {
                            IO.println("New project not found. Project not changed");
                        }
                    }
                } else {
                    IO.println("Invalid project selection. Project not changed");
                }
            } else {
                IO.println("No projects associated with this employee");
            }
        }

        boolean updated = eDao.update(e);

        IO.println(updated ? "Employee updated" : "Employee not updated");
    }

    /**
     * Borrar un empleado
     *
     * @param eDao Objeto EmpleadoDao para realizar a borrar
     */
    public static void delete(EmpleadoDao eDao) {
        IO.println("ID of employee to delete ? ");
        Integer id = IO.readInt();

        if (eDao.findById(id) == null) {
            IO.println("Employee not found");
            return;
        }

        IO.printf("Confirm to delete employee [%s] ? Y/N ", eDao.findById(id).getNombre());
        String yn = IO.readString();

        if (yn.equalsIgnoreCase("n")) {
            IO.printf("Deletion of employee [%s] canceled \n", eDao.findById(id).getNombre());
            return;
        }

        boolean deleted = eDao.delete(id);

        IO.println(deleted ? "Employee deleted" : "Employee not deleted");
    }

    /**
     * Agregar un nuevo empleado
     *
     * @param eDao Objeto EmpleadoDao para realizar a add
     */
    public static void add(EmpleadoDao eDao) {
        boolean added;
        Empleado e;
        
        IO.println("Employee name ? ");
        String nombre = IO.readString();

        IO.println("Employee salary ? ");
        Double salario = IO.readDouble();

        IO.println("Do you want to assign the new employee to an existing department ? Y/N ");
        String yn = IO.readString();

        if (yn.equalsIgnoreCase("y")) {
            DepartamentoDao dDao = new DepartamentoDao(entityManager);
            Departamento d;

            IO.println("Department ID ? ");
            Integer id = IO.readInt();

            d = dDao.findById(id);


            if (d == null) {
                IO.println("Department not found");
                return;
            }

            IO.println("Do you want to assign the new employee to an existing project ? Y/N ");
            yn = IO.readString();

            List<Proyecto> projectList = new ArrayList<>();

            if (yn.equalsIgnoreCase("y")) {
                ProyectoDao pDao = new ProyectoDao(entityManager);

                IO.println("Project ID ? ");
                id = IO.readInt();

                Proyecto p = pDao.findById(id);

                if (p == null) {
                    IO.println("Project not found");
                    return;
                }

                projectList.add(p);
            }

           
            e = Empleado.builder().nombre(nombre).salario(salario).departamento(d).proyectos(projectList).build();
        } else {
   
            IO.println("Do you want to assign the new employee to an existing project ? Y/N ");
            yn = IO.readString();

            List<Proyecto> projectList = new ArrayList<>();

            if (yn.equalsIgnoreCase("y")) {
                ProyectoDao pDao = new ProyectoDao(entityManager);

                Integer id = IO.readInt();

                Proyecto p = pDao.findById(id);

            
                if (p == null) {
                    IO.println("Project not found");
                    return;
                }

                projectList.add(p);
            }

            e = Empleado.builder().nombre(nombre).salario(salario).proyectos(projectList).build();
        }

        added = eDao.create(e);
        IO.println(added ? "Employee added" : "Employee not added");
    }
}
