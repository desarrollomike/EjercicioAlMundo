package ar.com.almundo.callcenter.test;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.almundo.callcenter.CallCenter;
import ar.com.almundo.core.Llamada;
import junit.framework.TestCase;

/**
 * Test para el call center
 * 
 * @author MAI
 *
 */
public class CallCenterTest extends TestCase {

	List<Llamada> listaDeLlamadasEnEspera;
	private static final Logger LOGGER = Logger.getLogger(CallCenterTest.class.getName());

	public CallCenterTest(String name) {
		super(name);
		this.listaDeLlamadasEnEspera = new ArrayList<>();
	}

	@Before
	public void beforeEachTest() throws Exception {
		listaDeLlamadasEnEspera = new ArrayList<>();
	}

	/**
	 * Test con un empleado y una llamada: El unico operador atendio a la unica
	 * llamada entrante
	 */
	@Test
	public void testUnoSolo() throws Exception {
		int operadores = 1;
		int supervisores = 0;
		int directores = 0;
		int hilos = 1;
		CallCenter callCenter = new CallCenter(hilos, operadores, supervisores, directores);

		Llamada llamadaEntrante = new Llamada();
		llamadaEntrante.setNumeroLlamada("Llamada Test 1");
		listaDeLlamadasEnEspera.add(llamadaEntrante);

		callCenter.procesarLlamadas(this.listaDeLlamadasEnEspera);
		sleep(4000);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getAtendidaPor(), "Operador 0");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getFalloAtencion(), 0);
	}

	/**
	 * Un Empleado y dos llamadas entrantes: El unico operador atiende la primera
	 * llamada y luego de desocuparse atiende la otra La segunda queda con intentos
	 * fallidos de atencion
	 */
	@Test
	public void testUnOperadorDosLlamadas() throws Exception {
		int operadores = 1;
		int supervisores = 0;
		int directores = 0;
		int numeroLLamadas = 2;
		int cantidadHilos = 2;
		CallCenter callCenter = new CallCenter(cantidadHilos, operadores, supervisores, directores);

		// genera tantas llamadas como llamadas concurrente
		this.generarLlamadas(numeroLLamadas, "test2");

		callCenter.procesarLlamadas(this.listaDeLlamadasEnEspera);
		sleep(6000);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getAtendidaPor(), "Operador 0");
		// una llamada es re procesada
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(1).getAtendidaPor(), "Operador 0");
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(1).getFalloAtencion() > 0);
	}

	/**
	 * Test 5 operadores, 2 supervisores, 1 director con 8 llamadas concurrentes,
	 * todas son atendidas nunguna sufre un reintento por no contar con empleados
	 * libres
	 *
	 */
	@Test
	public void testIgualEmpleadosQueLLamadas() throws Exception {
		int operadores = 5;
		int supervisores = 2;
		int directores = 1;
		int numeroLLamadas = 8;
		int cantidadHilos = 8;
		CallCenter callCenter = new CallCenter(cantidadHilos, operadores, supervisores, directores);

		this.generarLlamadas(numeroLLamadas, "test3");

		callCenter.procesarLlamadas(this.listaDeLlamadasEnEspera);
		sleep(4000);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getAtendidaPor(), "Operador 0");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(1).getAtendidaPor(), "Operador 1");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(2).getAtendidaPor(), "Operador 2");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(3).getAtendidaPor(), "Operador 3");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(4).getAtendidaPor(), "Operador 4");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(5).getAtendidaPor(), "Supervisor 0");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(6).getAtendidaPor(), "Supervisor 1");
		// nunguna queda con error
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(1).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(2).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(3).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(4).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(5).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(6).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(7).getFalloAtencion(), 0);
	}

	/**
	 * Test 10 llamadas para 6 puestos de atencion: 6 llamadas son atendidas y las
	 * otras 4 pasan a la lista de espera hasta que luego son atendidas por los que
	 * se van desocupando
	 */
	@Test
	public void test10llamada6Hilos() throws Exception {
		int operadores = 3;
		int supervisores = 2;
		int directores = 1;
		int numeroLLamadas = 10;
		int cantidadHilos = 6;
		CallCenter callCenter = new CallCenter(cantidadHilos, operadores, supervisores, directores);

		this.generarLlamadas(numeroLLamadas, "test3");

		callCenter.procesarLlamadas(this.listaDeLlamadasEnEspera);
		sleep(6000);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getAtendidaPor(), "Operador 0");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(1).getAtendidaPor(), "Operador 1");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(2).getAtendidaPor(), "Operador 2");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(3).getAtendidaPor(), "Supervisor 0");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(4).getAtendidaPor(), "Supervisor 1");
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(5).getAtendidaPor(), "Director 0");

		// de las primeras 5 ninguna queda con error
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(0).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(1).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(2).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(3).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(4).getFalloAtencion(), 0);
		Assert.assertEquals(this.listaDeLlamadasEnEspera.get(5).getFalloAtencion(), 0);

		// estas llamadas en algun momento fueron atendidas cuando se fueron desocupando
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(6).getAtendidaPor() != null);
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(6).getFalloAtencion() > 0);

		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(7).getAtendidaPor() != null);
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(7).getFalloAtencion() > 0);

		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(8).getAtendidaPor() != null);
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(8).getFalloAtencion() > 0);

		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(9).getAtendidaPor() != null);
		Assert.assertTrue(this.listaDeLlamadasEnEspera.get(9).getFalloAtencion() > 0);

	}

	/***
	 * Genera llamadas segun la cantidad solicitada
	 * 
	 * @param numeroLLamadas
	 * @param test
	 */
	private void generarLlamadas(int numeroLLamadas, String test) {
		for (int i = 0; i < numeroLLamadas; i++) {
			Llamada llamada = new Llamada();
			llamada.setNumeroLlamada(test + ": " + i);
			this.listaDeLlamadasEnEspera.add(llamada);
		}

	}

}
