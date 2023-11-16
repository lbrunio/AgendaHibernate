package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.mariadb.jdbc.Connection;

import model.Departamento;
import model.Empleado;

public class DepartamentoDao {
	private Connection conn = null;
	
	private final String QUERY = """
			SELECT d.id_dep as id, d.nombre_dep as nombre,
			jefe, e.nombre_emple as nombreJefe 
			FROM Departamento d 
			LEFT JOIN Empleado e 
			ON d.jefe = e.id_emple
			""";
	
	
	public DepartamentoDao() {
		conn = Database.getConnection();
	}
	
	
	public void close() {
		Database.close();
	}
	
	public List<Departamento> findAll() {
		List<Departamento> departamentos = new ArrayList<>();
		
		String sql = QUERY;
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			while(rs.next()) departamentos.add(read(rs));
				
			
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return departamentos;
	}
	
	
	public Departamento findById(Integer id) {
		String sql = QUERY + "WHERE ID = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) return read(rs);
			
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		
		return null;
	}
	
	
	
	public List<Departamento> findByName(String inicio) {
		String sql = QUERY + "WHERE nombre_dep LIKE ?";
		
		List<Departamento> departamentos = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, inicio + "%");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) departamentos.add(read(rs));
			
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return null;
	}
	
	
	public boolean create(Departamento d) {
		String sql = """
				INSERT INTO Departamento(nombre, jefe) VALUES(?, ?)
				""";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, d.getNombre());
			
			if (d.getJefe() == null) {
				ps.setObject(2, null);
			} else {
				ps.setInt(2, d.getJefe().getId());
			}
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		return false;
	}
	
	
	public boolean update(Departamento d) {
		String sql = """
					UPDATE Departamento
					SET nombre_dep = ?, jefe = ?
					WHERE id_dep = ?
				""";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getNombre());
			
			if (d.getJefe() == null) {
				ps.setObject(2, null);
			} else {
				EmpleadoDao empleDao = new EmpleadoDao();
				Empleado e = empleDao.findById(d.getJefe().getId());
				
				if (e != null) {
					e.setDepartamento(d);
					empleDao.update(e);
				}
				
				ps.setInt(2, d.getJefe().getId());
			}
			
			ps.setInt(3, d.getId());
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return false;
	}
	
	
	
	
	public boolean delete(Integer id) {
		try {
			PreparedStatement ps;
			
			String sql = """
					UPDATE Empleado
					SET departamento = null
					WHERE departamento = ?
					""";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			sql = """
					DELETE FROM Departamento
					WHERE id_dep = ?
					""";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return false;
	}
	
	
	private Departamento read(ResultSet rs) {
		try {
			Integer id = rs.getInt("id_dep");
			String nombre = rs.getString("nombre");
			Integer jefe = rs.getInt("jefe");
			String nombreJefe = rs.getString("nombreJefe");
			
			Empleado e = Empleado.builder().id(jefe).nombre(nombreJefe).build();
			
			return new Departamento(id, nombre, e);
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return null;
	}
	
	
	public List<Empleado> getEmpleado(Integer id) {
		List<Empleado> empleados = new ArrayList<Empleado>();
		
		String sql = """
				SELECT id_emple, nombre_emple, salario, departamento
				FROM Empleado
				WHERE departamento = ?
				""";
		
		try {
			Departamento d = findById(id);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Empleado e = Empleado.builder().id(rs.getInt("id")).nombre(rs.getString("nombre")).salario(rs.getDouble("salario")).departamento(d).build();
				
				empleados.add(e);
			}
		} catch (SQLException e) {
			Logger.getLogger(DepartamentoDao.class.getName());
		}
		
		return empleados;
	}
	
	
}
