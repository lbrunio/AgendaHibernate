package model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "empleado_hib")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_emple")
	private Integer id;

	@Column(name = "nombe_emple", unique = true, nullable = false)
	private String nombre;

	@Column(name = "salario")
	private Double salario;

	@ManyToOne
	@JoinColumn(name = "departamento")
	private Departamento departamento;
	
	@ManyToOne
	@JoinColumn(name = "proyecto_id")
	private Proyecto proyecto;

	@ManyToMany
	@JoinTable(name = "empleado_proyecto", joinColumns = @JoinColumn(name = "id_empleado"), inverseJoinColumns = @JoinColumn(name = "id_proyecto"))
	private List<Proyecto> proyectos;

	public String show() {
		if (id == 0)
			return "no hay empleado";

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(" %2d:%-20s:%4.2f", id, nombre, salario));

		sb.append(":");

		if (departamento == null || departamento.getNombre() == null) {
			sb.append(" Sin departamento");
		}
		else {
			sb.append(String.format(" Departamento [%2d:%s]", departamento.getId(), departamento.getNombre()));
		}
		
		sb.append(":");
		
		if (proyectos == null || proyectos.isEmpty()) {
			sb.append(" Sin proyectos");
		} else {
			for (Proyecto proyecto : proyectos) {
				sb.append(String.format(" Proyectos [%2d:%s] ", proyecto.getId(), proyecto.getNombre()));
			}
			
		}
		return sb.toString();
	}
}
