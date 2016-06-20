package otros;

import static org.junit.Assert.*;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.junit.Before;
import org.junit.Test;

import ej2_3.MensajeBanco;

public class TestArchivo {
	
	String path = "cuentas/Cuentas.txt";
	
	@Before
	public void setUp() throws Exception {
		if( ManejadorArchivo.eliminarArchivo(path)){
			System.out.println("borre el archivo");
		}else{
			System.out.println("Error: no pude borrar el archivo");
		}
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
		ManejadorArchivo.mostrarArchivo(path);
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
	
	@Test	//verifico que se inicialize el archivo
	public void testBuscarDirectoID() {
		assertTrue( ManejadorArchivo.crearArchivo(path) );
		assertTrue( ManejadorArchivo.iniciarArchivoDirecto(path) );
		MensajeBanco mb=ManejadorArchivo.buscarDirectoPorID(2, path);
		assertEquals((Integer)2,mb.getId());
		assertEquals((Integer)20,mb.getMonto());
		mb=ManejadorArchivo.buscarDirectoPorID(1, path);
		assertEquals((Integer)1,mb.getId());
		assertEquals((Integer)10,mb.getMonto());
		mb=ManejadorArchivo.buscarDirectoPorID(9, path);
		assertEquals((Integer)9,mb.getId());
		assertEquals((Integer)90,mb.getMonto());
		mb=ManejadorArchivo.buscarDirectoPorID(11, path);
		assertNull(mb);
		ManejadorArchivo.mostrarArchivo(path);
	}
	
	@Test	//verifico que se modifica bien el archivo
	public void testModificarDirectoID() {
		assertTrue( ManejadorArchivo.crearArchivo(path) );
		assertTrue( ManejadorArchivo.iniciarArchivoDirecto(path) );
		MensajeBanco mb=ManejadorArchivo.buscarDirectoPorID(2, path);
		assertEquals((Integer)2,mb.getId());
		assertEquals((Integer)20,mb.getMonto());
		assertTrue( ManejadorArchivo.escribirEnID(2, 30, path) );
		mb=ManejadorArchivo.buscarDirectoPorID(2, path);
		assertEquals((Integer)2,mb.getId());
		assertEquals((Integer)30,mb.getMonto());

	}
	
	
	@Test	//verifico que funcione el lock sobre el archivo
	public void testLockArchivo() {
		
		assertTrue( ManejadorArchivo.crearArchivo(path) );
		assertTrue( ManejadorArchivo.iniciarArchivoDirecto(path) );
		ManejadorArchivo.mostrarArchivo(path);
		ManejadorArchivo.escribirMontoThreadSafe(1, 40, path, 2000);
		MensajeBanco mb=ManejadorArchivo.buscarDirectoPorID(1, path);
		assertEquals((Integer)1,mb.getId());
		assertEquals((Integer)50,mb.getMonto());
		ManejadorArchivo.mostrarArchivo(path);
	}
	
}
