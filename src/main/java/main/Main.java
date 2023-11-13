package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class Main {
	public static EntityManager em = null;
	
	public static void main(String[] args) {
		Logger.getLogger("com.lbrunio").setLevel(Level.SEVERE);
		
		Departamento departamento;
		Empleado empleado;
		Proyecto proyecto;
	}
}
