package ejercisio3_4y7;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import ejercicio1y2.HiloServidorTCP;
import ejercicio1y2.ServidorMultipleTCP;

public class ServidorMultipleMensajes extends ServidorMultipleTCP {
	
	protected ControladorMensajes cm;
	
	public static void main(String args[]){
		//creo el servidor y este crea un controlador de mensajes
		ServidorMultipleMensajes smm= new ServidorMultipleMensajes();
		smm.start();
		Scanner s = new Scanner(System.in);
		System.out.println();
		System.out.println("ingrese algo para deterner el servicio de mensajes");
		s.next();
		smm.dejarDeEsperarConexiones();
		System.out.println("servicio finalizado!");
	}
	
	public ServidorMultipleMensajes(){
		super();
		this.cm=new ControladorMensajes();
		
	}
	
	
	@Override
	protected HiloServidorTCP nuevoHilo(Socket so){			
		HiloServidorTCP hs =new HiloServidorMensajes(so,this.cm);		
		hs.start();	
		return hs;
	}
	
	
	
}
