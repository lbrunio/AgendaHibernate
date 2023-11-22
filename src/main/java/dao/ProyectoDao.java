package dao;

import io.IO;
import model.Proyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProyectoDao {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private EntityManager entityManager;

    public ProyectoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

 

	public List<Proyecto> findAll() {
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT p FROM Proyecto p");
            proyectos = query.getResultList();
        } catch (PersistenceException e) {
            logger.severe("Error finding all projects: " + e.getMessage());
        }

        return proyectos;
    }

    public Proyecto findById(Integer id) {
        try {
            return entityManager.find(Proyecto.class, id);
        } catch (PersistenceException e) {
            logger.severe("Error finding project by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Proyecto> findByName(String name) {
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT p FROM Proyecto p WHERE p.nombre LIKE :name");
            query.setParameter("name", name + "%");
            proyectos = query.getResultList();
        } catch (PersistenceException e) {
            logger.severe("Error finding projects by name: " + e.getMessage());
        }

        return proyectos;
    }

    public boolean create(Proyecto p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e1) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error creating project: " + e1.getMessage());
        }

        return false;
    }

    public boolean update(Proyecto p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(p);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e1) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error updating project: " + e1.getMessage());
        }

        return false;
    }

    public boolean delete(Integer id) {
        try {
            entityManager.getTransaction().begin();
            Proyecto project = entityManager.find(Proyecto.class, id);
            entityManager.remove(project);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error deleting project: " + e.getMessage());
        }

        return false;
    }
}
