package ar.com.almundo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Representa el staff de empleados que estan trabajando en el call center
 * 
 * @author MAI
 *
 */
public class StaffEmpleados {
	private List<Empleado> operadores;
	private List<Empleado> supervisores;
	private List<Empleado> directores;

	private static final Logger LOGGER = Logger.getLogger(StaffEmpleados.class.getName());

	/**
	 * Crea el staff segun los paramtros
	 * 
	 * @param cantOperadores
	 * @param cantSupervisores
	 * @param cantDirectores
	 */
	public StaffEmpleados(int cantOperadores, int cantSupervisores, int cantDirectores) {
		this.operadores = new ArrayList<Empleado>();
		for (int i = 0; i < cantOperadores; i++) {
			Empleado empleado = new Empleado(TipoRango.OPERADOR);
			empleado.setNombreEmpleado("Operador " + i);
			empleado.setNumeroLegago(i);
			this.operadores.add(empleado);
		}

		this.supervisores = new ArrayList<Empleado>();
		for (int i = 0; i < cantSupervisores; i++) {
			Empleado empleado = new Empleado(TipoRango.SUPERVISOR);
			empleado.setNombreEmpleado("Supervisor " + i);
			empleado.setNumeroLegago(i);
			this.supervisores.add(empleado);
		}

		this.directores = new ArrayList<Empleado>();
		for (int i = 0; i < cantDirectores; i++) {
			Empleado empleado = new Empleado(TipoRango.DIRECTOR);
			empleado.setNombreEmpleado("Director " + i);
			empleado.setNumeroLegago(i);
			this.directores.add(empleado);
		}
	}

	/**
	 * Retorna un empleado disponible para atender,caso contrario null
	 */
	public synchronized Empleado getEmpleadoDisponible() {
		Optional<Empleado> empleado = this.operadores.stream().filter(empleadoBuscando -> empleadoBuscando.estaLibre())
				.findAny();
		if (empleado.isPresent()) {
			empleado.get().setOcupado();
			return empleado.get();
		}

		empleado = this.supervisores.stream().filter(empleadoBuscando -> empleadoBuscando.estaLibre()).findAny();
		if (empleado.isPresent()) {
			empleado.get().setOcupado();
			return empleado.get();
		}

		empleado = this.directores.stream().filter(empleadoBuscando -> empleadoBuscando.estaLibre()).findAny();
		if (empleado.isPresent()) {
			empleado.get().setOcupado();
			return empleado.get();
		}

		return null;
	}

	public List<Empleado> getOperadores() {
		return operadores;
	}

	public void setOperadores(List<Empleado> operadores) {
		this.operadores = operadores;
	}

	public List<Empleado> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Empleado> supervisores) {
		this.supervisores = supervisores;
	}

	public List<Empleado> getDirectores() {
		return directores;
	}

	public void setDirectores(List<Empleado> directores) {
		this.directores = directores;
	}

}
