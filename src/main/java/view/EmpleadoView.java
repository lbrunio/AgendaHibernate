package view;

import java.util.List;
import io.IO;
import model.Departamento;
import model.Empleado;

public class EmpleadoView {

    
    final List<String> options = List.of( 
        "Add departamento", 
        "Remove departamento", 
        "Update departamento", 
        "Show alL", 
        "Search by Id",
        "Search by Name", 
        "Exit"
    );

    /**
     * Muestra las opciones disponibles
     *
     * @return Opcion
     */
    public Character getOption() {
        IO.println("Employees: " + options);
        return Character.toUpperCase(IO.readChar());
    }

    /**
     * Muestra la informacion de una lista de empleados
     *
     * @param employees Lista de empleados
     */
    public void show(List<Empleado> employees) {
        for (Empleado e : employees) {
            IO.println(e.show());
        }
    }

    /**
     * Muestra la informacion de un empleado
     *
     * @param e Empleado a mostrar
     */
    public void show(Empleado e) {
        if (e == null) {
            return;
        }
        IO.println(e.show());
    }

    /**
     * Muestra un mensaje
     *
     * @param str Mensaje a mostrar
     */
    public void result(String str) {
        IO.println(str);
    }

    /**
     * Codigo para buscar un empleado
     *
     * @return Codigo
     */
    public int searchByCode() {       
        IO.print("ID ? ");
        return IO.readInt();
    }

    /**
     * Nombre inicial para buscar empleados
     *
     * @return Nombre inicial
     */
    public String searchInitialName() {
        IO.print("Name starts with ? ");
        return IO.readString();
    }

    /**
     * Actualizar un empleado
     *
     * @param e Empleado a actualizar
     * @return Empleado actualizado
     */
    public Empleado update(Empleado e) {
        IO.printf("Change employee name [%s] to ? ", e.getNombre());
        String name = IO.readString();
        
        if (!name.isBlank()) e.setNombre(name);
        
        IO.printf("Change employee salary [%s] to ? ", e.getSalario());
        Double salario = IO.readDoubleOrNull();
        
        if (salario != null) e.setSalario(salario);

        Departamento d = e.getDepartamento();
        IO.printf("Change employee department [%s] to ? ", d == null ? "Department not found" : d.show());
        Integer departamento = IO.readIntOrNull();
        
        if (departamento != null) e.setDepartamento(Departamento.builder().id(departamento).build());
        
        return e;
    }

    /**
     * Agregar un nuevo empleado.
     *
     * @return Nuevo empleado creado
     */
    public Empleado add() {
        IO.println("Name ? ");
        String name = IO.readString();
        
        IO.println("Salary ? ");
        Double salario = IO.readDoubleOrNull();
        
        Empleado e = Empleado.builder().nombre(name).salario(salario).build();
        
        return e;
    }
}
