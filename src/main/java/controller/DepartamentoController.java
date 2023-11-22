package controller;

import java.util.List;
import java.util.logging.Logger;

import dao.DepartamentoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Departamento;
import view.DepartamentoView;

public class DepartamentoController {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final DepartamentoDao dao;
	private final DepartamentoView view;
	
	
    private EntityManager em;


	public DepartamentoController() {
		dao = new DepartamentoDao(em);
		view = new DepartamentoView();
		
		
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
		String initial = view.searchByNameInitial();
		logger.info("Departamentos starts with " + initial);
		List<Departamento> list = dao.findByName(initial + "%");
		view.show(list);		
	}

	private void getById() {
		Integer id = view.searchByCode();
		logger.info("Departamento with id: " + id);
		Departamento entity = dao.findById(id);
		if (entity != null) {
			view.show(entity, dao.getEmpleado(id));
		}
	}
	
	private void getAll() {
		logger.info("ObtainingDepartamentos");
		List<Departamento> list = dao.findAll();
		view.show(list);
	}

	private void update() {
		boolean updated = false;
		Integer id = view.searchByCode();
		logger.info("Updating Departamento with id: " + id);
		Departamento entity = dao.findById(id);
		Departamento d = null;
		if (entity != null) {
			d = view.update(entity);
			updated = dao.update(d);
		}
		view.result(updated ? "Updated" : "Not updated");
	}

	private void delete() {
		boolean deleted = false;
		Integer id = view.searchByCode();
		logger.info("Deleting Departamento with id: " + id);
		Departamento entity = dao.findById(id);
		if (entity != null) {
			deleted = dao.delete(id);
		}
		view.result(deleted ? "Deleted" : "Not deleted");
	}
	
	private void create() {
		logger.info("Creating Departamento");
		Departamento entity = view.add();
		boolean added  = dao.create(entity);
		view.result(added ? "Added" : "Not added");
	}
}

