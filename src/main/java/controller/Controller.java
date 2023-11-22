package controller;

import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import view.View;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class.getName());

	public Controller(EntityManager entityManager) {
		
		DepartamentoController departamento = new DepartamentoController();
		EmpleadoController empleado = new EmpleadoController();
		
		while (true) {
			Character opt = View.getOption();
			logger.info("Menu");
			switch (opt) {
				case 'E':
					empleado.menu();
					break;
				case 'D':
					departamento.menu();
					break;
				case 'S':
					return;
				default:
			}		
		}
	}
	
}

