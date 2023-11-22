package model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "departamento_hib")
public class Departamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dep")
	private Integer id;
	
	@Column(name = "nombre_dep", unique = true, nullable = false)
	private String nombre;
	
	@JoinColumn(name = "jefe")
	@ManyToOne
	private Empleado jefe;
	
	@OneToMany(mappedBy = "departamento")
	private Set<Empleado> empleados = new HashSet<>();
	
	public Departamento(Integer id, String nombre, Empleado jefe) {
        this.id = id;
        this.nombre = nombre;
        this.jefe = jefe;
    }
	
	
	public String show() {
		if(id == 0) return "no hay departamento";
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d:%-20s", id, nombre));
		
		if(jefe == null || jefe.getNombre() == null) sb.append("no jefe");
		
		else sb.append(String.format("jefe [%2d:%s]", jefe.getId(), jefe.getNombre(), jefe.getProyectos()));
		
		return sb.toString();
	}
}
