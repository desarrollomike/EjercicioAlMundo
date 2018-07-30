package ar.com.almundo.core;

/**
 * Representa una llamada
 * 
 * @author MAI
 *
 */
public class Llamada {

	private int duracion;
	private String numeroLlamada;
	private String atendidaPor;
	private int falloAtencion;

	public Llamada() {
		this.falloAtencion = 0;
	}

	public void setDuracion(int sleepTime) {
		this.duracion = sleepTime;

	}

	public String getAtendidaPor() {
		return atendidaPor;
	}

	public void setAtendidaPor(String atendidaPor) {
		this.atendidaPor = atendidaPor;
	}

	public int getDuracion() {
		return duracion;
	}

	public String getNumeroLlamada() {
		return numeroLlamada;
	}

	public void setNumeroLlamada(String numeroLlamada) {
		this.numeroLlamada = numeroLlamada;
	}

	public int getFalloAtencion() {
		return falloAtencion;
	}

	public void setFalloAtencion(int falloAtencion) {
		this.falloAtencion = falloAtencion;
	}

	public void falloAtencion() {
		this.falloAtencion = falloAtencion + 1;
	}

}
