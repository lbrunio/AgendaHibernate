package controller;

import java.util.List;
import java.util.logging.Logger;


import dao.ProyectoDao;
import jakarta.persistence.EntityManager;
import model.Empleado;
import model.Proyecto;
import view.ProyectoView;



public class ProyectoController {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final ProyectoDao dao;
	private final ProyectoView view;
	
	private EntityManager em;

	public ProyectoController() {
		dao = new ProyectoDao(em);
		view = new ProyectoView();
	}
	
	public void menu() {
		while (true) {
			Character opt = view.getOption();
			switch (opt) {
			case 'A':
				create();
				break;
			case 'R':
				delete();
				break;
			case 'U':
				update();
				break;
			case 'L':
				getAll();
				break;
			case 'I':
				getById();
				break;
			case 'N':
				getByStartsName();
				break;
			case 'E':
				return;
			default:
			}
		}
	}

	private void getByStartsName() {
		String initial = view.searchInitialName();
		logger.info("Empleados starts with " + initial);
		List<Proyecto> list = dao.findByName(initial + "%");
		view.show(list);		
	}

	private void getById() {
		Integer id = view.searchByCode();
		logger.info("Empleado with id: " + id);
		Proyecto entity = dao.findById(id);
		if (entity != null) {
			view.show(entity);
		}
	}
	
	private void getAll() {
		logger.info("Obtaining Empleados");
		List<Proyecto> list = dao.findAll();
		view.show(list);
	}

	private void create() {
		logger.info("Creating Empleado");
		Proyecto entity = view.add();
		boolean added = dao.create(entity);
		view.result(added ? "Added" : "Not added");
	}

	private void update() {
		boolean updated = false;
		Integer id = view.searchByCode();
		logger.info("Updating Empleado with id: " + id);
		Proyecto entity = dao.findById(id);
		Proyecto d = null;
		if (entity != null) {
			d = view.update(entity);
			updated = dao.update(d);
		}
		view.result(updated ? "Updated" : "Not updated");
	}

	private void delete() {
		boolean deleted = false;
		Integer id = view.searchByCode();
		logger.info("Deleting Empleado with id: " + id);
		Proyecto entity = dao.findById(id);
		if (entity != null) {
			deleted = dao.delete(id);
		}
		view.result(deleted ? "Deleted" : "Not deleted");
	}
}

