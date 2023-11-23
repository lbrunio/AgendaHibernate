package dao;

import io.IO;
import model.Departamento;
import model.Empleado;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.TransactionRolledbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.query.QueryArgumentException;

public class DepartamentoDao {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private EntityManager entityManager;

    public DepartamentoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

 

    public List<Departamento> findAll() {
        List<Departamento> departamentos = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT d FROM Departamento d LEFT JOIN FETCH d.jefe");
            departamentos = query.getResultList();
        } catch (QueryArgumentException e) {
            logger.severe("Error finding all departments: " + e.getMessage());
        }

        return departamentos;
    }

    public Departamento findById(Integer id) {
        try {
            return entityManager.find(Departamento.class, id);
        } catch (IllegalArgumentException e) {
            logger.severe("Error finding department by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Departamento> findByName(String inicio) {
        List<Departamento> departamentos = new ArrayList<>();

        try {
            Query query = entityManager.createQuery("SELECT d FROM Departamento d WHERE d.nombre_dep LIKE :inicio");
            query.setParameter("inicio", inicio + "%");
            departamentos = query.getResultList();
        } catch (QueryArgumentException e) {
            logger.severe("Error finding departments by name: " + e.getMessage());
        }

        return departamentos;
    }

    public boolean create(Departamento d) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(d);
            entityManager.getTransaction().commit();
            return true;
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException | RollbackException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error creating department: " + e.getMessage());
        }

        return false;
    }

    public boolean update(Departamento d) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(d);
            entityManager.getTransaction().commit();
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException | RollbackException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error updating department: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(Integer id) {
        try {
            entityManager.getTransaction().begin();
            Departamento department = entityManager.find(Departamento.class, id);
            entityManager.remove(department);
            entityManager.getTransaction().commit();
            return true;
        } catch (EntityNotFoundException | IllegalArgumentException | TransactionRequiredException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error deleting department: " + e.getMessage());
        }

        return false;
    }

    public List<Empleado> getEmpleado(Integer id) {
        List<Empleado> empleados = new ArrayList<>();

        try {
            Departamento d = entityManager.find(Departamento.class, id);
            if (d != null) {
                empleados = (List<Empleado>) d.getEmpleados();
            }
        } catch (IllegalArgumentException e) {
            logger.severe("Error getting employees for department: " + e.getMessage());
        }

        return empleados;
    }
}
