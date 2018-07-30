package ar.com.almundo.dispatcher;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import ar.com.almundo.core.Empleado;
import ar.com.almundo.core.Llamada;
import ar.com.almundo.core.StaffEmpleados;

/**
 * El dispatcher
 * 
 * @author MAI
 *
 */
public class Dispatcher {
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());
	private ExecutorService executor;
	private BlockingQueue colaPendientes;
	private StaffEmpleados empleados;
	private List<Llamada> llamadasEntrantes;

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public BlockingQueue getColaPendientes() {
		return colaPendientes;
	}

	public void setColaPendientes(BlockingQueue colaPendientes) {
		this.colaPendientes = colaPendientes;
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

	/***
	 * Constructor del Dispatcher
	 * 
	 * @param cantidadHilos
	 *            : Cantidad de llamadas concurrentes
	 * @param empleados
	 */
	public Dispatcher(int cantidadHilos, StaffEmpleados empleados) {
		this.setEmpleados(empleados);
		this.executor = Executors.newFixedThreadPool(cantidadHilos, new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread hilo = Executors.defaultThreadFactory().newThread(r);
				hilo.setDaemon(false);
				return hilo;
			}
		});

		// la cola de pendientes, aca se encolan las llamadas que no pudieron ser
		// atendidas
		this.colaPendientes = new LinkedBlockingDeque();
	}

	/**
	 * Metodo que manda a procesar las llamadas de la lista
	 * 
	 * @param llamadas
	 */
	public void dispatchCalls(List<Llamada> llamadas) {
		this.setLlamadasEntrantes(llamadas);
		llamadas.forEach(llamada -> this.procesarLlamada(llamada));
	}

	/**
	 * Procesa la llamada, siempre que consiga un empleado libre, caso contrario la
	 * encola en las de pendientes
	 * 
	 * @param llamada
	 */
	private void procesarLlamada(Llamada llamada) {
		Empleado empleadoLibre = empleados.getEmpleadoDisponible();
		if (empleadoLibre == null) {
			// no tengo empleados
			llamada.falloAtencion();
			LOGGER.info(
					"Llamada " + llamada.getNumeroLlamada() + " imposible de atender, no hay empleados disponibles");
			System.out.println(
					"Llamada " + llamada.getNumeroLlamada() + " imposible de atender, no hay empleados disponibles");
			this.colaPendientes.add(llamada);
			return;
		}
		// sino la proceso
		this.dispatchCall(empleadoLibre, llamada);
	}

	/**
	 * Manda al empleado a atender la llamada
	 * 
	 * @param empleadoLibre
	 *            un empleado libre que puede atender a llamada
	 * @param llamadaEntrante
	 *            : es la llamada a ser atendida
	 */
	public void dispatchCall(Empleado empleadoLibre, Llamada llamadaEntrante) {
		Runnable hiloLlamada = () -> {
			empleadoLibre.atender(llamadaEntrante);
			this.verificoPendientes();
		};
		this.executor.submit(hiloLlamada);
	}

	/**
	 * Chequea que no haya en la cola de pendientes llamadas encoladas, si es asi
	 * las atiende, caso contrario termina el proceso
	 */
	public void verificoPendientes() {
		if (this.colaPendientes.isEmpty()) {
			try {
				LOGGER.info("No hay mas nada para atender");
				this.executor.shutdown();
				executor.awaitTermination(5, TimeUnit.SECONDS);
			} catch (Exception e) {
				LOGGER.info("Tarea interrumpida");
			} finally {
				if (!executor.isTerminated()) {
					LOGGER.info("no ha terminado");
				}
				executor.shutdownNow();
				LOGGER.info("Termino");
			}

		}
		// sino la proceso
		LOGGER.info("Proceso la llamada de la cola de pendientes");
		System.out.println("Proceso la llamada de la cola de pendientes");
		Llamada llamada = (Llamada) this.colaPendientes.remove();
		this.procesarLlamada(llamada);
	}

}
