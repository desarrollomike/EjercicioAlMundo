package ar.com.almundo.callcenter;

import java.util.List;
import java.util.logging.Logger;

import ar.com.almundo.core.Llamada;
import ar.com.almundo.core.StaffEmpleados;
import ar.com.almundo.dispatcher.Dispatcher;

/**
 * Representa al CallCenter
 * 
 * @author MAI
 *
 */
public class CallCenter {

	private static final Logger LOGGER = Logger.getLogger(CallCenter.class.getName());
	private Dispatcher dispatcher;
	private StaffEmpleados empleados;
	private List<Llamada> llamadasEntrantes;

	
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public StaffEmpleados getEmpleados() {
		return empleados;
	}

	public void setEmpleados(StaffEmpleados empleados) {
		this.empleados = empleados;
	}

	public List<Llamada> getLlamadasEntrantes() {
		return llamadasEntrantes;
	}

	public void setLlamadasEntrantes(List<Llamada> llamadasEntrantes) {
		this.llamadasEntrantes = llamadasEntrantes;
	}

	public CallCenter(int cantidadHilos) {
		this.empleados = new StaffEmpleados(5, 3, 1);
		this.dispatcher = new Dispatcher(cantidadHilos,this.empleados);
	}

	
	/**
	 * Constructor de CallCenter 
	 * @param cantidadHilos es la cantidad de llamadas concurrentes
	 * @param cantOperadores
	 * @param cantSupervisores
	 * @param cantDirectores
	 */
	public CallCenter(int cantidadHilos, int cantOperadores, int cantSupervisores, int cantDirectores) {

		this.empleados = new StaffEmpleados(cantOperadores, cantSupervisores, cantDirectores);
		this.dispatcher = new Dispatcher(cantidadHilos,this.empleados);

	}

	/**
	 * Recibe y envia a procesar una lista de llamadas.
	 * 
	 * @param llamadas
	 *            lista de llamadas a atender
	 */
	public void procesarLlamadas(List<Llamada> llamadas) {
		this.setLlamadasEntrantes(llamadas);
		this.dispatcher.dispatchCalls(llamadas);
	}

}
