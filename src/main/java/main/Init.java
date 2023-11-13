package main;

import jakarta.persistence.Persistence;
public class Init {
	public static void main(String[] args) {
		
		Persistence.generateSchema("Persistence", null);
	}
}
