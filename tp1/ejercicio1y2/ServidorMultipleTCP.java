package ejercicio1y2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServidorMultipleTCP extends Thread {
	
	protected Integer puerto=5555;
	protected ServerSocket ss;
	protected ArrayList<HiloServidorTCP> hilos;
	protected boolean esperar_conexiones;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ServidorMultipleTCP server= new ServidorMultipleTCP();		
		server.start();
		Scanner s = new Scanner(System.in);
		System.out.println("ingrese cualquier cosa para cerrar el servidor");
		s.next();
		server.dejarDeEsperarConexiones();
	}

	public ServidorMultipleTCP(){
	}
	
	@Override
	public void run(){
		System.out.println("Soy el ServidorMultipleTCP ");
		try {
			ss= new ServerSocket(puerto);					//creo el serversocket
			ss.setSoTimeout(3000);							//le digo q espere una coexion por 5 segundos
			System.out.println("Mi IP es: "+InetAddress.getLocalHost().getHostAddress()+" y mi puerto es: "+ss.getLocalPort());
			this.esperar_conexiones=true;
		} catch (IOException e) {
			System.out.println("Error al crear el ServerSocket");
			this.esperar_conexiones=false;
			e.printStackTrace();
		}
		try {
			Thread.sleep(100);				//le digo q espere un cachito asi quedan bien los System.out.print
		} catch (InterruptedException e1) {
		}
		hilos = new ArrayList<HiloServidorTCP>();
		System.out.print("espero conexion....");
		while(esperar_conexiones){							//mientras este esperando conexiones
			try {
				Socket so= ss.accept();						//acepto conexiones							
				hilos.add(nuevoHilo(so));					//llamo al nuevoHilo, y agrego el hilo devuelto a
				System.out.println("conexion recibida e hilo creado");
			}catch(java.net.SocketTimeoutException es){
				
			} catch (IOException e) {
				esperar_conexiones=false;					//si algo explota salgo del while y termino
				System.out.println("explote al recibir la conexion");
				e.printStackTrace();
			} 
		}//fin del while esperar_conexiones
		
	if(!ss.isClosed()){										//al salir del while cierro el ServerSocket si lo tenia abierto
		try {
			ss.close();
		} catch (IOException e) {
			System.out.println("no pude cerrar el ServerSocket");
			e.printStackTrace();
		}
		System.out.println("chau!");
	}
		
	}//fin del metodo run
	
	public void dejarDeEsperarConexiones(){ //metodo para que el servidor acepte una ultima conexion y se cierre
		this.esperar_conexiones=false;
	}
	
	//esta parte de codigo esta en un metodo aparte, asi si hago clases que extiendan de ServidorMultipleTCP
	//hago que cambien el comportamiento de este metodo y agregen clases q extiendan de HiloServidorTCP
	protected HiloServidorTCP nuevoHilo(Socket so){			
		HiloServidorTCP hs =new HiloServidorTCP(so);		
		hs.start();	
		return hs;
	}
	
	public Integer getPuerto() {
		return puerto;
	}

	public void setPuerto(Integer puerto) {
		this.puerto = puerto;
	}
}//fin de la clase
