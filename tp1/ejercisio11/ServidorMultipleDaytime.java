package ejercisio11;

import java.net.Socket;
import java.util.Scanner;

import ejercicio1y2.HiloServidorTCP;
import ejercicio1y2.ServidorMultipleTCP;

public class ServidorMultipleDaytime extends ServidorMultipleTCP {

	public static void main(String[] args) {
		ServidorMultipleDaytime server = new ServidorMultipleDaytime();
		server.setPuerto(5555);
		server.start();
		Scanner s = new Scanner(System.in);
		System.out.println("ingrese cualquier cosa para cerrar el servidor");
		s.next();
		server.dejarDeEsperarConexiones();
	}
	
	@Override
	protected HiloServidorTCP nuevoHilo(Socket so){			
		HiloServidorTCP hs =new HiloServidorTCPDaytime(so);		
		hs.start();	
		return hs;
	}

}
