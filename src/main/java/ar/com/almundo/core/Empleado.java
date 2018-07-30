package ar.com.almundo.core;

import static java.lang.Thread.sleep;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

/**
 * Representa a un empleado
 * @author MAI
 *
 */
public class Empleado {

	private String tipoRango;
	private Integer numeroLegago;
	private String nombreEmpleado;
	private boolean estaLibre = Boolean.TRUE;

	private static final Logger LOGGER = Logger.getLogger(Empleado.class.getName());

	/**
	 * Atender llamada: recibe una llamada, le asigna el empleado que la atendio y
	 * luego libera al empleado para que pueda seguir atendiendo otra
	 * 
	 * @param llamada
	 */
	public void atender(Llamada llamada) {

		int sleepTime = ThreadLocalRandom.current().nextInt(2000 - 1000) + 2000;
		llamada.setDuracion(sleepTime);
		try {
			sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.info("Error atendiendo llamada");
		}
		llamada.setAtendidaPor(this.getNombreEmpleado());
		this.estaLibre = Boolean.TRUE;
		LOGGER.info("Llamada " + llamada.getNumeroLlamada() + " atendida por " + this.getNombreEmpleado());
		System.out.println("Llamada " + llamada.getNumeroLlamada() + " atendida por " + this.getNombreEmpleado());
	}

	public void setOcupado() {
		this.estaLibre = Boolean.FALSE;

	}

	public void setLibre() {
		this.estaLibre = Boolean.TRUE;
	}

	public boolean estaLibre() {
		return this.estaLibre;
	}

	public Empleado(String tipoRango) {
		this.tipoRango = tipoRango;
	}

	public void setOperador() {
		this.tipoRango = TipoRango.OPERADOR;
	}

	public void setDirector() {
		this.tipoRango = TipoRango.DIRECTOR;
	}

	public void setSupervisor() {
		this.tipoRango = TipoRango.SUPERVISOR;
	}

	public String getTipoRango() {
		return tipoRango;
	}

	public void setTipoRango(String tipoRango) {
		this.tipoRango = tipoRango;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public boolean isEstaLibre() {
		return estaLibre;
	}

	public void setEstaLibre(boolean estaLibre) {
		this.estaLibre = estaLibre;
	}

	public Integer getNumeroLegago() {
		return numeroLegago;
	}

	public void setNumeroLegago(Integer numeroLegago) {
		this.numeroLegago = numeroLegago;
	}
}
