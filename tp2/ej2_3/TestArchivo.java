package ejercicio2y3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestArchivo {
	
	String path = "Cuentas.txt";
	
	@Before
	public void setUp() throws Exception {
		ManejadorArchivo.eliminarArchivo(path);
	}
	
	@Test	//creo archivo
	public void testNuevoArchivo() {
		assertTrue(ManejadorArchivo.inicializarArchivo(path) );
	}
	
	@Test	//verifico que existe el archivo q creo
	public void testExisteArchivo() {
		assertTrue( ManejadorArchivo.inicializarArchivo(path) );
		assertTrue( ManejadorArchivo.chequearExistencia(path) );
	}
	
	@Test	//verifico que no existe el archivo si no lo creo
	public void testNoExisteArchivo() {
		assertTrue( !ManejadorArchivo.chequearExistencia(path) );
	}
	
	@Test	//verifico que se inicialize el archivo
	public void testInicializarArchivo() {
		assertTrue( ManejadorArchivo.crearArchivo(path) );
		assertTrue( ManejadorArchivo.inicializarArchivo(path) );
	}
	
	@Test	//verifico que se inicialize el archivo
	public void testBuscarID() {
		assertTrue( ManejadorArchivo.crearArchivo(path) );
		assertTrue( ManejadorArchivo.inicializarArchivo(path) );
		MensajeBanco mb=ManejadorArchivo.buscarPorId(2, path);
		assertEquals((Integer)2,mb.getId());
		mb=ManejadorArchivo.buscarPorId(1, path);
		assertEquals((Integer)1,mb.getId());
		mb=ManejadorArchivo.buscarPorId(9, path);
		assertEquals((Integer)9,mb.getId());
		mb=ManejadorArchivo.buscarPorId(11, path);
		assertNull(mb);
	}
	
}
