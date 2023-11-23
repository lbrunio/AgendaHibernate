package dao;

import model.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmpleadoDao {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private EntityManager entityManager;

    public EmpleadoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    

	public List<Empleado> findAll() {
        List<Empleado> empleados = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT e FROM Empleado e");
            empleados = query.getResultList();
        } catch (PersistenceException e) {
            logger.severe("Error finding all employees: " + e.getMessage());
        }

        return empleados;
    }

    public Empleado findById(Integer id) {
        try {
            return entityManager.find(Empleado.class, id);
        } catch (PersistenceException e) {
            logger.severe("Error finding employee by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Empleado> findByName(String name) {
        List<Empleado> empleados = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT e FROM Empleado e WHERE e.nombre LIKE :name");
            query.setParameter("name", name + "%");
            empleados = query.getResultList();
        } catch (PersistenceException e) {
            logger.severe("Error finding employees by name: " + e.getMessage());
        }

        return empleados;
    }

    public boolean create(Empleado e) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(e);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e1) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error creating employee: " + e1.getMessage());
        }

        return false;
    }

    public boolean update(Empleado e) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(e);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e1) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error updating employee: " + e1.getMessage());
        }

        return false;
    }

    public boolean delete(Integer id) {
        try {
            entityManager.getTransaction().begin();
            Empleado employee = entityManager.find(Empleado.class, id);
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error deleting employee: " + e.getMessage());
        }

        return false;
    }
}
