package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
	private Integer id;
	private String nombre;
	private Double salario;
	private Departamento departamento;
	
	
	public String show() {
		if (id == 0) return "no hay empleado";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%2d:%-20s:%4.2f", id, nombre, salario));
		
		sb.append(":");
		
		if (departamento == null || departamento.getNombre() == null) sb.append("Sin departamento");
		
		else sb.append(String.format("Departamento [%2d:%s]", departamento.getId(), departamento.getNombre()));
		
		return sb.toString();
	}
}
