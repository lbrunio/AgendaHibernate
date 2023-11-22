package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Departamento;
import model.Empleado;
import model.Proyecto;
import view.DepartamentoMenu;
import view.EmpleadoMenu;
import view.Menu;
import view.ProyectoMenu;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Menu.Menu();

        entityManager.close();
        entityManagerFactory.close();
    }
}

