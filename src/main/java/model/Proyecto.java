package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyecto_hib")
public class Proyecto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_proyecto")
	private Integer id;
	
	@Column(name = "nombre_proyecto", unique = true, nullable = false)
	private String nombre;
	
	@ManyToOne
    @JoinColumn(name = "departamento_hib")
    private Departamento departamento;
	
	@ManyToMany(mappedBy = "proyectos")
    private Set<Empleado> empleados = new HashSet<Empleado>();
	
	public Proyecto(Integer id, String name) {
		this.id = id;
		this.nombre = name;
	}
	
	public String show() {
		if(id == 0) return "no hay proyecto";
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d:%-20s", id, nombre));
		
		
		return sb.toString();
	}

	
}


