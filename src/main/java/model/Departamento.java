package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {
	private Integer id;
	private String nombre;
	private Empleado jefe;
	
	
	public String show() {
		if(id == 0) return "no hay departamento";
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d:%-20s", id, nombre));
		
		if(jefe == null || jefe.getNombre() == null) sb.append("sin jefe");
		
		else sb.append(String.format("jefe [%2d:%s]", jefe.getId(), jefe.getNombre()));
		
		return sb.toString();
	}
}
