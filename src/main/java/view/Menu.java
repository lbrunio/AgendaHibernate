package view;

import io.IO;
import jakarta.persistence.EntityManager;

public class Menu {
	public static void Menu() {
		
		while(true) {
			Integer opt = menu();
			
			switch(opt) {
			case 1:
				EmpleadoMenu.menu();
				break;
				
			case 2:
				DepartamentoMenu.menu();
				break;
				
			case 3:
				ProyectoMenu.menu();
				return;
				
			case 4:
				return;
				
				
			default:
				IO.print("Invalid Option");
				break;
			}
		}
	}
	
	/** Muestra las opciones
	 * 
	 * @return Opcion
	 */
	private static Integer menu() {
		IO.println("""
				1. Employee
				2. Department
				3. Project
				4. Exit
				""");
		
		Integer option = IO.readInt();
		
		return option;
		
	}
}
